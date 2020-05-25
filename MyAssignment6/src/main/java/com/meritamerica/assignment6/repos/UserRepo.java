package com.meritamerica.assignment6.repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.models.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	Optional<User> findByUserName(String userName);
}
