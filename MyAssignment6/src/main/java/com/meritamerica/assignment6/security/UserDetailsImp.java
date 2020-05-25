package com.meritamerica.assignment6.security;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.meritamerica.assignment6.models.User;

public class UserDetailsImp implements UserDetails{

	private String userName;
	private String password;
	private boolean active;
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImp() {
	}
	
	public UserDetailsImp(User u) {
		this.userName = u.getUserName();
		this.password = u.getPassword();
		this.active = u.isActive();
		this.authorities = Arrays.stream(u.getRoles().split(",")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}
	 
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
