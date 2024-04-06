package br.com.newsletter.poc.subscriber.service;

import br.com.newsletter.poc.subscriber.model.QueueMessage;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.quarkus.logging.Log;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.Json;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.jms.*;

@ApplicationScoped
public class AmazonQueue implements SubscriberQueue {

    @ConfigProperty(name = "aws.user.access_key_id")
    private String accessKeyId;

    @ConfigProperty(name = "aws.user.secret_access_key")
    private String secretAccessKey;

    @ConfigProperty(name = "aws.sqs.queue.name")
    private String queueName;

    @ConfigProperty(name = "aws.sqs.region")
    private String queueRegion;

    private SQSConnection sqsConnection;

    private static final ObjectMapper jsonMapper = new ObjectMapper();
    @PostConstruct
    public void InitClient() {
        try {
            this.sqsConnection = createConnection();
        }catch (JMSException e) {
            System.out.println(e.getMessage());
        }

    }

    private SQSConnection createConnection() throws JMSException {

        var connectionFactory =  new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard().withRegion(queueRegion).build()
        );


        return connectionFactory.createConnection(accessKeyId, secretAccessKey);
    }
    @Override
    public void Send(QueueMessage queueMessage) {

        try {
            // ensure scalability, resource management, fault tolerance and integrating greater resilience
            Session sendSession = sqsConnection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = sendSession.createQueue(queueName);

            MessageProducer producer = sendSession.createProducer(queue);

            var message = jsonMapper.writeValueAsString(queueMessage);

            var serializableMessage = sendSession.createTextMessage(message);

            producer.send(serializableMessage);

            Log.infof("Send Message: %s", serializableMessage.getJMSMessageID());

        } catch (JMSException e) {
            Log.errorf(e, "Error on create JMS message");
            throw new RuntimeException(e);

        } catch (JsonProcessingException e) {
            Log.errorf(e, "Error on create json message");

            throw new RuntimeException(e);
        }


    }
}
