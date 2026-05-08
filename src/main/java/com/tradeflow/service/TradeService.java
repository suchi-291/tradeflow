package com.tradeflow.service;

import java.util.List;

import com.tradeflow.dto.TradeCreateRequest;
import com.tradeflow.dto.TradeResponse;
import com.tradeflow.entity.Trade;
import com.tradeflow.enums.TradeStatus;

public interface TradeService {

	 public TradeResponse createTrade(TradeCreateRequest request);
	 
	 public List<Trade> getAllTrades();
	 
	 public Trade getTradeById(Long id);
	 
	 public List<TradeResponse> getTradeByStatus(TradeStatus status);
}
