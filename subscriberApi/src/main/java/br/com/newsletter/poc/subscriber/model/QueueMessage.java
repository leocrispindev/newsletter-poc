package br.com.newsletter.poc.subscriber.model;

import java.io.Serializable;

public record QueueMessage(String email, String subscribeTopic) implements Serializable {
}
