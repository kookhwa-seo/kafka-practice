package com.practice.kafka.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PracticeDTO{
    private UUID id;
    private String name;
    int viewCount;

    @Override
    public String toString() {
        return "{" + id.toString() + ", " + name + ", " + viewCount + "}";
    }
}
