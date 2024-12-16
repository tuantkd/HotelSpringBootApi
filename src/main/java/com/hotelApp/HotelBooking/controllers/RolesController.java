package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.MessageDto;
import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;
import com.hotelApp.HotelBooking.dtos.RoleDto;
import com.hotelApp.HotelBooking.services.roles.RolesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/role")
public class RolesController {
    private final RolesService rolesService;

    public RolesController(RolesService rolesService) {
        this.rolesService = rolesService;
    }

    /**
     * API to get all roles.
     */
    @GetMapping("/getRoles")
    public ResponseEntity<?> getRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortOrder // ASC or DESC
    ) {
        try {
            PaginatedResponseDto roleDtoPage = rolesService.getRoles(page, pageSize, sortField, sortOrder);
            return new ResponseEntity<>(roleDtoPage, HttpStatus.OK);
        } catch (Exception e) {
            MessageDto message = new MessageDto();
            message.setStatus(HttpStatus.NO_CONTENT.toString());
            message.setMessage(MessageConst.NO_CONTENT);
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API to post add new role.
     */
    @PostMapping("/createRole")
    public ResponseEntity<?> createRole(@RequestBody RoleDto roleDto) {
        RoleDto role = rolesService.createRole(roleDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    /**
     * API to post update role.
     */
    @PostMapping("/updateRole")
    public ResponseEntity<?> updateRole(@RequestBody RoleDto roleDto) {
        RoleDto role = rolesService.updateRole(roleDto);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteRole/{id}")
    public ResponseEntity<?> deleteRole(@PathVariable Long id) {
        rolesService.deleteRole(id);
        MessageDto message = new MessageDto();
        message.setStatus(HttpStatus.OK.toString());
        message.setMessage(MessageConst.DEL_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}