package com.tradeflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tradeflow.entity.CounterParty;

@Repository
public interface CounterPartyRepository extends JpaRepository<CounterParty, Long>{

}
