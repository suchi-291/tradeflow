package com.tradeflow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tradeflow.entity.Instrument;
import com.tradeflow.repository.InstrumentRepository;

@Service
public class InstrumentServiceImpl implements InstrumentService{
	
	private InstrumentRepository repository;
	
	public InstrumentServiceImpl(InstrumentRepository repository) {
		this.repository  = repository;
	}

	@Override
	public List<Instrument> saveAll(List<Instrument> instruments) {
		// TODO Auto-generated method stub
		return repository.saveAll(instruments) ;
	}

	@Override
	public List<Instrument> getAllInstruments() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Instrument getInstrumentById(Long id) {
		// TODO Auto-generated method stub
		return repository.findById(id).orElse(null);
	}

}
