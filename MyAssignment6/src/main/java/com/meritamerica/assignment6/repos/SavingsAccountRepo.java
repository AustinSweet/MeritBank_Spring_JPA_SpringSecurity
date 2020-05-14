package com.meritamerica.assignment6.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.SavingsAccount;

public interface SavingsAccountRepo extends JpaRepository<SavingsAccount, Integer>{

}
