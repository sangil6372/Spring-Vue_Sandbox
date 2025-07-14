package org.scoula.push.entity;

import lombok.Data;

/**
 * DB 테이블 subscription 과 1:1 매핑되는 클래스
 * SELECT * FROM subscription 결과를 받거나,
 * INSERT 시 매핑에 사용된다.
 */
@Data
public class Subscription {
    private Long id;
    private String endpoint;
    private String p256dh;
    private String auth;
    private String createdAt;

    // getters/setters
}