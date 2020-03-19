package com.example.demo;

import com.carrotsearch.junitbenchmarks.BenchmarkRule;
import org.jetbrains.annotations.NotNull;
import org.junit.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockserver.client.server.MockServerClient;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.MockServerContainer;
import org.testcontainers.shaded.com.google.common.collect.ImmutableList;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(initializers = {AbstractIntegrationTest.Initializer.class})
public class AbstractIntegrationTest {

    @ClassRule
    public static MockServerContainer mockServerContainer = new MockServerContainer();

    public static MockServerClient mockServerClient;

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        public void initialize(@NotNull ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(new ImmutableList.Builder<String>()
                    .add("externalService.url=" + mockServerContainer.getEndpoint())
                    .build()).applyTo(configurableApplicationContext);
            ConfigurationPropertySources.attach(configurableApplicationContext.getEnvironment());
        }
    }

    @Before
    public void init() {
        mockServerContainer.start();
        mockServerClient = new MockServerClient(mockServerContainer.getContainerIpAddress(), mockServerContainer.getServerPort());
    }

    public MockServerContainer getMockServerContainer() {
        return mockServerContainer;
    }


    public  MockServerClient getMockServerClient() {
        return mockServerClient;
    }
}
