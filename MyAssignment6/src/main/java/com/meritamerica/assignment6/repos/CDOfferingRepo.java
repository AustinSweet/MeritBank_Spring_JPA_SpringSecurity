package com.meritamerica.assignment6.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.CDOffering;

public interface CDOfferingRepo extends JpaRepository<CDOffering, Integer>{

}
