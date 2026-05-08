package com.tradeflow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tradeflow.dto.TradeCreateRequest;
import com.tradeflow.dto.TradeResponse;
import com.tradeflow.entity.Trade;
import com.tradeflow.enums.TradeStatus;
import com.tradeflow.service.TradeService;

@RestController
public class TradeController {
	
	private TradeService tradeService;

	public TradeController(TradeService tradeService) {
		this.tradeService = tradeService;
	}
	
	@PostMapping("/trades")
	public ResponseEntity<TradeResponse> createTrade(@RequestBody TradeCreateRequest tradeRequest){  
		System.out.println(tradeRequest);
		TradeResponse response = tradeService.createTrade(tradeRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/trades")
	public List<Trade> getAllTrades(){
		return tradeService.getAllTrades();
	}
	
	@GetMapping("/trades/{id}")
	public Trade getTradeById(@PathVariable Long id){
		return tradeService.getTradeById(id);
	}
	
	@GetMapping("/trades/by-status")
	public ResponseEntity<List<TradeResponse>> getTradesByStatus(
	        @RequestParam TradeStatus status) {

	    List<TradeResponse> response =
	            tradeService.getTradeByStatus(status);

	    return ResponseEntity.ok(response);
	}

}
