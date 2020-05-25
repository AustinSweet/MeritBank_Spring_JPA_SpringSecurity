package com.meritamerica.assignment6.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.CDAccount;

public interface AccountHolderRepo extends JpaRepository<AccountHolder, Integer>{
	AccountHolder findById(int iD);

}
