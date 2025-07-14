package org.scoula.push.controller;


import org.scoula.push.dto.SubscriptionRequest;
import org.scoula.push.entity.Subscription;
import org.scoula.push.service.SubscriptionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/push")
public class PushController {

    private final SubscriptionService service;

    public PushController(SubscriptionService service) {
        this.service = service;
    }

    @PostMapping("/subscribe")
    public String saveSubscription(@RequestBody SubscriptionRequest req) {
        // SubscriptionRequest DTO -> Subscription Entity 변환
        Subscription sub = new Subscription();
        sub.setEndpoint(req.getEndpoint());
        sub.setP256dh(req.getKeys().getP256dh());
        sub.setAuth(req.getKeys().getAuth());

        service.save(sub);
        return "saved";
    }
}
