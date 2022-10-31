package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
/**
 * Represents a Role entity
 * 
 * @author Team-E
 */
public class Role {
    static final String STRING_FORMAT = "Role [roleId=%d, roleName=%s]";

    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("roleName")
    private String roleName;
    /**
     * Create a Role with the given roleId, and name
     * @param roleId The id of the role
     * @param roleName The name of the role
     * 
     * {@literal @}JsonProperty is used in serialization and deserialization
     * of the JSON object to the Java object in mapping the fields.  If a field
     * is not provided in the JSON object, the Java field gets the default Java
     * value, i.e. 0 for int
     */
    public Role(
            @JsonProperty("roleId") int roleId, @JsonProperty("roleName") String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }
    /**
     * returns the number that represents the id of the role
     * 
     * @return a number representing this roles id
     */
    public int getRoleId() {
        return roleId;
    }
    /**
     * Sets the id of the Role - necessary for JSON object to Java object
     * deserialization
     * 
     * @param roleId The id of the Role
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    /**
    * returns the name of the role
    * 
    * @return a string representing this roles name
    */
    public String getRoleName() {
        return roleName;
    }
    /**
    * Sets the name of the Role - necessary for JSON object to Java object
    * deserialization
    * 
    * @param roleName The name of the Role
    */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
