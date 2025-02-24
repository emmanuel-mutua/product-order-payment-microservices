package com.emmutua.applicationGateway.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Log4j2
public class FallBackController {
    @RequestMapping("/fallback")
    Mono<String> fallback(@RequestHeader(value = "x-failed-url", required = false) String failedUrl) {
        log.info("use alt payment service: {}-----------------------", failedUrl);
        return Mono.just("use alt payment service");
    }
}
