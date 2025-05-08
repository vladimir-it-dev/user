package org.example.app.repository;

import org.example.app.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByIdAndUserId(Long subscriptionId, Long userId);

    @Query("SELECT s.subscriptionName, COUNT(u) as usersCount " +
            "FROM Subscription s JOIN s.user u " +
            "GROUP BY s.subscriptionName " +
            "ORDER BY usersCount DESC " +
            "LIMIT 3")
    List<Object[]> findTop3PopularSubscriptions();
}
