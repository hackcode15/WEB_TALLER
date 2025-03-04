// Uso solo para cargar los roles y permisos

/* package com.app;

import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.app.model.Permission;
import com.app.model.PermissionEnum;
import com.app.model.Role;
import com.app.model.RoleEnum;
import com.app.repo.PermissionRepo;
import com.app.repo.RoleRepo;

@Component
public class DataLoader implements CommandLineRunner {
    
    private final RoleRepo roleRepo;
    private final PermissionRepo permissionRepo;

    public DataLoader(RoleRepo roleRepo, PermissionRepo permissionRepo){
        this.roleRepo = roleRepo;
        this.permissionRepo = permissionRepo;
    }

    @Override
    public void run(String... args) throws Exception {
       
        Permission createPermission = new Permission();
        createPermission.setPermissionEnum(PermissionEnum.CREATE);
        permissionRepo.save(createPermission);

        Permission readPermission = new Permission();
        readPermission.setPermissionEnum(PermissionEnum.READ);
        permissionRepo.save(readPermission);

        Permission updatePermission = new Permission();
        updatePermission.setPermissionEnum(PermissionEnum.UPDATE);
        permissionRepo.save(updatePermission);

        Permission deletePermission = new Permission();
        deletePermission.setPermissionEnum(PermissionEnum.DELETE);
        permissionRepo.save(deletePermission);
    
        Role userRole = new Role();
        userRole.setRoleEnum(RoleEnum.USER);
        userRole.setPermissions(Set.of(readPermission));
        roleRepo.save(userRole);

        Role adminRole = new Role();
        adminRole.setRoleEnum(RoleEnum.ADMIN);
        adminRole.setPermissions(Set.of(readPermission, updatePermission));
        roleRepo.save(adminRole);

        Role developerRole = new Role();
        developerRole.setRoleEnum(RoleEnum.DEVELOPER);
        developerRole.setPermissions(Set.of(createPermission, readPermission, updatePermission, deletePermission));
        roleRepo.save(developerRole);

    }

    

} */