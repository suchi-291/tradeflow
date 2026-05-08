package com.tradeflow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeflow.entity.Trade;
import com.tradeflow.enums.TradeStatus;

public interface TradeRepository extends JpaRepository<Trade, Long>{

	List<Trade> findByStatus(TradeStatus status);
}
