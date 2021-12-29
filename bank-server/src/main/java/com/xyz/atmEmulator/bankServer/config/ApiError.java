package com.xyz.atmEmulator.bankServer.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
}
