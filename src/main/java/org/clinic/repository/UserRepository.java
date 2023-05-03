package org.clinic.repository;

import org.clinic.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            WITH tmp AS (
                SELECT *
                FROM users_roles AS ur
                JOIN role AS r
                    ON ur.role_id = r.role_id)
            SELECT u.*
            FROM users AS u
            JOIN tmp
                ON u.user_id = tmp.user_id
            WHERE tmp.role_name = ?;
            """, nativeQuery = true)
    List<User> findAdminUsers(String roleName);

    Optional<User> findByUsername(String username);
}
