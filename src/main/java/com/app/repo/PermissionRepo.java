package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Permission;
import com.app.model.PermissionEnum;

@Repository
public interface PermissionRepo extends JpaRepository<Permission, Integer> {
    
    Permission findByPermissionEnum(PermissionEnum permissionEnum);

}
