package org.example.repository;

import org.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository  extends JpaRepository<User, Long> {
        Optional<User> findByUsername(String username);

        User deleteByUsername(String username);
        @Query(value = "select * from users u where u.username = ?1", nativeQuery = true)
        Optional<User> getByUsernameNative(String username);


}
