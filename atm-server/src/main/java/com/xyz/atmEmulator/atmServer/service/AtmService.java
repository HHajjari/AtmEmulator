package com.xyz.atmEmulator.atmServer.service;

import com.xyz.atmEmulator.atmServer.dto.CardSessionItem;
import com.xyz.atmEmulator.atmServer.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@Slf4j
public class AtmService {


    private CardService cardService;
    private SimpleSession<String, CardSessionItem> session;

    @Autowired
    public AtmService(CardService cardService, SimpleSession<String, CardSessionItem> session) {
        this.cardService = cardService;
        this.session = session;
    }

    public String isReady(){

        log.info("isReady()");

        return "atm is up!";
    }

    public String insertCard(InsertCardDto insertCardDto){

        log.info("insertCard()" + insertCardDto);

        String card = insertCardDto.getCard();

        checkCardIsNotInSlot(card);

        insertIntoSlot(card);

        return "Card was inserted successfully. Please set authetntication method as : 1-By PIN 2-By Fingerprint";
    }

    public String setAuthMethod(CardSetAuthMethodDto cardSetAuthMethodDto){

        log.info("setAuthMethod()" + cardSetAuthMethodDto);

        String card = cardSetAuthMethodDto.getCard();

        checkCardValidated(card);

        setAuthMethod(card, cardSetAuthMethodDto.getAuthMethod());

        return "Authentication method was set to " + cardSetAuthMethodDto.getAuthMethod().toString() + ". You can now proceed to authenticate by your " + cardSetAuthMethodDto.getAuthMethod() + ".";
    }

    public String authByPin(CardAuthByPinDto cardAuthByPinDto) {

        log.info("authByPin()" + cardAuthByPinDto);

        String card = cardAuthByPinDto.getCard();

        checkCardValidatedAndNotAuthenticated(card);

        if (getCardInSlot(card).getAuthMethod() != AuthMethod.PIN) {
            throw new RuntimeException("The choosed authentication method is not selected in the previous step");
        }

        //***************CALL authetication servervice***************
        Integer failedAuthCount = cardService.authenticateByPIN(cardAuthByPinDto);

        Boolean cardAuthResult = failedAuthCount == 0;

        if (cardAuthResult) {

            getCardInSlot(card).setCardState(CardState.AUTHENTICATED);

            return "Card was authenticated successfully. You can able to do the following commands: 1-Check balance 2-Deposit 3-Withdrawal.";
        }
        else{

            return "Invalid PIN. You have had " + failedAuthCount + " login attempt. If it reaches to 3 your card will be blocked.";
        }
    }

    public String authByFingerPrint(CardAuthByFingerPrintDto cardAuthByFingerPrintDto) {

        log.info("authByFingerPrint()" + cardAuthByFingerPrintDto);

        String card = cardAuthByFingerPrintDto.getCard();

        checkCardValidated(card);

        CardSessionItem cardSessionItem = session.get(card);

        if (cardSessionItem.getCardState() == CardState.AUTHENTICATED) {
            throw new RuntimeException("Card has already been authenticated");
        }

        if (cardSessionItem.getAuthMethod() != AuthMethod.FINGER_PRINT) {
            throw new RuntimeException("The choosed authentication method is not selected in the previous step");
        }

        //***************CALL authetication servervice***************
        Integer failedAuthCount = cardService.authenticateByFingerPrint(cardAuthByFingerPrintDto);

        Boolean cardAuthResult = failedAuthCount == 0;

        if (cardAuthResult) {

            cardSessionItem.setCardState(CardState.AUTHENTICATED);

            return "Card was authenticated successfully. You can able to do the following commands: 1-Check balance 2-Deposit 3-Withdrawal.";
        }
        else{

            return "Invalid Fingerprint. You have had " + failedAuthCount + " login attempt. If it reaches to 3 your card will be blocked.";
        }
    }

    public String balance(BalanceDto balanceDto){

        log.info("balance()" + balanceDto);

        checkCardAuthenticated(balanceDto.getCard());

        //Send balance query and retrive balance
        BigDecimal balanceValue = cardService.getBalance(balanceDto.getCard());

        return "Your balance is " + balanceValue;
    }

    public String withdraw(WithdrawDto withdrawDto){

        log.info("withdraw()" + withdrawDto);

        checkCardAuthenticated(withdrawDto.getCard());

        Boolean withdrawResult = cardService.withdraw(withdrawDto);

        return "Withdraw done.";
    }

    public String deposit(DepositDto depositDto){

        log.info("deposit()" + depositDto);

        checkCardAuthenticated(depositDto.getCard());

        //Send deposti commadn
        Boolean depositResult = cardService.deposit(depositDto);

        return "Deposit done.";
    }

    public String exit(CardExitDto cardExitDto){

        log.info("exit()" + cardExitDto);

        String card = cardExitDto.getCard();

        checkCardValidated(card);

        exit(card);

        return "Card was exited successfully.";
    }

    private void checkCardIsNotInSlot(String card){

        log.info("_checkCardIsNotInSlot()" + card);

        if (session.containsKey(card)) {
            throw new RuntimeException("Given card is already in progress");
        }
    }

    private void checkCardValidated(String card){

        log.info("_checkCardValidated()" + card);

        if (!session.containsKey(card)) {
            throw new RuntimeException("Given card is not in progress");
        }
    }

    private void checkCardValidatedAndNotAuthenticated(String card){

        log.info("_checkCardValidatedAndNotAuthenticated()" + card);

        if (!session.containsKey(card)) {
            throw new RuntimeException("Given card is not in progress");
        }

        CardSessionItem cardSessionItem = session.get(card);

        if (cardSessionItem.getCardState() != CardState.VALIDATED) {
            throw new RuntimeException("Card is already authenticated.");
        }
    }

    private void checkCardAuthenticated(String card){

        log.info("_checkCardAuthenticated()" + card);

        if (!session.containsKey(card)) {
            throw new RuntimeException("Given card is not in progress");
        }

        CardSessionItem cardSessionItem = session.get(card);

        if (cardSessionItem.getCardState() != CardState.AUTHENTICATED) {
            throw new RuntimeException("Card should be authenticated first.");
        }
    }

    private void insertIntoSlot(String card){

        log.info("_insertIntoSlot()" + card);

        session.put(card, new CardSessionItem(card, null, CardState.VALIDATED));
    }

    private void setAuthMethod(String card, AuthMethod authMethod){

        log.info("_setAuthMethod()" + card + ";" + authMethod);

        session.get(card).setAuthMethod(authMethod);
    }

    private CardSessionItem getCardInSlot(String card){

        log.info("_getCardInSlot()" + card);

        return session.get(card);
    }

    private void exit(String card){

        log.info("_exit()" + card);

        session.remove(card);;
    }
}
