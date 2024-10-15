package br.com.mangarosa.messages.repositories;

import br.com.mangarosa.messages.Message;
import br.com.mangarosa.messages.interfaces.MessageRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryMessageRepositoryImpl implements MessageRepository {
    private final Map<String, List<Message>> topics = new HashMap<>();

    public InMemoryMessageRepositoryImpl() {
    }

    @Override
    public void append(String topic, Message message) {
        if (this.topics.get(topic) == null) {
            this.topics.put(topic, new ArrayList<>());
        }
        this.topics.get(topic).add(message);
    }

    @Override
    public void consumeMessage(String topic, UUID messageId) {
        List<Message> messages = this.topics.get(topic);
        if (messages == null) {
            throw new IllegalArgumentException(topic + " not found");
        }

        for (Message message : messages) {
            if (Objects.equals(message.getId(), messageId.toString())) {
                if (message.isExperied()) {
                    throw new IllegalStateException("Cannot consume expired message");
                }
                message.setConsumed(true);
                break;
            }
        }
    }

    @Override
    public List<Message> getAllNotConsumedMessagesByTopic(String topic) {
        List<Message> messages = this.topics.get(topic);
        if (messages == null) {
            throw new IllegalArgumentException(topic + " not found");
        }

        return messages.stream()
                .filter(message -> !message.isConsumed() && !message.isExperied())
                .collect(Collectors.toList());
    }

    @Override
    public List<Message> getAllConsumedMessagesByTopic(String topic) {
        List<Message> messages = this.topics.get(topic);
        if (messages == null) {
            throw new IllegalArgumentException(topic + " not found");
        }

        return messages.stream()
                .filter(Message::isConsumed)
                .filter(message -> !message.isExperied())
                .collect(Collectors.toList());
    }
}
