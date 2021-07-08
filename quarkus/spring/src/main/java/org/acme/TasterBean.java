package org.acme;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TasterBean {

    private final MessageProducer messageProducer;

    @Autowired
    @Qualifier("noopFunction")
    StringFunction noopStringFunction;

    @Autowired
    @Qualifier("capitalizeFunction")
    StringFunction capitalizerStringFunction;

    @Value("${taste.suffix:!}")
    String suffix;

    public TasterBean(MessageProducer messageProducer) {
        this.messageProducer = messageProducer;
    }

    public String taste(String fruitName) {
        final String initialValue = fruitName + ": " + messageProducer.getTaste() + " " + suffix;
        return noopStringFunction.andThen(capitalizerStringFunction).apply(initialValue);
    }
}
