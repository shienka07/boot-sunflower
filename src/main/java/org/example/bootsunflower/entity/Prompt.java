package org.example.bootsunflower.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;

import java.time.ZonedDateTime;

@Entity
@Data
public class Prompt {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id; // UUID
    @Column(nullable = false)
    String originalText;
    @Column(nullable = false)
    String editedText;
    ZonedDateTime createdAt; // UTC
}