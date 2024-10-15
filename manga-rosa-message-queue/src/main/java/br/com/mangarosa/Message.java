package br.com.mangarosa.messages;

import br.com.mangarosa.messages.interfaces.Consumer;
import br.com.mangarosa.messages.interfaces.Producer;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Message implements Serializable {
    private String id;
    private Producer producer;
    private final LocalDateTime createdAt;
    private final List<MessageConsumption> consumptionList;
    private boolean isConsumed;
    private String message;

    public Message(Producer producer, String message) {
        setProducer(producer);
        setMessage(message);
        this.createdAt = LocalDateTime.now();
        this.consumptionList = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if (id == null || id.isBlank() || id.isEmpty())
            throw new IllegalArgumentException("The message id can't be null, blank or empty");
        this.id = id;
    }

    public Producer getProducer() {
        return producer;
    }

    private void setProducer(Producer producer) {
        if (producer == null)
            throw new IllegalArgumentException("The message's producer can't be null");
        this.producer = producer;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isConsumed() {
        return isConsumed;
    }

    public void setConsumed(boolean consumed) {
        isConsumed = consumed;
    }

    public String getMessage() {
        return message;
    }

    private void setMessage(String message) {
        if (message == null || message.isBlank() || message.isEmpty())
            throw new IllegalArgumentException("The message content can't be null or empty or blank");
        this.message = message;
    }

    public void addConsumption(Consumer consumer) {
        if (consumer == null)
            throw new IllegalArgumentException("Consumer can't be null in a consumption");
        this.consumptionList.add(new MessageConsumption(consumer));
    }

    public Map<String, String> toMap() throws IllegalAccessException {
        final HashMap<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            Object value = field.get(this);
            if (value != null) {
                map.put(field.getName(), value.toString());
            }
        }
        return map;
    }

    public boolean isExperied() {
        return this.createdAt.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== MESSAGE ============").append("\n");
        sb.append("ID: ").append(this.getId()).append("\n");
        sb.append("Message: ").append(this.getMessage()).append("\n");
        sb.append("Producer: ").append(this.getProducer().name()).append("\n");
        sb.append("CreatedAt: ").append(this.getCreatedAt()).append("\n");
        sb.append("Consumed: ").append(this.isConsumed()).append("\n");
        sb.append("Expired: ").append(this.isExperied()).append("\n");
        return sb.toString();
    }
}
