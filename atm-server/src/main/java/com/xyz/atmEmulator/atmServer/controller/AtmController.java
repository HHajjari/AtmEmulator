package com.xyz.atmEmulator.atmServer.controller;

import com.xyz.atmEmulator.atmServer.dto.*;
import com.xyz.atmEmulator.atmServer.service.AtmService;
import com.xyz.atmEmulator.atmServer.service.CardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/atm")
@Api(value="ATM Service api endpoint")
@Slf4j
public class AtmController {

    private AtmService atmService;
    private CardService cardService;

    @Autowired
    public AtmController(AtmService atmService, CardService cardService) {
        this.atmService = atmService;
        this.cardService = cardService;
    }


    @GetMapping("/isready")
    public ResponseEntity<String> isReady(){

        log.info("isReady");

        String atmIsReady = atmService.isReady();
        String bankIsReady = cardService.bankServerIsReady();

        return ResponseEntity.ok(atmIsReady + ";" + bankIsReady);
    }

    @PostMapping("/insertCard")
    public ResponseEntity<String> insertCard(@Valid @RequestBody InsertCardDto insertCardDto){

        log.info("insertCard {}", insertCardDto);

        return ResponseEntity.ok(atmService.insertCard(insertCardDto));
    }

    @PostMapping("/setAuthMethod")
    public ResponseEntity<String> setAuthMethod(@Valid @RequestBody CardSetAuthMethodDto cardSetAuthMethodDto){

        log.info("setAuthMethod {}", cardSetAuthMethodDto);

        return ResponseEntity.ok(atmService.setAuthMethod(cardSetAuthMethodDto));
    }

    @PostMapping("/authByPIN")
    public ResponseEntity<String> authByPin(@Valid @RequestBody CardAuthByPinDto cardAuthByPinDto) {

        log.info("authByPin {}", cardAuthByPinDto);

        return ResponseEntity.ok(atmService.authByPin(cardAuthByPinDto));
    }

    @PostMapping("/authByFingerPrint")
    public ResponseEntity<String> authByFingerPrint(@Valid @RequestBody CardAuthByFingerPrintDto cardAuthByFingerPrintDto) {

        log.info("authByFingerPrint {}", cardAuthByFingerPrintDto);

        return ResponseEntity.ok(atmService.authByFingerPrint(cardAuthByFingerPrintDto));
    }

    @GetMapping("/balance")
    public ResponseEntity<String> balance(@Valid BalanceDto balanceDto){

        log.info("balance {}", balanceDto);

        return ResponseEntity.ok(atmService.balance(balanceDto));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody WithdrawDto withdrawDto){

        log.info("withdraw {}", withdrawDto);

        return ResponseEntity.ok(atmService.withdraw(withdrawDto));
    }

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositDto depositDto){

        log.info("deposit {}", depositDto);

        return ResponseEntity.ok(atmService.deposit(depositDto));
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@Valid @RequestBody CardExitDto cardExitDto){

        log.info("exit {}", cardExitDto);

        return ResponseEntity.ok(atmService.exit(cardExitDto));
    }

}
