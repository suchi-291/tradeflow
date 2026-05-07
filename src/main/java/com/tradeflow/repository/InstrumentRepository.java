package com.tradeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tradeflow.entity.Instrument;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

}
