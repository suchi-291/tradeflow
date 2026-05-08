package com.tradeflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tradeflow.enums.TradeStatus;
//After trade is saved, we want to return clean output.We do not want to return raw entity because entity may contain lazy-loaded objects, unwanted fields, or internal database structure.
// This is what backend returns after creating a trade.
// It combines: Trade basic fields, Buyer details, Seller details, Instrument details, System-generated fields

public class TradeResponse {
	
	private Long id;
	private CounterPartyResponse buyer;
	private CounterPartyResponse seller;
	private InstrumentResponse instrument;
	private LocalDate tradeDate;
	private LocalDate settlementDate;
	private BigDecimal quantity;
	private BigDecimal price;
	private TradeStatus status;
	private Integer version;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	
	public TradeResponse() {
		
	}
	
	

	public TradeResponse(Long id, CounterPartyResponse buyer, CounterPartyResponse seller,
			InstrumentResponse instrument, LocalDate tradeDate, LocalDate settlementDate, BigDecimal quantity,
			BigDecimal price, TradeStatus status, Integer version, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.id = id;
		this.buyer = buyer;
		this.seller = seller;
		this.instrument = instrument;
		this.tradeDate = tradeDate;
		this.settlementDate = settlementDate;
		this.quantity = quantity;
		this.price = price;
		this.status = status;
		this.version = version;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}



	public Long getId() {
		return id;
	}

	public CounterPartyResponse getBuyer() {
		return buyer;
	}

	public CounterPartyResponse getSeller() {
		return seller;
	}

	public InstrumentResponse getInstrument() {
		return instrument;
	}

	public LocalDate getTradeDate() {
		return tradeDate;
	}

	public LocalDate getSettlementDate() {
		return settlementDate;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public TradeStatus getStatus() {
		return status;
	}

	public Integer getVersion() {
		return version;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
	

}
