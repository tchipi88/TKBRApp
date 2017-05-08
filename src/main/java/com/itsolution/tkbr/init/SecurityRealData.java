/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.init;

import com.itsolution.tkbr.domain.Authority;
import com.itsolution.tkbr.domain.EmployeDepartement;
import com.itsolution.tkbr.domain.EmployeFonction;
import com.itsolution.tkbr.domain.User;
import com.itsolution.tkbr.repository.AuthorityRepository;
import com.itsolution.tkbr.repository.EmployeDepartementRepository;
import com.itsolution.tkbr.repository.EmployeFonctionRepository;
import com.itsolution.tkbr.repository.UserRepository;
import com.itsolution.tkbr.security.AuthoritiesConstants;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Component
@Transactional
public class SecurityRealData implements RealData {

    @Autowired
    UserRepository UserRepository;
    @Autowired
    AuthorityRepository profilRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmployeFonctionRepository  employeFonctionRepository;
    @Autowired
    EmployeDepartementRepository  employeDepartementRepository;
    
     public static final String DepartementADMIN = "Système d'information";
     public static final String FonctionADMIN = "Administrateur Fonctionnel";

    @Override
    public void populateData(HttpServletRequest req) throws Exception {
        profilRepository.save(new Authority(AuthoritiesConstants.ADMIN));
        profilRepository.save(new Authority(AuthoritiesConstants.USER));
        
        employeDepartementRepository.save(new EmployeDepartement(DepartementADMIN));
        employeFonctionRepository.save(new EmployeFonction(FonctionADMIN));
        
        
        

        User newUser = new User();
        Authority profil = profilRepository.findByName(AuthoritiesConstants.ADMIN);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode("admin");
        newUser.setLogin("admin");
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setNom(newUser.getLogin());
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        authorities.add(profil);
        newUser.setAuthorities(authorities);
        
        newUser.setDepartement(employeDepartementRepository.findByLibelle(DepartementADMIN));
        newUser.setFonction(employeFonctionRepository.findByLibelle(FonctionADMIN));
        UserRepository.save(newUser);

     

    }

}
