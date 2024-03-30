package br.com.newsletter.poc.subscriber.controller;

import br.com.newsletter.poc.subscriber.model.SubscriberDto;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Objects;

@ApplicationScoped
public class SubscriberController {

    public void HandleSubscriber(SubscriberDto subscriberDto){
        validate(subscriberDto);



    }

    private void validate(SubscriberDto subscriberDto) {
        if (subscriberDto.email().isEmpty()) {
            throw new IllegalArgumentException("Email can not be empty");
        }

        if (subscriberDto.newsletter().isEmpty()) {
            throw new IllegalArgumentException("Newsletter cannot be empty");
        }
    }

}
