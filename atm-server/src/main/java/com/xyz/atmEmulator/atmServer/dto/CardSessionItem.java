package com.xyz.atmEmulator.atmServer.dto;


import com.xyz.atmEmulator.atmServer.dto.AuthMethod;
import com.xyz.atmEmulator.atmServer.dto.CardState;
import lombok.*;

import javax.naming.AuthenticationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CardSessionItem {
    private String cardNumber;
    private AuthMethod authMethod;
    private CardState cardState;
}
