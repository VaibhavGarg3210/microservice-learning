package com.learn.cards.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
class TestingController {

	private final RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/hit")
    public ResponseEntity<?> hitUrl(@RequestParam String url) throws InterruptedException {
//    	String url = "http://localhost:8072/learning/cards/api/contact-info";

        int totalRequests = 5;
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < totalRequests; i++) {
            futures.add(executor.submit(() -> {
                try {
                    ResponseEntity<String> response =
                            restTemplate.getForEntity(url, String.class);
                    return response.getStatusCodeValue();
                } catch (Exception e) {
                	e.printStackTrace();
                    return 500; // treat failures as 500
                }
            }));
        }

        executor.shutdown();
        executor.awaitTermination(2, TimeUnit.MINUTES);

        List<Integer> statusCodes = new ArrayList<>();
        for (Future<Integer> future : futures) {
            try {
                statusCodes.add(future.get());
            } catch (Exception e) {
                statusCodes.add(500);
            }
        }

        return ResponseEntity.ok(statusCodes);
    }
}
