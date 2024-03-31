package br.com.newsletter.poc.subscriber.service;

import br.com.newsletter.poc.subscriber.model.SubscriberDto;
import jakarta.inject.Inject;

public class SubscriberService {

    @Inject
    private AmazonQueue subscriberQueue;

    public void Subscribe(SubscriberDto subscriberDto) {
        // TODO pegar topico de acordo com a newsllet parametrizada
        // TODO criar mensagem da Queue
        // TODO chamar metodo de Queue
    }

}
