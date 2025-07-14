package org.scoula.push.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * Vue에서 pushManager.subscribe() 후
 * 서버로 전달하는 JSON을 매핑하는 DTO
 */
@Data
public class SubscriptionRequest {
    private String endpoint;
    private Keys keys;

    @Getter
    @Setter
    public static class Keys {
        private String p256dh;
        private String auth;

        // getters/setters
    }

    // getters/setters
}