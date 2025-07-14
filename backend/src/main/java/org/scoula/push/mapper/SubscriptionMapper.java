package org.scoula.push.mapper;

import org.scoula.push.entity.Subscription;

import java.util.List;

public interface SubscriptionMapper {
    void insertSubscription(Subscription sub);
    List<Subscription> getAllSubscriptions();
}
