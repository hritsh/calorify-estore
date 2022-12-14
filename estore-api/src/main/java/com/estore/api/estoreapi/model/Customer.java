package com.estore.api.estoreapi.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The customer class
 * A class that represents a user that contains a shopping cart and can buy
 * products from the shop
 * 
 * @author Team-E
 */
public class Customer extends User {

    static final String STRING_FORMAT = "Customer [firstName=%s, lastName=%s, gender=%s, height=%d, weight=%d, age=%d]";

    @JsonProperty("firstName")
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
    @JsonProperty("gender")
    private String gender;
    @JsonProperty("height")
    private int height;
    @JsonProperty("weight")
    private int weight;
    @JsonProperty("age")
    private int age;

    @JsonProperty("cart")
    private ShoppingCart cart;
    @JsonProperty("salad")
    private String salad;

    /**
     * Json constructor to initialize a {@linkplain Customer customer} from a json
     * string
     * 
     * @param username the username of the {@link Customer customer}
     * @param role     the set of roles of the {@link Customer customer}
     * @param password the password of the {@link Customer customer}
     * @param cart     the {@link ShoppingCart cart} that is associated with the
     *                 {@link Customer customer}
     */
    @JsonCreator
    public Customer(@JsonProperty("username") String username, @JsonProperty("password") String password,
            @JsonProperty("role") Set<Role> role, @JsonProperty("cart") ShoppingCart cart) {
        super(username, password, role);
        this.cart = cart;
        this.salad = "0-0000000000000-0000000-000000-0-0-0-0";
    }

    /**
     * Create a brand new {@linkplain Customer customer} with an empty
     * {@link ShoppingCart cart}
     * 
     * @param username the username of the {@link Customer customer}
     * @param role     the set of roles associated with the customer
     * @param password the password of the {@link Customer customer}
     */
    public Customer(String username, String password, Set<Role> role) {
        super(username, password, role);
        this.cart = new ShoppingCart(null);
        this.salad = "0-0000000000000-0000000-000000-0-0-0-0";
    }

    /**
     * Adds a product into this {@linkplain Customer customer}'s
     * {@linkplain ShoppingCart cart}
     * 
     * @param product the {@link Product product} to add to this {@link Customer
     *                customer}'s {@link ShoppingCart cart}
     * @return the {@link Product product} that was added
     */
    public Product addProduct(Product product) {
        return this.cart.addProduct(product);
    }

    /**
     * removes a {@linkplain Product product} from the {@link Customer customer}'s'
     * cart
     * 
     * @param id the id of the {@link Product product} to remove
     * @return the {@link Product product} that was removed
     */
    public Product removeProduct(int id) {
        return this.cart.removeProduct(id);
    }

    /**
     * clears the {@linkplain ShoppingCart cart}
     * 
     * @return true indicating that the cart was cleared
     */
    public boolean clearCart() {
        return this.cart.clearCart();
    }

    /**
     * Returns the user's {@linkplain ShoppingCart cart}
     * 
     * @return the {@linkplain ShoppingCart cart}
     */
    @JsonIgnore
    public Product[] getCart() {
        return this.cart.getItems();
    }

    /**
     * Returns the user's {@linkplain String salad}
     * 
     * @return the {@linkplain String salad}
     */
    public String getSalad() {
        return this.salad;
    }

    /**
     * Sets the user's {@linkplain String salad}
     * 
     * @param salad the {@linkplain String salad}
     */
    public void setSalad(String salad) {
        this.salad = salad;
    }

    /**
     * Retrieves the height of the user
     * 
     * @return The height of the user
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the gender of the user
     * 
     * @return The gender of the user
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets the gender of the User
     * 
     * @param gender The gender of the User
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the height of the User
     * 
     * @param height The height of the User
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * Retrieves the weight of the user
     * 
     * @return The weight of the user
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Sets the weight of the User
     * 
     * @param weight The weight of the User
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * Retrieves the age of the User
     * 
     * @return The age of the User
     */
    public int getAge() {
        return age;
    }

    /**
     * Retrieves the age of the User
     * 
     * @param age The age of the User
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Set the first name of the User
     * 
     * @param firstName The lastName of the User
     */
    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Retrieves the first name of the User
     * 
     * @return The first name of the User
     */
    public String getfirstName() {
        return firstName;
    }

    /**
     * Set the last name of the User
     * 
     * @param lastName The lastName of the User
     */
    public void setlastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Retrieves the last name of the User
     * 
     * @return The last name of the User
     */
    public String getlastName() {
        return lastName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return String.format(STRING_FORMAT, firstName, lastName, gender, height, weight, age);
    }
}