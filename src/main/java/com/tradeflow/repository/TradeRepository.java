package com.tradeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeflow.entity.Trade;

public interface TradeRepository extends JpaRepository<Trade, Long>{

}
