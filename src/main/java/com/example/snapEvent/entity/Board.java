package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long boardId;
    @Column
    private String boardName;
    @Column
    private String description;
}
