package org.scoula.push.service;

import org.scoula.push.entity.Subscription;
import org.scoula.push.mapper.SubscriptionMapper;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class SubscriptionService {
    private final SubscriptionMapper mapper;

    public SubscriptionService(SubscriptionMapper mapper) {
        this.mapper = mapper;
    }

    public void save(Subscription sub) {
        mapper.insertSubscription(sub);
    }

    public List<Subscription> findAll() {
        return mapper.getAllSubscriptions();
    }
}
