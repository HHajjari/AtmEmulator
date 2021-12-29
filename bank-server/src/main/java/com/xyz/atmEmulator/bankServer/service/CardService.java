package com.xyz.atmEmulator.bankServer.service;

import com.xyz.atmEmulator.bankServer.dto.CardAuthByFingerPrintDto;
import com.xyz.atmEmulator.bankServer.dto.CardAuthByPinDto;
import com.xyz.atmEmulator.bankServer.dto.DepositDto;
import com.xyz.atmEmulator.bankServer.dto.WithdrawDto;
import com.xyz.atmEmulator.bankServer.model.Card;
import com.xyz.atmEmulator.bankServer.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class CardService {

    private CardRepository cardRepository;
    private Integer maxInvalidAuthAttept = 3;

    @Autowired
    public CardService(CardRepository cardRepository) {

        this.cardRepository = cardRepository;
    }

    public BigDecimal getBalance(String cardNumber){

        log.info("getBalance()" + cardNumber);

        Card card = cardRepository.findByCardNumber(cardNumber);

        checkCardIsActive(card);

        return card.getBalance();
    }

    public Integer authenticateByPIN(CardAuthByPinDto cardAuthByPinDto){

        log.info("authenticateByPIN()" + cardAuthByPinDto);

        Card card = cardRepository.findByCardNumber(cardAuthByPinDto.getCard());

        checkCardIsActive(card);

        if(card.getPin().equals(cardAuthByPinDto.getPin())) {

            if(card.getInvalidAuthAttemptCount() > 0){

                card.setInvalidAuthAttemptCount(0);
            }

        }
        else{

            card.setInvalidAuthAttemptCount(card.getInvalidAuthAttemptCount() + 1);

            if(card.getInvalidAuthAttemptCount() == maxInvalidAuthAttept){

                card.setIsActive(false);
            }
        }

        cardRepository.save(card);

        return card.getInvalidAuthAttemptCount();
    }

    public Integer authenticateByFingerPrint(CardAuthByFingerPrintDto cardAuthByFingerPrintDto){

        log.info("authenticateByFingerPrint()" + cardAuthByFingerPrintDto);

        Card card = cardRepository.findByCardNumber(cardAuthByFingerPrintDto.getCard());

        checkCardIsActive(card);

        if(card.getFingerPrint().equals(cardAuthByFingerPrintDto.getFingerprint())) {

            if(card.getInvalidAuthAttemptCount() > 0){

                card.setInvalidAuthAttemptCount(0);
            }
        }
        else{

            card.setInvalidAuthAttemptCount(card.getInvalidAuthAttemptCount() + 1);

            if(card.getInvalidAuthAttemptCount() == maxInvalidAuthAttept){

                card.setIsActive(false);
            }
        }

        cardRepository.save(card);

        return card.getInvalidAuthAttemptCount();
    }

    public Boolean deposit(DepositDto depositDto){

        log.info("deposit()" + depositDto);

        checkAmount(depositDto.getValue());

        Card card = cardRepository.findByCardNumber(depositDto.getCard());

        checkCardIsActive(card);

        card.setBalance(card.getBalance().add(depositDto.getValue()));

        cardRepository.save(card);

        return true;
    }

    public Boolean withdraw(WithdrawDto withdrawDto){

        log.info("withdraw()" + withdrawDto);

        checkAmount(withdrawDto.getValue());

        Card card = cardRepository.findByCardNumber(withdrawDto.getCard());

        checkCardIsActive(card);

        checkWithdrawIsValid(card, withdrawDto.getValue());

        card.setBalance(card.getBalance().subtract(withdrawDto.getValue()));

        cardRepository.save(card);

        return true;
    }

    private void checkCardIsActive(Card card){

        log.info("checkCardIsActive()" + card);

        if(card == null){
            throw new RuntimeException("The requested card does not exist.");
        }

        if(!card.getIsActive()){
            throw new RuntimeException("The requested card is blocked.");
        }
    }

    private void checkAmount(BigDecimal amount){

        log.info("checkAmount()" + amount);

        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new RuntimeException("The amount should be positive.");
    }

    private void checkWithdrawIsValid(Card card, BigDecimal amount){

        log.info("checkWithdrawIsValid()" + card + ";" + amount);

        if(card.getBalance().compareTo(amount)  == -1){
            throw new RuntimeException("The requested amount is greater than current balance.");
        }
    }
}
