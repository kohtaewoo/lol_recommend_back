package com.example.lol_backend.service;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class RiotService {

    private static final Logger logger = LoggerFactory.getLogger(RiotService.class);

    private final RestTemplate restTemplate = new RestTemplate();

    // ✅ application.yml or Render 환경변수에서 주입
    @Value("${python.server.url}")
    private String pythonBaseUrl;

    @Value("${backend.secret-key}")
    private String backendSecretKey;

    public JSONObject getRecommendations(String riotId) {
        try {
            String url = buildUrl("/recommend", riotId);
            HttpEntity<String> entity = buildRequestEntity();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            return new JSONObject(response.getBody());
        } catch (Exception e) {
            logger.error("추천 정보를 가져오는데 실패했습니다. riotId: {}", riotId, e);
            return new JSONObject().put("error", e.getMessage());
        }
    }

    public boolean checkUserExists(String riotId) {
        try {
            String url = buildUrl("/check", riotId);
            HttpEntity<String> entity = buildRequestEntity();
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
            JSONObject result = new JSONObject(response.getBody());
            return result.optBoolean("exists", false);
        } catch (Exception e) {
            logger.error("사용자 존재 여부 확인에 실패했습니다. riotId: {}", riotId, e);
            return false;
        }
    }

    private String buildUrl(String endpoint, String riotId) {
        String encodedRiotId = URLEncoder.encode(riotId, StandardCharsets.UTF_8);
        return pythonBaseUrl + endpoint + "?riotId=" + encodedRiotId;
    }

    private HttpEntity<String> buildRequestEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Backend-Key", backendSecretKey);
        return new HttpEntity<>(headers);
    }
}
