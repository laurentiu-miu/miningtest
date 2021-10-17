package com.bitcoin.miningtest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class Endpoint {
    @Autowired
    WebClient client;
    @Autowired
    ObjectMapper objectMapper;
    @GetMapping("/getblocktemplate")
    public Mono<String> getblocktemplate() throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree("{\"jsonrpc\": \"1.0\", \"id\": \"curltest\", \"method\": \"getblocktemplate\", \"params\": [{\"mode\": \"template\",\"rules\": [\"segwit\"]}]}");
        return client.post()
                .body(Mono.just(jsonNode),JsonNode.class)
                .retrieve()
                .bodyToMono(String.class);
    }
}
