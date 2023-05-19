package com.manage.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.dto.ResponseCustom;
import com.manage.entity.Role;
import com.manage.entity.User;
import com.manage.repository.RoleRepository;
import com.manage.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/manage")
public class ManageController {

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;


    @RolesAllowed("ADMIN")
    @PostMapping("/create_role")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        roleRepo.save(role);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/get_list_role")
    public ResponseEntity<?> getListRole(){
        List<Role> roles = roleRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<Role>>(200,"Thành công",roles));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/get_list_user")
    public ResponseEntity<?> getListUser(){
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<User>>(200,"Thành công", users));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/delete_user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id){
        User user = userRepo.getById(id);
        userRepo.delete(user);
        return ResponseEntity.ok(new ResponseCustom<String>(200,"Thành công",""));
    }




}
