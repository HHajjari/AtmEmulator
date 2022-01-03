package com.xyz.atmEmulator.bankServer.controller;


import com.xyz.atmEmulator.bankServer.dto.*;
import com.xyz.atmEmulator.bankServer.service.CardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "/api/v1/bank",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class BankController {

    private CardService cardService;

    @Autowired
    public BankController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/isready")
    public ResponseEntity<String> isReady(){

        if(LocalDateTime.now().getSecond()%2==0)
            throw new RuntimeException("server is unavailabel");

        log.info("isReady");

        return ResponseEntity.ok("bank service is up!");
    }

    @PostMapping("/authenticateByPIN")
    public ResponseEntity<Integer> authenticateByPIN(@RequestBody CardAuthByPinDto cardAuthByPinDto) {

        log.info("authByPin {}", cardAuthByPinDto);

        return ResponseEntity.ok(cardService.authenticateByPIN(cardAuthByPinDto));
    }

    @PostMapping("/authByFingerPrint")
    public ResponseEntity<Integer> authByFingerPrint(@RequestBody CardAuthByFingerPrintDto cardAuthByFingerPrintDto) {

        log.info("authByFingerPrint {}", cardAuthByFingerPrintDto);

        return ResponseEntity.ok(cardService.authenticateByFingerPrint(cardAuthByFingerPrintDto));
    }

    @GetMapping("/balance")
    public ResponseEntity<BigDecimal> balance(String cardNumber){

        log.info("balance", cardNumber);

        return ResponseEntity.ok(cardService.getBalance(cardNumber));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Boolean> withdraw(@RequestBody WithdrawDto withdrawDto){

        log.info("withdraw {}", withdrawDto);

        return ResponseEntity.ok(cardService.withdraw(withdrawDto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<Boolean> deposit(@RequestBody DepositDto depositDto){

        log.info("deposit {}", depositDto);

        return ResponseEntity.ok(cardService.deposit(depositDto));
    }
}
