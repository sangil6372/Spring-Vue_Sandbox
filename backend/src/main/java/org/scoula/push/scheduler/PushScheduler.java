package org.scoula.push;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PushScheduler {

    private final PushNotificationService pushService;

    public PushScheduler(PushNotificationService pushService) {
        this.pushService = pushService;
    }

    // 매 30초마다 실행 (테스트용)
    @Scheduled(fixedRate = 30000)
    public void testPushNotifications() {
        System.out.println("== Push 테스트 스케줄러 실행 ==");

        // 가짜 DB에서 사용자 구독 정보 꺼내온다고 가정
        List<SubscriptionInfo> subs = new ArrayList<>();

        SubscriptionInfo dummy = new SubscriptionInfo();
        dummy.setEndpoint("https://fcm.googleapis.com/fcm/send/...");
        dummy.setP256dh("...");
        dummy.setAuth("...");
        subs.add(dummy);

        // 가짜 List<알림>을 돌면서 전송
        pushService.sendPushToSubscriptions(subs);
    }
}
