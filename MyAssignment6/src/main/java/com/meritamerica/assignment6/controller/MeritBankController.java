package com.meritamerica.assignment6.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.meritamerica.assignment6.models.AccountHolder;
import com.meritamerica.assignment6.models.AccountHolderContactDetails;
import com.meritamerica.assignment6.models.CDAccount;
import com.meritamerica.assignment6.models.CDOffering;
import com.meritamerica.assignment6.models.CheckingAccount;
import com.meritamerica.assignment6.models.MeritBank;
import com.meritamerica.assignment6.models.SavingsAccount;
import com.meritamerica.assignment6.models.User;
import com.meritamerica.assignment6.repos.AccountHolderContactDetailsRepo;
import com.meritamerica.assignment6.repos.AccountHolderRepo;
import com.meritamerica.assignment6.repos.CDAccountRepo;
import com.meritamerica.assignment6.repos.CDOfferingRepo;
import com.meritamerica.assignment6.repos.CheckingAccountRepo;
import com.meritamerica.assignment6.repos.SavingsAccountRepo;
import com.meritamerica.assignment6.repos.UserRepo;
import com.meritamerica.assignment6.security.AuthenticationRequest;
import com.meritamerica.assignment6.security.AuthenticationResponse;
import com.meritamerica.assignment6.security.JWTUtil;
import com.meritamerica.assignment6.security.UserDetailsServiceImp;
import com.meritamerica.assignment7.exceptions.ExceedsCombinedBalanceLimitException;

@Controller
public class MeritBankController {
	@Autowired
	AccountHolderRepo ah_Repo;
	@Autowired
	UserRepo user_repo;
	@Autowired
	AccountHolderContactDetailsRepo contact_repo;
	@Autowired
	CDAccountRepo cda_repo;
	@Autowired 
	CDOfferingRepo cdo_repo;
	@Autowired 
	CheckingAccountRepo checking_repo;
	@Autowired
	SavingsAccountRepo savings_repo;
	
	@Autowired
	UserDetailsServiceImp userDetailsService;
	@Autowired
	JWTUtil jwtUtil;
	@Autowired
	AuthenticationManager authenticationManager;
	
	// my logger
	Logger myLogger = LoggerFactory.getLogger(MeritBankController.class);
	
	List<AccountHolder> aHController = new ArrayList<AccountHolder>();
	List<CDOffering> cDController = new ArrayList<CDOffering>();
	
	public MeritBankController() {
	}

