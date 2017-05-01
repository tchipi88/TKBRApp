package com.itsolution.tkbr.security;

import com.itsolution.tkbr.domain.Access;
import com.itsolution.tkbr.domain.AccessGroup;
import com.itsolution.tkbr.domain.Authority;
import com.itsolution.tkbr.domain.User;
import com.itsolution.tkbr.repository.AccessRepository;
import com.itsolution.tkbr.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;
    private final AccessRepository accessRepository;

    public DomainUserDetailsService(UserRepository userRepository,AccessRepository accessRepository) {
        this.userRepository = userRepository;
        this.accessRepository=accessRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        
        List<Access> liste=accessRepository.findOneByLoginWithEagerRelationships(lowercaseLogin);
        System.out.println("************* TAILLE "+liste.size()+" *****************************");
        System.out.println("*************"+liste.size()+" *****************************");
        for(Access access :liste){
        	System.out.println("************* Access :"+access.getLibelle()+" "+access.getValeur()+" *****************************");
        	//for(AccessGroup accessGroup )
        }    	
        
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                    .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            
            //Mon code d'ajout des access
            for(Access access :liste){
            	grantedAuthorities.add(new SimpleGrantedAuthority(access.getValeur()));
            }            
            
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }
}
