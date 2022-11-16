package com.estore.api.estoreapi.persistence;

import java.io.EOFException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.Customer;
import com.estore.api.estoreapi.model.Role;
import com.estore.api.estoreapi.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * The file that manipulates the saved data that correlates specifically to the
 * making and deletions of
 * an entire user.
 * 
 * {@Literal @Component} is a spring annotation that indicates that this class
 * is to be instantiated
 * and inserted into any class that needs it upon starting.
 * 
 * @author Team E
 */
@Component
public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<String, Customer> customers; // local data storage of the inventory
    User admin;
    private RoleDAO roleDAO;
    // to object
    private String filename; // the file to read and write to
    private JsonUtilities jsonUtilities;

    /**
     * Creates an Inventory File Data Access Object
     * 
     * @param filename      filename to read from and write to
     * @param jsonUtilities provides conversion between JSON files to object
     * @param roleDAO       used for role operations while creating user
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${users.file}") String filename, RoleDAO roleDAO, JsonUtilities jsonUtilities)
            throws IOException {
        this.filename = filename;
        this.roleDAO = roleDAO;
        this.jsonUtilities = jsonUtilities;
        load(); // load the users from the file
    }

    /**
     * Loads all {@linkplain Customer customers } that were in the file that was
     * passed in
     * Deserialize all JSON products and saves it into a local storage for easy
     * access
     * 
     * @return a boolean indicating if the operation was successful
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        customers = new TreeMap<>();
        // deserialize the JSON file into a list of users
        try {
            String JSONString = Files.readString(Path.of(filename));
            if (JSONString.length() > 0) {
                Customer[] customerPassedIn = jsonUtilities.DeserializeObject(JSONString, Customer[].class);

                // add every user that was just recently deserialized to the local storage
                for (Customer i : customerPassedIn) {
                    if (!i.getUsername().toLowerCase().equals(User.ADMIN)) {
                        customers.put(i.getUsername(), i);
                    } else {
                        // if the admin was read, change the customer into a regular user to save the
                        // admin
                        admin = (User) i;
                    }
                }
            }

        } catch (EOFException e) {
            LOG.info(e.toString());
        }
        // set the nextId to be the next available id
        return true;

    }

    /**
     * saves the list of {@linkplain Customer customers}
     *
     * @return a true indicating the save was successful
     *         An exception if an error occured
     */
    private boolean save() throws IOException {
        Customer[] userList = getUsers();

        jsonUtilities.SerializeObject(filename, userList);
        return true;

    }

    /**
     * {@inheritDoc}
     */
    public Customer[] getUsers() {
        // init
        ArrayList<Customer> userList = new ArrayList<>();
        // userList.add(new Customer(User.ADMIN));

        // get all users saved in a local list
        for (Customer user : customers.values()) {
            // setting password from retrieved JSON to null for security purposes
            // user.setPassword("NULL");
            userList.add(user);
        }

        // transport the list into an array and return it
        Customer[] result = new Customer[userList.size()];
        userList.toArray(result);
        return result;

    }

    // to check if admin exists in users.json already if so, return true , else
    // false
    private boolean checkIfAdminExists() {
        boolean adminExists = false;
        for (Customer user : customers.values()) {
            for (Role r : user.getRole()) {
                if (r.getRoleName().equals("admin")) {
                    adminExists = true;
                }
            }
        }
        return adminExists;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User addUser(User user) throws IOException {
        int roleDoesNotExist = 0;
        Role role;
        for (Role r : user.getRole()) {
            role = roleDAO.getRole(r.getRoleId());
            if (role == null) {
                roleDoesNotExist += 1;
            }
            // to make unique admin
            if (r.getRoleName().equals("admin") == true && checkIfAdminExists() == true) {
                return new User("ERROR: Admin already exists", null, null);
            }
        }
        synchronized (customers) {
            String username = user.getUsername();
            if (roleDoesNotExist > 0) {
                // return null if even one of the roles does not exist in roles.json
                return new User("ERROR: Role does not exist", null, null);
            }
            //need to add check for whether user with same username is being called
            Customer newUser = new Customer(user.getUsername(), user.getPassword(), user.getRole());
            customers.put(username, newUser);
            save();
            // newUser.setPassword("NULL");
            return newUser;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public Customer updateUserDetails(Customer customer) throws IOException {
        synchronized (customers) {
            if (customers.containsKey(customer.getUsername()) == false)
                return null;
            Customer updatedCustomer = customers.get(customer.getUsername());
            updatedCustomer.setfirstName(customer.getfirstName());
            updatedCustomer.setlastName(customer.getlastName());
            updatedCustomer.setGender(customer.getGender());
            updatedCustomer.setHeight(customer.getHeight());
            updatedCustomer.setWeight(customer.getWeight());
            updatedCustomer.setAge(customer.getAge());
            updatedCustomer.setLoggedIn(customer.getLoggedIn());
            customers.put(customer.getUsername(), updatedCustomer);
            save();
            return customer;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUser(String username) throws IOException {
        load();
        synchronized (customers) {
            User currUser = null;
            if (customers.containsKey(username)) {
                currUser = customers.get(username);
            } else {
                if (username.toLowerCase().equals(User.ADMIN)) {
                    currUser = admin;
                }
            }
            return currUser;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean deleteUser(String username) throws IOException {
        synchronized (customers) {
            User currUser = customers.remove(username);
            save();
            return currUser != null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Boolean saveUsers() throws IOException {
        return this.save();
    }

    @Override
    public void setSalad(String username, String salad) throws IOException {
        synchronized (customers) {
            if (customers.containsKey(username)) {
                customers.get(username).setSalad(salad);
                save();
            }
        }
    }

    @Override
    public String getSalad(String username) throws IOException {
        synchronized (customers) {
            if (customers.containsKey(username)) {
                return customers.get(username).getSalad();
            }
        }
        return "";
    }
}