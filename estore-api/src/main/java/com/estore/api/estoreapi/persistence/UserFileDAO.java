package com.estore.api.estoreapi.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import com.estore.api.estoreapi.model.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.estore.api.estoreapi.model.User;

public class UserFileDAO implements UserDAO {
    private static final Logger LOG = Logger.getLogger(UserFileDAO.class.getName());
    Map<Integer, User> users;
    private ObjectMapper objectMapper;

    private static int nextId;
    private String filename;

    /**
     * Creates a User File Data Access Object
     * 
     * @param filename     Filename to read from and write to
     * @param objectMapper Provides JSON Object to/from Java Object serialization
     *                     and deserialization
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    public UserFileDAO(@Value("${user.file}") String filename, ObjectMapper objectMapper) throws IOException {
        this.filename = filename;
        this.objectMapper = objectMapper;
        load(); // load the inventory from the file
    }

    /**
     * Generates the next id for a new {@linkplain User user}
     * 
     * @return The next id
     */
    private synchronized static int nextId() {
        int id = nextId;
        ++nextId;
        return id;
    }

    /**
     * Saves the {@linkplain User users} from the map into the file as an
     * array of JSON objects
     * 
     * @return true if the {@link User users} were written successfully
     * 
     * @throws IOException when file cannot be accessed or written to
     */
    private boolean save() throws IOException {
        User[] userArray = getUsersArray();

        // Serializes the Java Objects to JSON objects into the file
        // writeValue will thrown an IOException if there is an issue
        // with the file or reading from the file
        objectMapper.writeValue(new File(filename), userArray);
        return true;
    }

    /**
     * Loads {@linkplain User users} from the JSON file into the map
     * <br>
     * Also sets next id to one more than the greatest id found in the file
     * 
     * @return true if the file was read successfully
     * 
     * @throws IOException when file cannot be accessed or read from
     */
    private boolean load() throws IOException {
        users = new TreeMap<>();
        nextId = 0;

        // Deserializes the JSON objects from the file into an array of products
        // readValue will throw an IOException if there's an issue with the file
        // or reading from the file
        User[] userArray = objectMapper.readValue(new File(filename), User[].class);

        // Add each user to the tree map and keep track of the greatest id
        for (User user : userArray) {
            users.put(user.getId(), user);
            if (user.getId() > nextId)
                nextId = user.getId();
        }
        // Make the next id one greater than the maximum from the file
        ++nextId;
        return true;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map
     * 
     * @return The array of {@link User users}, may be empty
     */
    private User[] getUsersArray() {
        return getUsersArray(null);
    }

    private static String getSalt() {
        byte[] salt = new byte[16];
        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return salt.toString();
    }

    private static String convertToSHA256(String password, String salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createUser(String username, String password) throws IOException {
        String salt = getSalt();
        String passwordHash = convertToSHA256(password, salt);
        synchronized (users) {
            // We create a new user object because the id field is immutable
            // and we need to assign the next unique id
            User newUser = new User(nextId(), username, passwordHash);
            users.put(newUser.getId(), newUser);
            save(); // may throw an IOException
            return newUser;
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User createProfile(User user) throws IOException {
        synchronized (users) {
            if (users.containsKey(user.getId()) == false)
                return null;

            user.setfirstName(user.getfirstName());
            user.setlastName(user.getlastName());
            user.setHeight(user.getHeight());
            user.setWeight(user.getWeight());
            user.setAge(user.getAge());
            user.setGender(user.getGender());
        }
        return null;
    }

    /**
     * Generates an array of {@linkplain User users} from the tree map for any
     * {@linkplain User users} that contains the text specified by
     * containsText
     * <br>
     * If containsText is null, the array contains all of the {@linkplain User
     * users}
     * in the tree map
     * 
     * @return The array of {@link User users}, may be empty
     */
    private User[] getUsersArray(String containsText) {
        ArrayList<User> userArrayList = new ArrayList<>();

        for (User user : users.values()) {
            if (containsText == null || user.getUsername().contains(containsText)) {
                userArrayList.add(user);
            }
        }

        User[] userArray = new User[userArrayList.size()];
        userArrayList.toArray(userArray);
        return userArray;
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User[] getUsers() {
        synchronized (users) {
            return getUsersArray();
        }
    }

    /**
     ** {@inheritDoc}
     */
    @Override
    public User getUser(int id) {
        synchronized (users) {
            if (users.containsKey(id))
                return users.get(id);
            else
                return null;
        }
    }

    @Override
    /**
     ** {@inheritDoc}
     */
    public boolean deleteUser(int userId, String password) throws IOException {
        String salt = getSalt();
        String passwordHash = convertToSHA256(password, salt);
        synchronized (users) {
            if (users.containsKey(userId)) {
                users.remove(userId);
                return save();
            } else
                return false;
        }
    }

    @Override
    public User updateUser(User user) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User updateUserDetails(int userId, String passwordHash, String firstName, String lastName, int height,
            int weight, int age, String gender) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public User logoutUser(int userId, boolean loggedInStatus) throws IOException {
        // TODO Auto-generated method stub
        return null;
    }
}
