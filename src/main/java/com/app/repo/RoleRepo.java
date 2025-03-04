package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Role;
import com.app.model.RoleEnum;

@Repository
public interface RoleRepo extends JpaRepository<Role, Integer> {

    Role findByRoleEnum(RoleEnum roleEnum);

}
