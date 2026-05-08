package com.tradeflow.mapper;

import org.springframework.stereotype.Component;

import com.tradeflow.dto.CounterPartyResponse;
import com.tradeflow.dto.InstrumentResponse;
import com.tradeflow.dto.TradeResponse;
import com.tradeflow.entity.CounterParty;
import com.tradeflow.entity.Instrument;
import com.tradeflow.entity.Trade;

@Component
public class TradeMapper {
	
	public TradeResponse toTradeResponse(Trade trade) {
		
		CounterPartyResponse buyerResponse = toCounterPartyResponse(trade.getBuyer());
		CounterPartyResponse sellerResponse = toCounterPartyResponse(trade.getSeller());
		InstrumentResponse instrumentResponse = toInstrumentResponse(trade.getInstrument());
		
		return new TradeResponse(trade.getId(), buyerResponse, sellerResponse, instrumentResponse, trade.getTradeDate(), trade.getSettlementDate(), trade.getQuantity(), trade.getPrice(), trade.getStatus(), trade.getVersion(), trade.getCreatedAt(),trade.getUpdatedAt());
	}

	public CounterPartyResponse toCounterPartyResponse(CounterParty counterParty) {
		return new CounterPartyResponse(counterParty.getId(), counterParty.getLei(), counterParty.getName());
	}
	
	public InstrumentResponse toInstrumentResponse(Instrument instrument) {
		return new InstrumentResponse(instrument.getId(),instrument.getIsin(),instrument.getType(),instrument.getCurrency());
	}
	
}



