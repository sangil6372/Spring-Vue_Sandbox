<script setup>
import { ref } from "vue";

// 로그 출력 상태
const logs = ref([]);

function log(msg) {
  logs.value.push(msg);
}

async function subscribeUser() {
  if (!("serviceWorker" in navigator)) {
    log("서비스 워커를 지원하지 않는 브라우저입니다.");
    return;
  }

  const registration = await navigator.serviceWorker.ready;
  log("Service Worker 준비 완료");

  const applicationServerKey = urlBase64ToUint8Array(
    "BKodvh3r5E72nXeA2GlatVYMPf0Ey159zQaMEvQUXWtXPqDd0IuQav-J_PyKm0Kr39kBrDS87TbdWi5FxxnaHJY"
  );

  try {
    const subscription = await registration.pushManager.subscribe({
      userVisibleOnly: true,
      applicationServerKey,
    });
    log("푸시 구독 성공!");
    log(JSON.stringify(subscription, null, 2));

    // 🔥 여기서 서버로 POST
    await fetch("http://localhost:8080/api/push/subscribe", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(subscription),
    });
  } catch (err) {
    log("구독 실패: " + err);
  }
}

// VAPID 키 변환 함수
function urlBase64ToUint8Array(base64String) {
  const padding = "=".repeat((4 - (base64String.length % 4)) % 4);
  const base64 = (base64String + padding).replace(/-/g, "+").replace(/_/g, "/");
  const rawData = window.atob(base64);
  return Uint8Array.from([...rawData].map((char) => char.charCodeAt(0)));
}
</script>

<template>
  <div style="padding: 20px">
    <h1>Push 테스트 페이지</h1>
    <button @click="subscribeUser">Push 구독하기</button>

    <h3>로그</h3>
    <pre
      style="background: #eee; padding: 10px; max-height: 400px; overflow: auto"
    >
      <div v-for="(logMsg, idx) in logs" :key="idx">{{ logMsg }}</div>
    </pre>
  </div>
</template>
