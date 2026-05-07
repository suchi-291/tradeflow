package com.tradeflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tradeflow.entity.CounterParty;

public interface CounterPartyService {

	public List<CounterParty> saveCounterParty(List<CounterParty> counterParty);
	
	public List<CounterParty> getAllCounterParties();
	
	public CounterParty getCounterPartyById(Long id);
}
