package com.bitcoin.miningtest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MiningtestApplication {
	@Value("${rpcuser:user}")
	public String rpcuser;
	@Value("${rpcuser:password}")
	public String rpcpassword;

	@Bean
	WebClient webClient(){
		return WebClient.builder()
				.baseUrl("http://127.0.0.1:18332/")
				.filter(ExchangeFilterFunctions.basicAuthentication(rpcuser, rpcpassword))
				.build();
	}
	public static void main(String[] args) {
		SpringApplication.run(MiningtestApplication.class, args);
	}

}
