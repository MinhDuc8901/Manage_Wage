package com.manage.controller;

import javax.annotation.security.RolesAllowed;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manage.config.jwt.JwtTokenUtil;
import com.manage.dto.ResponseCustom;
import com.manage.dto.request.auth.AuthRequest;
import com.manage.dto.request.auth.UserRequest;
import com.manage.dto.response.auth.AuthResponse;
import com.manage.entity.Role;
import com.manage.entity.User;
import com.manage.repository.UserRepository;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

            User user = (User) authentication.getPrincipal();
            String accessToken = jwtTokenUtil.generateAccessToken(user);
            AuthResponse response = new AuthResponse(user.getEmail(), accessToken);

            return ResponseEntity.ok(response);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        User user = modelMapper.map(userRequest, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.addRole(new Role(2));// mặc định có quyền USER
        userRepo.save(user);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }
    
    @RolesAllowed("ADMIN")
    @PostMapping("/set_role_by_user/{id}")
    public ResponseEntity<?> setRoleUser(@RequestBody Role role, @PathVariable Integer id) {
        User user = userRepo.findById(id).get();
        user.addRole(role);
        userRepo.save(user);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", "Thành công"));
    }

}
