package com.estore.api.estoreapi.persistence;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;

/**
 * Implements the functionality for JSON file-based peristance for Products
 * 
 * {@literal @}Component Spring annotation instantiates a single instance of
 * this
 * class and injects the instance into other classes as needed
 * 
 * @author Team-E
 */
@Component
public class InventoryFileDAO implements InventoryDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer, Product> products; // Provides a local cache of the product objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Product
                                       // objects and JSON text format written
                                       // to the file
    private static int nextId; // The next Id to assign to a new product
    private String filename; // Filename to read from and write to

    /**
     * Creates a Product File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public InventoryFileDAO(@Value("${inventory.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the inventory from the file
    }

    /**
     * Generates the next id for a new {@linkplain Product product}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map
     * 
     * @return The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray() {
        return getProductsArray(null, null, true);
    }

    /**
     * Generates an array of {@linkplain Product products} from the tree map for any
     * {@linkplain Product products} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain Product
     * products}
     * in the tree map
     * 
     * @return The array of {@link Product products}, may be empty
     */
    private Product[] getProductsArray(String containsText, Integer containsCalories, boolean getAll) {
        ArrayList<Product> productArrayList = new ArrayList<>();
        // if containsText == null, no filter, (excluding because controller accounts
        // for no query parameter)
        for (Product product : products.values()) {
            if (getAll == true) {
                productArrayList.add(product);
            } else if (containsCalories == null) {
                // matches has been used instead of containsText to make search case insensitive
                // ?i switches to case insensitive mode, .* means any sequence of characters is
                // accepted
                if (product.getName().matches("(?i).*" + containsText + ".*"))
                    productArrayList.add(product);
            } else if (containsText == null) {
                if (product.getCalories() <= containsCalories)
                    productArrayList.add(product);
            } else if (containsCalories != null && containsText != null) {
                if (product.getName().matches("(?i).*" + containsText + ".*")
                        && product.getCalories() <= containsCalories)
                    productArrayList.add(product);
            }
        }

        Product[] productArray = new Product[productArrayList.size()];
        productArrayList.toArray(productArray);
        return productArray;
    }

    /**
     * Saves the {@linkplain Product products} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link Product products} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        Product[] productArray = getProductsArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), productArray);
        return true;
    }

    /**
     * Loads {@linkplain Product products} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        products = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        Product[] productArray = objectMapper.readValue(new File(filename), Product[].class);

        // Add each product to the tree map and keep track of the greatest id
        for (Product product : productArray) {
            products.put(product.getId(), product);
            if (product.getId() > nextId)
                nextId = product.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product[] getProducts() {
        synchronized (products) {
            return getProductsArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product getProduct(int id) {
        synchronized (products) {
            if (products.containsKey(id))
                return products.get(id);
            else
                return null;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Product createProduct(Product product) throws IOException {
        synchronized (products) {
            // We create a new product object because the id field is immutable
            // and we need to assign the next unique id
            // check if a product name already exists in products
            // if it does, return null
            for (Product p : products.values()) {
                if (p.getName().equals(product.getName())) {
                    save();
                    return null;
                }
            }
            // else, create a new product and add it to products
            Product newProduct = new Product(nextId(), product.getName(), product.getImage(), product.getCalories(),
                    product.getPrice());
            products.put(newProduct.getId(), newProduct);
            save(); // may throw an IOException
            return newProduct;
        }
    }

    @Override
    /**
     ** {@inheritDoc}
     ** called searchProduct on Trello card
     */
    public Product[] searchProduct(String containsText, Integer containsCalories) throws IOException {
        synchronized (products) {
            return getProductsArray(containsText, containsCalories, false);
        }
    }

    @Override
    /**
     ** {@inheritDoc}
     */
    public Product updateProduct(Product product) throws IOException {
        synchronized (products) {
            if (products.containsKey(product.getId()) == false)
                return null; // product does not exist

            products.put(product.getId(), product);
            save(); // may throw an IOExc eption
            return product;
        }
    }

    @Override
    /**
     ** {@inheritDoc}
     */
    public boolean deleteProduct(int id) throws IOException {
        synchronized (products) {
            if (products.containsKey(id)) {
                products.remove(id);
                return save();
            } else
                return false;
        }
    }

    /**
     * {@inheritDoc}
     */
    public Product createClone(int id, int quantity) {
        if (quantity == 0) {
            return null;
        } else {

            Product result = new Product(products.get(id), quantity);
            return result;
        }
    }

    /**
     * Changes the ammount of the inventory has in stock
     * 
     * @return false:
     *         if the amount of the {@linkplain Product product} that is being
     *         checkedout is larger than the amount the inventory has in stock
     *         if a {@link Product product} that is being checked out no longer is
     *         available in the inventory
     *         true otherwise indicating a sucessful action
     * 
     * @throws IOException
     */
    public Boolean checkOut(Product[] passed) throws IOException {
        // base cases for false
        for (Product input : passed) {
            if (!products.containsKey(input.getId())) {
                return false;
            } else {
                if (input.getQuantity() > products.get(input.getId()).getQuantity()) {
                    return false;
                }
            }
        }

        // execute the checkout in other terms: reduce the stock of each item based on
        // the items being checkedout
        for (int i = 0; i < passed.length; i++) {
            products.get(passed[i].getId())
                    .setQuantity(products.get(passed[i].getId()).getQuantity() - passed[i].getQuantity());
            if (products.get(passed[i].getId()).getQuantity() <= 0) {
                this.deleteProduct(passed[i].getId());
            }
        }

        // save the changed inventory
        this.save();
        return true;
    }
}
