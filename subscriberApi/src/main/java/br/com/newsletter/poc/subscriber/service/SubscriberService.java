package br.com.newsletter.poc.subscriber.service;

import br.com.newsletter.poc.subscriber.model.QueueMessage;
import br.com.newsletter.poc.subscriber.model.SubscriberDto;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class SubscriberService {

    @Inject
    private AmazonQueue subscriberQueue;

    @ConfigProperty(name = "newsletter.topic.prefix", defaultValue = "test-")
    private String topicPrefix;

    public void Subscribe(SubscriberDto subscriberDto) {
        String newsLetterTopicSubscribe = topicPrefix + subscriberDto.newsletter();

        var queueMessage = new QueueMessage(subscriberDto.email(), newsLetterTopicSubscribe);


        subscriberQueue.Send(queueMessage);
    }

}
