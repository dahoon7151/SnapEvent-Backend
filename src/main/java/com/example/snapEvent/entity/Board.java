package com.example.snapEvent.entity;

import com.example.snapEvent.audit.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board extends BaseEntity {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long boardId;
    @Column
    private String boardName;
    @Column
    private String description;
}
