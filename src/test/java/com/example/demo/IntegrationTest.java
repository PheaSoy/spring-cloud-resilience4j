package com.example.demo;

import com.carrotsearch.junitbenchmarks.BenchmarkOptions;
import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import com.example.demo.model.BaseResponse;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockserver.model.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

@Log4j2
public class IntegrationTest extends AbstractIntegrationTest {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate template;

    final Random random = new Random();
    int i = 0;

    @Rule
    public TestRule benchmarkRun = new BenchmarkRule();


    @Test
    @BenchmarkOptions(warmupRounds = 0, concurrency = 1, benchmarkRounds = 200)
    public void testFallback() {
        int gen = 1 + (i++ % 2);

        HttpRequest request = request("/accounts").withMethod("GET");
        getMockServerClient().when(request).respond(response()
                .withBody("{\"msg\":\"Success\",\"msgCode\":\"1\",\"accountNo\":\"1234\",\"phoneNumber\":\"+85567627272\",\"isDeleted\":false}")
                .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .withDelay(TimeUnit.SECONDS, 2));

        log.info("Call -> {}",i);
        ResponseEntity<BaseResponse> r = template.exchange(Uri("orders"), HttpMethod.GET, null, BaseResponse.class, 1);
        log.info("Got body:{}",r.getBody());
        Assertions.assertThat(r.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
    String Uri(String uri){
       return String.format("http://localhost:%d/%s",port,uri);
    }

}
