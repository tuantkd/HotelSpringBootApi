package com.hotelApp.HotelBooking.repository;

import com.hotelApp.HotelBooking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {
    @Query(value = """
        select
            r.id,
            r.name,
            r.description,
            r.created_at,
            r.updated_at
         from
            roles r
         join user_roles ur on
            ur.role_id = r.id
         join users u on
            r.id = ur.user_id
         where
            u.id = :userId
    """, nativeQuery = true)
    Set<Role> findRolesByUserId(@Param("userId") Long userId);
}