package com.tradeflow.dto;

import com.tradeflow.enums.InstrumentType;
//After trade is saved, we want to return clean output.We do not want to return raw entity because entity may contain lazy-loaded objects, unwanted fields, or internal database structure.

public class InstrumentResponse {
	
	private Long id;
	private String isin;
	private InstrumentType type;
	private String currency;
	
	public InstrumentResponse() {
		
	}

	public InstrumentResponse(Long id, String isin, InstrumentType type, String currency) {
		super();
		this.id = id;
		this.isin = isin;
		this.type = type;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public String getIsin() {
		return isin;
	}

	public InstrumentType getType() {
		return type;
	}

	public String getCurrency() {
		return currency;
	}
	
	

}
