package com.tradeflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tradeflow.entity.CounterParty;
import com.tradeflow.repository.CounterPartyRepository;

@Service
public class CounterPartyServiceImpl implements CounterPartyService {
	
	private CounterPartyRepository counterPartyRepository;
	
	public CounterPartyServiceImpl(CounterPartyRepository counterPartyRepository) {
		this.counterPartyRepository = counterPartyRepository;
	}

	@Override
	public List<CounterParty> saveCounterParty(List<CounterParty> counterParty) {
		return counterPartyRepository.saveAll(counterParty);
	}
	
	@Override
	public List<CounterParty> getAllCounterParties(){
		return counterPartyRepository.findAll();
	}

	@Override
	public CounterParty getCounterPartyById(Long id) {
		// TODO Auto-generated method stub
		return counterPartyRepository.findById(id).orElse(null);
	}

}
