package org.example.app.repository;

import org.example.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.id = :id")
    Optional<User> findWithSubscriptionsById(@Param("id") Long id);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.subscriptions WHERE u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);


}
