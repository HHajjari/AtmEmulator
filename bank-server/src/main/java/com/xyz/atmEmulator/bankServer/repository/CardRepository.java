package com.xyz.atmEmulator.bankServer.repository;

import com.xyz.atmEmulator.bankServer.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    Card findByCardNumber(String cardNumber);
}
