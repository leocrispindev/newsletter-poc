package br.com.newsletter.poc.subscriber.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public record SubscriberDto(String email, String newsletter) { }
