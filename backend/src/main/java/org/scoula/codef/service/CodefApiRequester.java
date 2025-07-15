package org.scoula.codef.service;



import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.scoula.codef.util.RSAUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLOutput;
import java.util.Scanner;

/**
 * CODEF 계좌 연결 + 계좌 조회 전용 서비스
 */
@Service
@RequiredArgsConstructor
public class CodefApiRequester {

    private final CodefTokenService codefTokenService;

    @Value("${codef.public_key}")
    private String publicKey;

    private final ObjectMapper objectMapper = new ObjectMapper();  // JSON 파서

    /**
     * 하드코딩된 은행ID/PW를 사용해서 CODEF connectedId를 발급받고,
     * 그 connectedId로 CODEF 계좌 리스트를 조회한다.
     */
    public String connectAndGetConnectedId() {
        try {
            String bankLoginId = "sangil6372";
            String bankPassword = "p13639475!";
            String organization = "0004";

            String encPwd = RSAUtil.encryptRSA(bankPassword, publicKey);

            String connectUrl = "https://development.codef.io/v1/account/create";
            String connectBody = """
        {
          "accountList": [{
            "countryCode": "KR",
            "businessType": "BK",
            "clientType": "P",
            "organization": "%s",
            "loginType": "1",
            "id": "%s",
            "password": "%s"
          }]
        }
        """.formatted(organization, bankLoginId, encPwd);

            String connectResponse = sendPost(connectUrl, connectBody);
            String decoded = URLDecoder.decode(connectResponse, "UTF-8");
            System.out.println("✅ 계좌 연결 응답: " + decoded);
            return decoded;

        } catch (Exception e) {
            throw new RuntimeException("CODEF 계좌 연결 실패", e);
        }
    }

    public String getAccountList() {
        try {
            String connectedId = "2NUY6uilQ7p852MknDYaWq"; // 임시 테스트용
            String organization = "0004";

            String accountUrl = "https://development.codef.io/v1/kr/bank/p/account/account-list";
            String accountBody = """
        {
          "organization": "%s",
          "connectedId": "%s"
        }
        """.formatted(organization, connectedId);

            String accountResponse = sendPost(accountUrl, accountBody);
            String decoded = URLDecoder.decode(accountResponse, "UTF-8");
            System.out.println("💰 계좌 조회 응답: " + decoded);
            return decoded;

        } catch (Exception e) {
            throw new RuntimeException("CODEF 계좌 조회 실패", e);
        }
    }


    /**
     * CODEF POST 요청 (accessToken 자동 삽입)
     */
    private String sendPost(String urlStr, String jsonBody) throws Exception {
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        System.out.println("accessToken : "+ codefTokenService.getAccessToken());

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "Bearer " + codefTokenService.getAccessToken());
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        try (OutputStream os = conn.getOutputStream()) {
            os.write(jsonBody.getBytes());
        }

        // 응답 읽기
        Scanner sc = new Scanner(conn.getInputStream());
        StringBuilder result = new StringBuilder();
        while (sc.hasNext()) result.append(sc.nextLine());
        return result.toString();
    }
}
