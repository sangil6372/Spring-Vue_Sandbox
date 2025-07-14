package org.scoula.push;

/**
 * Vue에서 pushManager.subscribe() 후
 * 서버로 전달하는 JSON을 매핑하는 DTO
 */
public class SubscriptionRequest {
    private String endpoint;
    private Keys keys;

    public static class Keys {
        private String p256dh;
        private String auth;

        // getters/setters
    }

    // getters/setters
}