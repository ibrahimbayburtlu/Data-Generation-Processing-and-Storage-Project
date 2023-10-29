package com.bundle.ibrahimbayburtlu.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Document(collection = "bundle")
public class MongoEntity {

    @Id
    private String id;

    private String content;
    private List<MongoEntity> nestedMessages;

    // Getter ve Setter metotlarını burada ekleyebilirsiniz.


    public MongoEntity() {
        this.id = String.valueOf(UUID.randomUUID());
    }

    public List<MongoEntity> getNestedMessages() {
        return nestedMessages;
    }

    public void setNestedMessages(List<MongoEntity> nestedMessages) {
        this.nestedMessages = nestedMessages;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}