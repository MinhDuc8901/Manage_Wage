package com.manage.controller;

import java.util.List;

import javax.annotation.security.RolesAllowed;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manage.dto.ResponseCustom;
import com.manage.dto.request.hsl.HSLRequest;
import com.manage.dto.request.position.PositionRequest;
import com.manage.dto.request.wage.wageRequest;
import com.manage.entity.HSL;
import com.manage.entity.Position;
import com.manage.entity.Role;
import com.manage.entity.User;
import com.manage.entity.Wage;
import com.manage.repository.HSLRepository;
import com.manage.repository.PositionRepository;
import com.manage.repository.RoleRepository;
import com.manage.repository.UserRepository;
import com.manage.repository.WageRepository;

@RestController
@RequestMapping("/api/v1/manage")
public class ManageController {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private HSLRepository hslRepo;

    @Autowired
    private PositionRepository posiRepo;

    @Autowired
    private WageRepository wageRepo;

    @RolesAllowed("ADMIN")
    @PostMapping("/create_role")
    public ResponseEntity<?> createRole(@RequestBody Role role) {
        roleRepo.save(role);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/get_list_role")
    public ResponseEntity<?> getListRole() {
        List<Role> roles = roleRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<Role>>(200, "Thành công", roles));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/get_user_by_id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Integer id) {
        User user = userRepo.findById(id).get();
        return ResponseEntity.ok(new ResponseCustom<User>(200, "Thành công", user));
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/get_list_user")
    public ResponseEntity<?> getListUser() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<User>>(200, "Thành công", users));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/delete_user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        User user = userRepo.findById(id).get();
        userRepo.delete(user);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/get_list_hsl")
    public ResponseEntity<?> getHSL() {
        List<HSL> listHSL = hslRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<HSL>>(200, "Thành công", listHSL));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/create_hsl")
    public ResponseEntity<?> createHSL(@RequestBody HSLRequest request) {
        HSL hsl = modelMapper.map(request, HSL.class);
        hslRepo.save(hsl);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", "Thành công"));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/delete_hsl/{id}")
    public ResponseEntity<?> deleteHSL(@PathVariable Integer id) {
        HSL hsl = hslRepo.findById(id).get();
        hslRepo.delete(hsl);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/get_list_position")
    public ResponseEntity<?> getListPosition() {
        List<Position> listPosition = posiRepo.findAll();
        return ResponseEntity.ok(new ResponseCustom<List<Position>>(200, "Thành công", listPosition));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/get_position/{id}")
    public ResponseEntity<?> getPosition(@PathVariable Integer id) {
        Position position = posiRepo.findById(id).get();
        return ResponseEntity.ok(new ResponseCustom<Position>(200, "Thành công", position));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/delete_position/{id}")
    public ResponseEntity<?> deletePosition(@PathVariable Integer id) {
        Position position = posiRepo.findById(id).get();
        posiRepo.delete(position);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/create_position")
    public ResponseEntity<?> createPosition(@RequestBody PositionRequest request) {
        Position position = modelMapper.map(request, Position.class);
        posiRepo.save(position);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/update_postion")
    public ResponseEntity<?> updatePosition(@RequestBody Position request) {
        posiRepo.save(request);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/get_list_wage_by_user")
    public ResponseEntity<?> getListWageByUser() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getUsername();
        User user = userRepo.findByEmail(currentPrincipalName).get();
        List<Wage> listWage = wageRepo.getListWageByUser(user.getId());
        return ResponseEntity.ok(new ResponseCustom<List<Wage>>(200, "Thành công", listWage));
    }

    @RolesAllowed({ "ADMIN", "USER" })
    @GetMapping("/get_wage_by_user_new")
    public ResponseEntity<?> geWageByUserNew() {
        Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getUsername();
        User user = userRepo.findByEmail(currentPrincipalName).get();
        List<Wage> listWage = wageRepo.getListWageByUser(user.getId());
        return ResponseEntity.ok(new ResponseCustom<Wage>(200, "Thành công", listWage.get(listWage.size() - 1)));
    }

    @RolesAllowed("ADMIN")
    @PostMapping("/create_wage")
    public ResponseEntity<?> createWageByUser(@RequestBody wageRequest wage) {
        Wage wageInsert = modelMapper.map(wage, Wage.class);
        wageInsert.setWorkOfDay(30);
        User user = userRepo.findById(wage.getUserId()).get();
        wageInsert.setUser(user);
        HSL hsl = hslRepo.findById(wage.getHSLId()).get();
        wageInsert.setHsl(hsl);
        double tmpSalary;
        tmpSalary = 1490000 * hsl.getValue();
        wageInsert.setSalary(Double.valueOf(tmpSalary));
        wageRepo.save(wageInsert);
        return ResponseEntity.ok(new ResponseCustom<String>(200, "Thành công", ""));
    }

}
