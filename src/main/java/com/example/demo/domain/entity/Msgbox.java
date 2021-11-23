package com.example.demo.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data

@Entity
public class Msgbox {
    @Id
    private int msgboxId;
    private boolean msgboxRead;
    private int msgboxFrom;
    private int msgboxTo;

}
