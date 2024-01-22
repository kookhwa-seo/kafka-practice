package com.practice.kafka.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity(name = "practice")
public class Practice {
    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "viewCount")
    int viewCount;

    @Builder
    public Practice(UUID id, String name, int viewCount) {
        this.id = id;
        this.name = name;
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "{" + id.toString() + ", " + name + ", " + viewCount + "}";
    }
}
