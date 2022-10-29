package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("roleDescription")
    private String roleDescription;

    public Role(
            @JsonProperty("roleId") int roleId, @JsonProperty("roleName") String roleName, @JsonProperty("roleDescription") String roleDescription) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.roleDescription = roleDescription;
    }

    public int getRoleId() {
        return roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public String getRoleDescription() {
        return roleDescription;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }
}
