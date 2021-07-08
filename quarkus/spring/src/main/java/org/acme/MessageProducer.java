package org.acme;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageProducer {

    @Value("${taste.message}")
    String message;

    public String getTaste() {
        return message;
    }
}
