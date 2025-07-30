package com.recime.codingchallenge.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // needed for JPA
@AllArgsConstructor // needed for @Builder
public class Instruction {
    private String instruction;
    private int order;
}
