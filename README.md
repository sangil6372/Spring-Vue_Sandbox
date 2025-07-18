# 📌 프로젝트 주요 커밋 요약

## 🚀 1. 웹 푸시 알림 기능 추가

- 브라우저에서 `PushManager.subscribe()` 를 통해
  VAPID 기반의 endpoint, p256dh, auth 키를 생성하고,
  이를 서버에 POST하여 구독 정보를 DB에 저장.

- Spring에서 Scheduler가 주기적으로
  DB의 구독 정보를 조회해 WebPush 서버(FCM / Mozilla)에 HTTP POST 전송.

- 브라우저 Service Worker에서 `push` 이벤트를 수신해
  `showNotification()` 으로 OS 알림을 표시.

---
## ⚙️ 2. DB 및 MyBatis 연동

- `subscription` 테이블을 설계하여 endpoint, p256dh, auth를 저장.
- MyBatis Mapper (`SubscriptionMapper`) 와 XML을 통해
  구독 정보 INSERT 및 SELECT 쿼리 수행.

- 서비스 계층(`SubscriptionService`)을 통해
  컨트롤러와 스케줄러가 MyBatis Mapper를 간접 호출.

---
## 🔔 3. Spring Scheduler 기반 푸시 전송

- `@EnableScheduling` 을 RootConfig에 추가하여
  `@Scheduled` 를 활성화.

- `PushScheduler` 클래스에서 1분마다 DB를 조회하여
  등록된 구독자들에게 알림을 발송.

---
## 🔐 4. 보안 설정 (JWT)

- `SecurityConfig` 에 JWT 기반 필터와 시큐리티 설정을 추가.
- `JwtUsernamePasswordAuthenticationFilter` 를 통해
  요청 시 JWT 인증 처리.

---
## 🌐 5. Vue 프론트엔드

- `main.js`에서 Service Worker를 등록하여
  웹 푸시를 수신할 수 있도록 구성.

- `/push-test` 페이지에서
  `Push 구독하기` 버튼을 누르면 `pushManager.subscribe()` 가 동작,
  서버로 구독 정보를 POST 요청.

---
## ⚙️ 6. 개발 환경 및 설정

- RootConfig에 `@MapperScan` 과 `@EnableScheduling` 추가로
  MyBatis와 스케줄링 기능을 활성화.

- `application.properties` 및 `build.gradle` 에서
  datasource, BouncyCastle 등의 의존성과 설정 관리.

- BouncyCastle Provider를 `@PostConstruct` 에서 등록하여
  WebPush에서 EC 키 로딩을 가능하게 함.

---
