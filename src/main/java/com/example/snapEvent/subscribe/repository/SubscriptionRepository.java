package com.example.snapEvent.subscribe.repository;

import com.example.snapEvent.subscribe.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {

}
