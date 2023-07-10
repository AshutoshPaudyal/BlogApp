package com.blogapp.blog.app.repositories;

import com.blogapp.blog.app.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
