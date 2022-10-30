package com.estore.api.estoreapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Role {
    @JsonProperty("roleId")
    private int roleId;
    @JsonProperty("roleName")
    private String roleName;

    public Role(
            @JsonProperty("roleId") int roleId, @JsonProperty("roleName") String roleName) {
        this.roleId = roleId;
        this.roleName = roleName;
    }

    public int getRoleId() {
        return roleId;
    }
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