	//Authentication Requests
	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUserName(), 
															authenticationRequest.getPassword() ) 
																								);
		}
		catch (BadCredentialsException e) {
				throw new Exception ("Bad Username or Password", e);
			}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUserName());
		final String jwt = jwtUtil.generateToken(userDetails);
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
		
	//Account Holder Requests
	@GetMapping (value = "/Me")
	@Valid
	public AccountHolder getMe(@RequestHeader(name = "Authorization") String token) {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		AccountHolder tempHolder = ah_Repo.findById(u.getHolder().getiD());
		return tempHolder;
	}
	
	@PostMapping (value = "/Me/checkingAccounts")
	@Valid
	public CheckingAccount addMeChecking(@RequestHeader (name = "Authorization") String token, 
			@RequestBody CheckingAccount checking) throws Exception {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		AccountHolder tempHolder = ah_Repo.findById(u.getHolder().getiD());
		tempHolder.addCheckingAccount(checking);
		ah_Repo.save(tempHolder);
		return checking;
	}
	
	@GetMapping (value = "/Me/checkingAccounts")
	@Valid
	public CheckingAccount[] getMeChecking(@RequestHeader (name = "Authorization") String token) {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		return ah_Repo.findById(u.getHolder().getiD()).getCheckingAccounts();
	}
	
	@PostMapping (value = "/Me/savingsAccounts")
	@Valid
	public SavingsAccount addMeSavings(@RequestHeader (name = "Authorization") String token, 
			@RequestBody SavingsAccount savings) throws Exception {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		AccountHolder tempHolder = ah_Repo.findById(u.getHolder().getiD());
		tempHolder.addSavingsAccount(savings);
		ah_Repo.save(tempHolder);
		return savings;
	}
	
	@GetMapping (value = "/Me/savingsAccounts")
	@Valid
	public SavingsAccount[] getMeSavings(@RequestHeader (name = "Authorization") String token) {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		return ah_Repo.findById(u.getHolder().getiD()).getSavingsAccounts();
	}
	
	@PostMapping (value = "/Me/CDAccounts")
	@Valid
	public CDAccount addMeCD(@RequestHeader (name = "Authorization") String token, 
			@RequestBody CDAccount cda) throws Exception {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		AccountHolder tempHolder = ah_Repo.findById(u.getHolder().getiD());
		tempHolder.addCDAccount(cda);
		ah_Repo.save(tempHolder);
		return cda;
	}
	
	@GetMapping (value = "/Me/CDAccounts")
	@Valid
	public CDAccount[] getMeCD(@RequestHeader (name = "Authorization") String token) {
		String token1 = token.substring(0, 7);
		User u = user_repo.findByUserName(jwtUtil.extractUsername(token1)).get();
		return ah_Repo.findById(u.getHolder().getiD()).getCDAccounts();
	}
	
	//Admin Requests
	@PostMapping (value = "/authenticate/createUser")
	@Valid
	public ResponseEntity<?> createUser(@RequestBody User u) {
		user_repo.save(u);
		return ResponseEntity.ok(u);
	}
	
	@PostMapping(value = "/AccountHolders")
	@Valid
	public ResponseEntity<AccountHolder> addAccountHolderFromReq(@Valid @RequestBody AccountHolder data) {
		if (data.getFirstName() == null || data.getMiddleName() == null || data.getLastName() == null || data.getSSN() == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			aHController.add(data);
			ah_Repo.save(data);
			return new ResponseEntity<>(data, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/AccountHolders")
	public ResponseEntity<List<AccountHolder>> getAccountHoldersFromReq() {
		return new ResponseEntity<>(this.aHController, HttpStatus.OK);
	}

	@GetMapping(value = "/AccountHolders/{id}")
	public ResponseEntity<AccountHolder> getAccountHolderByID (@PathVariable int id) {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(aHController.get(id - 1), HttpStatus.OK);
		}
	}
	
	@PostMapping(value = "/AccountHolders/{id}/ContactDetails")
	public ResponseEntity<AccountHolderContactDetails> addContactDetailsByReq (@PathVariable int id, @Valid @RequestBody AccountHolderContactDetails data) {
		contact_repo.save(data);
		aHController.get(id - 1).setContactDetails(data);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/AccountHolders/{id}/ContactDetails")
	public ResponseEntity<AccountHolderContactDetails> getContactDetailsByReq (@Valid @PathVariable int id) {
		return new ResponseEntity<>(aHController.get(id - 1).getContactDetails(), HttpStatus.OK);
	}

	@PostMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	@Valid
	public ResponseEntity<CheckingAccount> addCheckingAccountByReq (@Valid @PathVariable int id,@Valid @RequestBody CheckingAccount data) throws ExceedsCombinedBalanceLimitException {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if (data.getBalance() < 0 || data.getBalance() > 250000) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		else {
			checking_repo.save(data);
			aHController.get(id - 1).addCheckingAccount(data);
			return new ResponseEntity<>(data, HttpStatus.CREATED);
		}
	}

	@GetMapping(value = "/AccountHolders/{id}/CheckingAccounts")
	public ResponseEntity<CheckingAccount[]> getCheckingAccountsById (@PathVariable int id) {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(aHController.get(id - 1).getCheckingAccounts(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	@Valid
	public ResponseEntity<SavingsAccount> addSavingsAccountByReq (@PathVariable int id,@Valid @RequestBody SavingsAccount data) throws ExceedsCombinedBalanceLimitException {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if (data.getBalance() < 0 || data.getBalance() > 250000) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		savings_repo.save(data);
		aHController.get(id - 1).addSavingsAccount(data);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@GetMapping(value = "/AccountHolders/{id}/SavingsAccounts")
	public ResponseEntity<SavingsAccount[]> getSavingsAccountsById (@PathVariable int id) {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(aHController.get(id - 1).getSavingsAccounts(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/AccountHolders/{id}/CDAccounts")
	@Valid
	public ResponseEntity<CDAccount>addCDAccountByReq (@PathVariable int id,@Valid @RequestBody CDAccount data) {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else if (data.getBalance() < 0 || data.getBalance() > 250000) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		cda_repo.save(data);
		aHController.get(id - 1).addCDAccount(data);
		return new ResponseEntity<>(data, HttpStatus.CREATED);
	}

	@GetMapping(value = "/AccountHolders/{id}/CDAccounts")
	public ResponseEntity<CDAccount[]> getCDAccountByReq (@PathVariable int id) {
		if (aHController.get(id - 1) == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		else {
			return new ResponseEntity<>(aHController.get(id - 1).getCDAccounts(), HttpStatus.OK);
		}
	}

	@PostMapping(value = "/CDOfferings")
	@Valid
	public CDOffering addCDOfferingByReq(@Valid @RequestBody CDOffering data) {
		cdo_repo.save(data);
		cDController.add(data);
		return data;
	}

	@GetMapping(value = "/CDOfferings")
	public List<CDOffering> getCDOfferingsByReq() {
		return this.cDController;
	}

}
