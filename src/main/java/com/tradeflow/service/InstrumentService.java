package com.tradeflow.service;

import java.util.List;

import com.tradeflow.entity.Instrument;

public interface InstrumentService {
	
	public List<Instrument> saveAll(List<Instrument> instruments);
	
	public List<Instrument> getAllInstruments();
	
	public Instrument getInstrumentById(Long id);
}
