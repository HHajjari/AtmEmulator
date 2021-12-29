package com.xyz.atmEmulator.atmServer.service;

import com.xyz.atmEmulator.atmServer.dto.CardAuthByFingerPrintDto;
import com.xyz.atmEmulator.atmServer.dto.CardAuthByPinDto;
import com.xyz.atmEmulator.atmServer.dto.DepositDto;
import com.xyz.atmEmulator.atmServer.dto.WithdrawDto;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;

@Service
@Slf4j
public class CardService {

    @Value("${bank.username}")
    private String username;

    @Value("${bank.password}")
    private String password;

    @Value("${bank.url}")
    private String hostUrl;

    private HttpHeaders getHeaders (){

        String adminuserCredentials = String.format("%s:%s", username, password);
        String encodedCredentials = new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }

    @Retry(name = "bankServerIsReady")
    public String bankServerIsReady(){

        log.info("bankServerIdReady()");

        RestTemplate restTemplate = new RestTemplate();

        final String url = hostUrl + "/api/v1/bank/isready";

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<Void> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);

        return response.getBody();
    }

    @Retry(name = "authenticateByPIN")
    public Integer authenticateByPIN(CardAuthByPinDto cardAuthByPinDto){

        log.info("authenticateByPIN()" + cardAuthByPinDto);

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = hostUrl + "/api/v1/bank/authenticateByPIN";

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<CardAuthByPinDto> httpEntity = new HttpEntity<>(cardAuthByPinDto, httpHeaders);

        ResponseEntity<Integer> response = restTemplate.postForEntity(baseUrl, httpEntity, Integer.class);

        return response.getBody();
    }

    @Retry(name = "authenticateByFingerPrint")
    public Integer authenticateByFingerPrint(CardAuthByFingerPrintDto cardAuthByFingerPrintDto){

        log.info("authenticateByFingerPrint()" + cardAuthByFingerPrintDto);

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = hostUrl + "/api/v1/bank/authByFingerPrint";

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<CardAuthByFingerPrintDto> httpEntity = new HttpEntity<>(cardAuthByFingerPrintDto, httpHeaders);

        ResponseEntity<Integer> result = restTemplate.postForEntity(baseUrl, httpEntity, Integer.class);

        return result.getBody();
    }

    @Retry(name = "deposit")
    public Boolean deposit(DepositDto depositDto){

        log.info("deposit()" + depositDto);

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = hostUrl + "/api/v1/bank/deposit";

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<DepositDto> httpEntity = new HttpEntity<>(depositDto, httpHeaders);

        ResponseEntity<Boolean> result = restTemplate.postForEntity(baseUrl, httpEntity, Boolean.class);

        return result.getBody();
    }

    @Retry(name = "withdraw")
    public Boolean withdraw(WithdrawDto withdrawDto){

        log.info("withdraw()" + withdrawDto);

        RestTemplate restTemplate = new RestTemplate();

        final String baseUrl = hostUrl + "/api/v1/bank/withdraw";

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<WithdrawDto> httpEntity = new HttpEntity<>(withdrawDto, httpHeaders);

        ResponseEntity<Boolean> result = restTemplate.postForEntity(baseUrl, httpEntity, Boolean.class);

        return result.getBody();
    }

    @Retry(name = "getBalance")
    public BigDecimal getBalance(String cardNumber){

        log.info("getBalance()" + cardNumber);

        RestTemplate restTemplate = new RestTemplate();

        final String url = hostUrl + "/api/v1/bank/balance?cardNumber=" + cardNumber;

        HttpHeaders httpHeaders = getHeaders();

        HttpEntity<String> httpEntity = new HttpEntity<>(httpHeaders);

        ResponseEntity<BigDecimal> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, BigDecimal.class);

        return response.getBody();
    }
}
