package com.example.snapEvent.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue
    private Long boardId;
    @Column
    private String boardName;
    @Column
    private String description;
}
