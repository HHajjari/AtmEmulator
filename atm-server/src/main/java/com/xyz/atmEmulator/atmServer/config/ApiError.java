package com.xyz.atmEmulator.atmServer.config;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
}
