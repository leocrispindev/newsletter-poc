package br.com.newsletter.poc.subscriber.service;

import br.com.newsletter.poc.subscriber.model.QueueMessage;

public interface SubscriberQueue {

    void Send(QueueMessage message);

}
