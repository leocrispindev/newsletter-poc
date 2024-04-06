package br.com.newsletter.poc.subscriber.controller;

import br.com.newsletter.poc.subscriber.model.SubscriberDto;
import br.com.newsletter.poc.subscriber.service.SubscriberService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Objects;

@ApplicationScoped
public class SubscriberController {

    @Inject
    SubscriberService subscriberService;

    public void HandleSubscriber(SubscriberDto subscriberDto){
        validate(subscriberDto);

        subscriberService.Subscribe(subscriberDto);
    }

    private void validate(SubscriberDto subscriberDto) {
        if (subscriberDto.email().isEmpty()) {
            throw new IllegalArgumentException("Email can not be empty");
        }

        if (subscriberDto.newsletter().isEmpty()) {
            throw new IllegalArgumentException("Newsletter can not be empty");
        }
    }

}
