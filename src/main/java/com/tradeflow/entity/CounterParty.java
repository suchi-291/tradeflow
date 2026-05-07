package com.tradeflow.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class CounterParty {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String lei;
	private String name;
	private LocalDateTime createdAt;
	
	public CounterParty() {
		
	}

	public CounterParty(Long id, String lei, String name, LocalDateTime createdAt) {
		super();
		this.id = id;
		this.lei = lei;
		this.name = name;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLei() {
		return lei;
	}

	public void setLei(String lei) {
		this.lei = lei;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	public void setCreatedAtBeforeSave() {
		this.createdAt = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return "CounterParty [id=" + id + ", lei=" + lei + ", name=" + name + ", createdAt=" + createdAt + "]";
	}
	
	

}
