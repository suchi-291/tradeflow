package com.tradeflow.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tradeflow.enums.TradeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

@Entity
@Table(name="trade")
public class Trade {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private CounterParty buyer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private  CounterParty seller;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instrument_id")
	private Instrument instrument;
	
	@Column(name="trade_date")
	private LocalDate tradeDate;
	
	@Column(name="settlement_date")
	private LocalDate settlementDate;
	
	private BigDecimal quantity;
	
	private BigDecimal price;
	
	@Enumerated(EnumType.STRING)
	private TradeStatus status;
	
	@Version
	private Integer version;
	
	@Column(name="created_at")
	private LocalDateTime createdAt;
	
	@Column(name="updated_at")
	private LocalDateTime updatedAt;
	
	
	public Trade() {
		
	}


	public Trade(Long id, CounterParty buyer, CounterParty seller, Instrument instrument, LocalDate tradeDate,
			LocalDate settlementDate, BigDecimal quantity, BigDecimal price, TradeStatus status, Integer version,
			LocalDateTime createdAt, LocalDateTime updatedAt) {
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


	public void setId(Long id) {
		this.id = id;
	}


	public CounterParty getBuyer() {
		return buyer;
	}


	public void setBuyer(CounterParty buyer) {
		this.buyer = buyer;
	}


	public CounterParty getSeller() {
		return seller;
	}


	public void setSeller(CounterParty seller) {
		this.seller = seller;
	}


	public Instrument getInstrument() {
		return instrument;
	}


	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
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


	public TradeStatus getStatus() {
		return status;
	}


	public void setStatus(TradeStatus status) {
		this.status = status;
	}


	public Integer getVersion() {
		return version;
	}


	public void setVersion(Integer version) {
		this.version = version;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
	@PrePersist
	public void beforeSave() {
		this.createdAt = LocalDateTime.now();
		this.updatedAt  = LocalDateTime.now();
		
		if(this.status == null) {
			this.status = TradeStatus.NEW;
		}
	}
	
	
	@PreUpdate
	public void beforeUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
