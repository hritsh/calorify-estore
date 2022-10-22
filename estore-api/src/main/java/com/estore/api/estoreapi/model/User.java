package com.estore.api.estoreapi.model;

import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a User entity
 * 
 * @author Team-E
 */
public class User {
    private static final Logger LOG = Logger.getLogger(Product.class.getName());

    // Package private for tests
    static final String STRING_FORMAT = "Product [id=%d, name=%s, image=%s, calories=%d, price=%f]";

// We intend to keep 30 products in the inventory.

    @JsonProperty("id")
    private int id;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
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
    @JsonProperty("loggedIn")
    private boolean loggedIn;
    @JsonProperty("saltString")
    private String saltString;
    /**
     * Create a product with the given id and name
     * 
     * @param id       The id of the product
     * @param name     The name of the product
     * 
     *                 {@literal @}JsonProperty is used in serialization and
     *                 deserialization
     *                 of the JSON object to the Java object in mapping the
     *                 fields. If a field
     *                 is not provided in the JSON object, the Java field gets
     *                 the default Java
     *                 value, i.e. 0 for int
     */
    public User(@JsonProperty("id") int id, @JsonProperty("username") String username, @JsonProperty("password") String password, @JsonProperty("saltString") String saltString) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.saltString = saltString;
    }
    public String getSaltString() {
        return saltString;
    }
    public void setSaltString(String saltString) {
        this.saltString = saltString;
    }
    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public boolean getloggedIn() {
        return loggedIn;
    }
    /**
     * Sets the log in status of the User
     * 
     * @param loggedIn The log in status of the User
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
    /**
     * Retrieves the id of the product
     * 
     * @return The id of the product
     */
    public int getId() {
        return id;
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
     * @return The weight of the User
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    /**
     * Gets the password of the User
     * 
     * @return The password of the User
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password of the User
     * 
     * @return The password of the User
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Retrieves the username of the User
     * 
     * @return The username of the User
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the name of the User - necessary for JSON object to Java object
     * deserialization
     * 
     * @param username The username of the User
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return The age of the User
     */
    public void setAge(Integer age) {
        this.age = age;
    }
    /**
     * Set the first name  of the User
     * 
     * @param firstName The lastName of the User
     */
    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * Retrieves the first name  of the User
     * 
     * @return The first name of the User
     */
    public String getfirstName() {
        return firstName;
    }
    /**
     * Set the last name  of the User
     * 
     * @param lastName The lastName of the User
     */
    public void setlastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * Retrieves the last name  of the User
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
        return String.format(STRING_FORMAT, id, username);
    }
}
