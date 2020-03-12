package com.example.demo;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class ApplicationTests {

	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void testOK(){
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(buildUri()+"/greeting/dara",String.class);
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void testFallBack_Status_still_200(){
		ResponseEntity<String> responseEntity = testRestTemplate.getForEntity(buildUri()+"/greeting/x_error",String.class);
		Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		Assertions.assertThat(responseEntity.getBody()).isEqualTo("Fallback message");
	}


	String buildUri(){
		return "http://localhost:"+port;
	}


}
