package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    @Query(value = """
        select
            p.id,
            p.name,
            p.description,
            p.created_at,
            p.updated_at
          from
            permissions p
          join role_permissions rp on
            rp.permission_id = p.id
          join roles r on
            r.id = rp.role_id
          join user_roles ur on
            ur.role_id = r.id
          join users u on
            u.id = ur.user_id
          where
            u.id = :userId
    """, nativeQuery = true)
    Set<Permission> findPermissionsByUserId(@Param("userId") Long userId);
}
