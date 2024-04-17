package com.example.snapevent.entity;

import com.example.snapevent.entity.audit.BaseEntity;
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
