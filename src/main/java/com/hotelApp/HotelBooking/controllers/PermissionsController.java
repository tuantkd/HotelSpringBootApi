package com.hotelApp.HotelBooking.controllers;

import com.hotelApp.HotelBooking.constants.MessageConst;
import com.hotelApp.HotelBooking.dtos.MessageDto;
import com.hotelApp.HotelBooking.dtos.PaginatedResponseDto;
import com.hotelApp.HotelBooking.dtos.PermissionDto;
import com.hotelApp.HotelBooking.dtos.RoleDto;
import com.hotelApp.HotelBooking.services.permissions.PermissionsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/permission")
public class PermissionsController {
    private final PermissionsService permissionsService;

    public PermissionsController(PermissionsService permissionsService) {
        this.permissionsService = permissionsService;
    }

    /**
     * API to get all permissions.
     */
    @GetMapping("/getPermissions")
    public ResponseEntity<?> getPermissions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortField,
            @RequestParam(defaultValue = "ASC") String sortOrder // ASC or DESC
    ) {
        try {
            PaginatedResponseDto paginatedResponse = permissionsService.getPermissions(page, pageSize, sortField, sortOrder);
            return new ResponseEntity<>(paginatedResponse, HttpStatus.OK);
        } catch (Exception e) {
            MessageDto message = new MessageDto(HttpStatus.NO_CONTENT.toString(), MessageConst.NO_CONTENT);
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }
    }

    /**
     * API to post add new permission.
     */
    @PostMapping("/createPermission")
    public ResponseEntity<PermissionDto> createRole(@RequestBody PermissionDto permissionDto) {
        PermissionDto permission = permissionsService.createPermission(permissionDto);
        return new ResponseEntity<>(permission, HttpStatus.CREATED);
    }

    /**
     * API to post update permission.
     */
    @PostMapping("/updatePermission")
    public ResponseEntity<PermissionDto> updatePermission(@RequestBody PermissionDto permissionDto) {
        PermissionDto permission = permissionsService.updatePermission(permissionDto);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }

    /**
     * API to delete permission.
     */
    @DeleteMapping("/deletePermission/{id}")
    public ResponseEntity<MessageDto> deletePermission(@PathVariable Long id) {
        permissionsService.deletePermission(id);
        MessageDto message = new MessageDto(HttpStatus.OK.toString(), MessageConst.DEL_SUCCESS);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    /**
     * API to get permission by id.
     */
    @GetMapping("/findPermissionById/{id}")
    public ResponseEntity<PermissionDto> findPermissionById(@PathVariable Long id) {
        PermissionDto permission = permissionsService.findPermissionById(id);
        return new ResponseEntity<>(permission, HttpStatus.OK);
    }
}
