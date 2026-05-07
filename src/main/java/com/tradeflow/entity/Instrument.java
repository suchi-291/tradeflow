package com.tradeflow.entity;

import java.util.List;

import com.tradeflow.enums.InstrumentType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="instrument")
public class Instrument {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String isin;
	
	@Enumerated(EnumType.STRING)
	private InstrumentType type;
	private String currency;

	public Instrument() {
		
	}

	public Instrument(Long id, String isin, InstrumentType type, String currency) {
		super();
		this.id = id;
		this.isin = isin;
		this.type = type;
		this.currency = currency;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsin() {
		return isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public InstrumentType getType() {
		return type;
	}

	public void setType(InstrumentType type) {
		this.type = type;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "Instrument [id=" + id + ", isin=" + isin + ", type=" + type + ", currency=" + currency + "]";
	}
	
//	@OneToMany(mappedBy="instrument")
//	private List<Trade> trades;
	
	
}
