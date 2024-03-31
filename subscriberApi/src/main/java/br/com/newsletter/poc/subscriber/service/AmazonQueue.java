package br.com.newsletter.poc.subscriber.service;

import br.com.newsletter.poc.subscriber.model.QueueMessage;
import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
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
    private SQSConnection connection;
    @PostConstruct
    public void InitClient() {
        try {
            this.connection = createConnection();
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
    public void Send(QueueMessage message) {

        try {
            // ensure scalability, resource management, fault tolerance and integrating greater resilience
            Session sendSession = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = sendSession.createQueue(queueName);

            MessageProducer producer = sendSession.createProducer(queue);

            ObjectMessage serializableMessage = sendSession.createObjectMessage(message);

            producer.send(serializableMessage);

            System.out.println("JMS Message " + serializableMessage.getJMSMessageID());

        } catch (JMSException e) {
            throw new RuntimeException(e);
        }


    }
}
