package com.tradeflow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradeflow.entity.CounterParty;
import com.tradeflow.service.CounterPartyService;

@RestController
public class CounterPartyController {
	
	private CounterPartyService counterPartyService;
	
	public CounterPartyController(CounterPartyService counterPartyService) {
		this.counterPartyService = counterPartyService;
	}
	
	@PostMapping("/counterparties")
	public ResponseEntity<List<CounterParty>> save(@RequestBody List<CounterParty> counterParty) {
		List<CounterParty> savedCounterParties =  counterPartyService.saveCounterParty(counterParty);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedCounterParties);
	}
	
	@GetMapping("/counterparties")
	public List<CounterParty> getAll(){
		return counterPartyService.getAllCounterParties();
	}
	
	@GetMapping("/counterparties/{id}")
	public CounterParty getCounterPartyById(@PathVariable Long id){
		return counterPartyService.getCounterPartyById(id);
	}
	

}
