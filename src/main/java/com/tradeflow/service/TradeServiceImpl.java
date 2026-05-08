package com.tradeflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tradeflow.dto.TradeCreateRequest;
import com.tradeflow.dto.TradeResponse;
import com.tradeflow.entity.CounterParty;
import com.tradeflow.entity.Instrument;
import com.tradeflow.entity.Trade;
import com.tradeflow.enums.TradeStatus;
import com.tradeflow.mapper.TradeMapper;
import com.tradeflow.repository.CounterPartyRepository;
import com.tradeflow.repository.InstrumentRepository;
import com.tradeflow.repository.TradeRepository;

@Service
public class TradeServiceImpl implements TradeService{
	
	private final TradeRepository tradeRepository;
	private final CounterPartyRepository counterPartyRepository;
	private final InstrumentRepository instrumentRepository;
	private final TradeMapper tradeMapper;
	
	

	public TradeServiceImpl(TradeRepository tradeRepository, CounterPartyRepository counterPartyRepository,
			InstrumentRepository instrumentRepository, TradeMapper tradeMapper) {
		
		this.tradeRepository = tradeRepository;
		this.counterPartyRepository = counterPartyRepository;
		this.instrumentRepository = instrumentRepository;
		this.tradeMapper = tradeMapper;
	}



	@Override
	public TradeResponse createTrade(TradeCreateRequest request) {
		// TODO Auto-generated method stub
		CounterParty buyer = counterPartyRepository.findById(request.getBuyerId()).orElseThrow();
		CounterParty seller = counterPartyRepository.findById(request.getSellerId()).orElseThrow();
		Instrument instrument = instrumentRepository.findById(request.getInstrumentId()).orElseThrow();
		
		Trade trade = new Trade();
		trade.setBuyer(buyer);
		trade.setSeller(seller);
		trade.setInstrument(instrument);
		trade.setTradeDate(request.getTradeDate());
		trade.setSettlementDate(request.getSettlementDate());
		trade.setQuantity(request.getQuantity());
		trade.setPrice(request.getPrice());
		
		Trade savedTrade = tradeRepository.save(trade);
		return tradeMapper.toTradeResponse(savedTrade);
	}



	@Override
	public List<Trade> getAllTrades() {
		// TODO Auto-generated method stub
		return tradeRepository.findAll();
	}



	@Override
	public Trade getTradeById(Long id) {
		// TODO Auto-generated method stub
		return tradeRepository.findById(id).orElseThrow();
	}



	@Override
	public List<TradeResponse> getTradeByStatus(TradeStatus status) {

	    List<Trade> trades = tradeRepository.findByStatus(status);

	    return trades.stream()
	            .map(trade -> tradeMapper.toTradeResponse(trade))
	            .toList();
	}
	
	
	
	
	

}
