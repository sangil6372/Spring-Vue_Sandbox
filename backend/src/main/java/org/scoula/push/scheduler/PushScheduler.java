package org.scoula.push.scheduler;

import org.scoula.push.entity.Subscription;
import org.scoula.push.service.PushNotificationService;
import org.scoula.push.service.SubscriptionService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PushScheduler {

    private final SubscriptionService service;
    private final PushNotificationService pushService;

    public PushScheduler(SubscriptionService service, PushNotificationService pushService) {
        this.service = service;
        this.pushService = pushService;
    }

    @Scheduled(cron = "0/10 * * * * *") // 1초마다
    public void sendScheduledPush() {
        List<Subscription> subs = service.findAll();
        for (Subscription s : subs) {
            try {
                pushService.sendPush(s, "스케줄 알림", "DB에서 꺼내 10초마다 보내는 알림!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}