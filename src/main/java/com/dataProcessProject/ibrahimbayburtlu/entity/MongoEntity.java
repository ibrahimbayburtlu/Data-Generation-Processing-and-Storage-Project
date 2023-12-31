package com.dataProcessProject.ibrahimbayburtlu.entity;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "bundle")
public class MongoEntity {

    @Id
    private String id;

    private String content;
    private List<MongoEntity> nestedMessages;

    public MongoEntity() {
        this.id = String.valueOf(UUID.randomUUID());
        this.nestedMessages = new ArrayList<>();
    }

    public List<MongoEntity> getNestedMessages() {
        return nestedMessages;
    }


    public void addNestedMessage(MongoEntity nestedMessage) {
        if (this.nestedMessages == null) {
            this.nestedMessages = new ArrayList<>();
        }
        this.nestedMessages.add(nestedMessage);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
