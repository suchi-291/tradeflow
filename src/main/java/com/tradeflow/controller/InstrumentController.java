package com.tradeflow.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tradeflow.entity.Instrument;
import com.tradeflow.service.InstrumentService;

@RestController
public class InstrumentController {
		
	private InstrumentService instrumentService;
	
	public InstrumentController(InstrumentService instrumentService) {
		this.instrumentService = instrumentService;
	}
	
	@PostMapping("/instruments")
	public ResponseEntity<List<Instrument>> saveAll(@RequestBody List<Instrument> instruments){
		List <Instrument> saveInstruments =  instrumentService.saveAll(instruments);
		return ResponseEntity.status(HttpStatus.CREATED).body(saveInstruments);
	}
	
	
	@GetMapping("/instruments")
	public List<Instrument> getAllInstruments(){
		return instrumentService.getAllInstruments();
	}
 
	
	@GetMapping("/instruments/{id}")
	public Instrument getInstrumentsById(@PathVariable Long id){
		return instrumentService.getInstrumentById(id);
	}
 
}
