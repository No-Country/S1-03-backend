package com.nocountry.messenger.AuthSecurity.security.services;

import com.nocountry.messenger.model.entity.Cliente;
import com.nocountry.messenger.repository.IClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	IClientRepository clienteRepository;
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            Cliente cliente = clienteRepository.findByUsername(username)
		.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		return UserDetailsImpl.build(cliente);
	}
}
