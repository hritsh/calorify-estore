package com.estore.api.estoreapi.persistence;

import java.io.IOException;
import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.estore.api.estoreapi.model.Product;
import com.estore.api.estoreapi.model.ShoppingCart;

@Component
public class ShoppingCartFileDAO implements ShoppingCartDAO {
    private static final Logger LOG = Logger.getLogger(InventoryFileDAO.class.getName());
    Map<Integer, ShoppingCart> carts; // Provides a local cache of the product objects
    // so that we don't need to read from the file
    // each time
    private ObjectMapper objectMapper; // Provides conversion between Product
                                       // objects and JSON text format written
                                       // to the file
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
    public ShoppingCartFileDAO(@Value("${shoppingcart.file}") String filename, ObjectMapper objectMapper)
            throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the inventory from the file
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
        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), carts.values().toArray(new ShoppingCart[0]));
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
        carts = new TreeMap<>();

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        ShoppingCart[] cartsArray = objectMapper.readValue(new File(filename), ShoppingCart[].class);

        // Add each cart to the tree map
        for (ShoppingCart cart : cartsArray) {
            carts.put(cart.getUserId(), cart);
        }

        return true;
    }

    @Override
    public ShoppingCart getShoppingCart(int id) throws IOException {
        // If the cart is not in the map, load it from the file
        if (!carts.containsKey(id)) {
            load();
        }

        return carts.get(id);
    }

    @Override
    public int getTotalCalories(int id) throws IOException {
        int totalCalories = 0;
        if (carts.containsKey(id)) {
            HashMap<Product, Integer> cart = carts.get(id).getShoppingCart();
            for (Product product : cart.keySet()) {
                totalCalories += product.getCalories() * cart.get(product);
            }
        }
        return totalCalories;
    }

    @Override
    public float getTotalPrice(int id) throws IOException {
        float totalPrice = 0;
        if (carts.containsKey(id)) {
            HashMap<Product, Integer> cart = carts.get(id).getShoppingCart();
            for (Product product : cart.keySet()) {
                totalPrice += product.getPrice() * cart.get(product);
            }
        }
        return totalPrice;
    }

    @Override
    public boolean addProduct(int id, Product product, int quantity) throws IOException {
        if (carts.containsKey(id)) {
            ShoppingCart cart = carts.get(id);
            HashMap<Product, Integer> shoppingCart = cart.getShoppingCart();
            if (shoppingCart.containsKey(product)) {
                shoppingCart.put(product, shoppingCart.get(product) + quantity);
            } else {
                shoppingCart.put(product, quantity);
            }
            cart.setShoppingCart(shoppingCart);
            carts.put(id, cart);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean removeProduct(int id, Product product) throws IOException {
        if (carts.containsKey(id)) {
            ShoppingCart cart = carts.get(id);
            HashMap<Product, Integer> shoppingCart = cart.getShoppingCart();
            if (shoppingCart.containsKey(product)) {
                shoppingCart.remove(product);
            } else {
                return false;
            }
            cart.setShoppingCart(shoppingCart);
            carts.put(id, cart);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateProductQuantity(int id, Product product, int quantity) throws IOException {
        if (carts.containsKey(id)) {
            ShoppingCart cart = carts.get(id);
            HashMap<Product, Integer> shoppingCart = cart.getShoppingCart();
            if (shoppingCart.containsKey(product)) {
                shoppingCart.put(product, quantity);
            } else {
                return false;
            }
            cart.setShoppingCart(shoppingCart);
            carts.put(id, cart);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean clearShoppingCart(int id) throws IOException {
        if (carts.containsKey(id)) {
            ShoppingCart cart = carts.get(id);
            cart.setShoppingCart(new HashMap<Product, Integer>());
            carts.put(id, cart);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean checkoutShoppingCart(int id) throws IOException {
        if (carts.containsKey(id)) {
            ShoppingCart cart = carts.get(id);
            cart.setShoppingCart(new HashMap<Product, Integer>());
            carts.put(id, cart);
            save();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteShoppingCart(int id) throws IOException {
        if (carts.containsKey(id)) {
            carts.remove(id);
            save();
            return true;
        } else {
            return false;
        }
    }
}
