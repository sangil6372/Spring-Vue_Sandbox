// Push 이벤트를 수신할 때마다 실행되는 부분
self.addEventListener("push", function (event) {
  console.log("Service Worker: 푸시 이벤트 수신", event);

  // 서버에서 보내준 payload가 json 형태라고 가정
  const data = event.data ? event.data.json() : {};

  console.log("푸시 데이터:", data);

  // 실제로 사용자에게 알림을 띄우기
  event.waitUntil(
    self.registration.showNotification(data.title || "알림", {
      body: data.body || "새 알림이 도착했습니다.",
      icon: "/img/icons/icon-192x192.png", // PWA 아이콘
      badge: "/img/icons/icon-72x72.png",
    })
  );
});
