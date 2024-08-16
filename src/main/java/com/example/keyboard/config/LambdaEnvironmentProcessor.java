package com.example.keyboard.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.nio.charset.StandardCharsets;
import java.util.Map;


public class LambdaEnvironmentProcessor implements EnvironmentPostProcessor {

    private final AWSLambda awsLambda;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LambdaEnvironmentProcessor() {

        // 자격 증명 명시적 설정
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(
                System.getenv("AWS_ACCESS_KEY_ID"),
                System.getenv("AWS_SECRET_ACCESS_KEY")
        );
        // AWSLambda 클라이언트 생성 시 명시적으로 지역 설정
        this.awsLambda = AWSLambdaClientBuilder.standard()
                .withRegion(Regions.AP_NORTHEAST_2)  // 원하는 지역을 설정 (예: ap-northeast-2)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            // Lambda 호출하여 시크릿 정보 가져오기
            InvokeRequest invokeRequest = new InvokeRequest()
                    .withFunctionName("lamdaBucket") // Lambda 함수 이름 (실제 Lambda 함수 이름을 사용)
                    .withPayload("{}"); // 빈 페이로드 전송

            InvokeResult invokeResult = awsLambda.invoke(invokeRequest);
            String resultJson = new String(invokeResult.getPayload().array(), StandardCharsets.UTF_8);

            // JSON으로 응답 파싱
            Map<String, Object> lambdaResponse = objectMapper.readValue(resultJson, Map.class);

            // 환경 변수로 설정// 상태 코드 확인
            if ((int) lambdaResponse.get("statusCode") == 200) {
                // body 내부의 값 추출
                Map<String, Object> secrets = (Map<String, Object>) lambdaResponse.get("body");
//                System.out.println(secrets);

                // 환경 변수로 설정
                environment.getPropertySources().addFirst(new MapPropertySource("lambdaSecrets", secrets));
            } else {
                throw new RuntimeException("Lambda call failed with status code: " + lambdaResponse.get("statusCode"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load secrets from Lambda", e);
        }
    }
}