package com.example.snapEvent.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long boardId;
    @Column
    private String boardName;
    @Column
    private String description;
}
