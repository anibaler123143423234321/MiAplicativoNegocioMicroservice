package com.dagnerchuman.miaplicativonegociomicroservice.entity;

import java.io.Serializable;

public class Role implements Serializable {
    private String roleName; // Nombre del rol (por ejemplo, "USER", "ADMIN", "SUPERADMIN")

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }
}
