package com.tradeflow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

//Your Trade entity has: private CounterParty buyer; private CounterParty seller; private Instrument instrument; But Postman cannot comfortably send full objects. So we create a request DTO. This class represents: What user sends while creating a trade. It does not contain: id,status, version, createdAt, updatedAt, because system/database controls those. DTO means: A class used only for API input/output.

public class TradeCreateRequest {

	private Long buyerId;
	private Long sellerId;
	private Long instrumentId;
	private LocalDate tradeDate;
	private LocalDate settlementDate;	
	private BigDecimal quantity;
	private BigDecimal price;
	
	
	public TradeCreateRequest() {
		
	}


	public Long getBuyerId() {
		return buyerId;
	}


	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}


	public Long getSellerId() {
		return sellerId;
	}


	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}


	public Long getInstrumentId() {
		return instrumentId;
	}


	public void setInstrumentId(Long instrumentId) {
		this.instrumentId = instrumentId;
	}


	public LocalDate getTradeDate() {
		return tradeDate;
	}


	public void setTradeDate(LocalDate tradeDate) {
		this.tradeDate = tradeDate;
	}


	public LocalDate getSettlementDate() {
		return settlementDate;
	}


	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}


	public BigDecimal getQuantity() {
		return quantity;
	}


	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	
}
