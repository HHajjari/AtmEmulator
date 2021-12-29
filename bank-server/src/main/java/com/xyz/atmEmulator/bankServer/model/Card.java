package com.xyz.atmEmulator.bankServer.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Card {

    @Id
    private String cardNumber;

    @Column
    private BigDecimal balance;

    @Column
    private Integer invalidAuthAttemptCount;

    @Column
    private Boolean isActive;

    @Column
    private Integer pin;

    @Column
    private String fingerPrint;
}
