package org.scoula.push.service;

import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Utils;
import org.apache.http.HttpResponse;
import org.scoula.push.entity.Subscription;
import org.springframework.stereotype.Service;

@Service
public class PushNotificationService {

    private static final String PUBLIC_KEY = "BKodvh3r5E72nXeA2GlatVYMPf0Ey159zQaMEvQUXWtXPqDd0IuQav-J_PyKm0Kr39kBrDS87TbdWi5FxxnaHJY";
    private static final String PRIVATE_KEY = "QkY2IH3aEMLmBZdhsif7mcQdEh32CVGjHFtH8p4UZuE";

    public void sendPush(Subscription sub, String title, String body) throws Exception {
        String payload = "{\"title\":\"" + title + "\",\"body\":\"" + body + "\"}";

        PushService pushService = new PushService();
        pushService.setPublicKey(Utils.loadPublicKey(PUBLIC_KEY));
        pushService.setPrivateKey(Utils.loadPrivateKey(PRIVATE_KEY));

        Notification notification = new Notification(
                sub.getEndpoint(),
                sub.getP256dh(),
                sub.getAuth(),
                payload
        );

        HttpResponse response = pushService.send(notification);
        try {
            System.out.println("푸시 전송 상태: " + response.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}