package com.example.rabbitmqdemo.controller;

import com.example.rabbitmqdemo.dto.User;
import com.example.rabbitmqdemo.publisher.RabbitMQJsonProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class JsonMessageController {
    private final RabbitMQJsonProducer jsonProducer;

    @PostMapping("/publish-json")
    public ResponseEntity<String> sendJsonMessage(@RequestBody User user) {
        jsonProducer.sendJsonMessage(user);
        return ResponseEntity.ok("Json message sent");
    }
}
