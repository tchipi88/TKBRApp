/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CompteAnalytiqueClient;
import com.itsolution.tkbr.domain.Location;
import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueClientType;
import com.itsolution.tkbr.repository.CompteAnalytiqueClientRepository;
import com.itsolution.tkbr.repository.LocationRepository;
import java.math.BigDecimal;
import java.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
@Transactional
public class LocationService {

    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CompteAnalytiqueClientRepository compteAnalytiqueClientRepository;

    public Location create(Location location) throws Exception {
        Period periodeBail = Period.between(location.getDateDebut(), location.getDateFin());

        CompteAnalytiqueClient compteClient = compteAnalytiqueClientRepository.findByIntitule(location.getLocataire().getNom());
        if (compteClient == null) {
            compteClient = new CompteAnalytiqueClient();
            compteClient.setIntitule(location.getLocataire().getNom());
            compteClient.setClient(location.getLocataire());
            compteClient.setType(CompteAnalytiqueClientType.LOCATION);
            compteClient.setCredit(BigDecimal.ZERO);
            compteClient.setDebit(BigDecimal.ZERO);
        }
        compteClient.setDebit(location.getMontantLoyer().multiply(new BigDecimal(periodeBail.getMonths())));

        compteAnalytiqueClientRepository.save(compteClient);

        return locationRepository.save(location);
    }

    public Location update(Location location) throws Exception {

        return locationRepository.save(location);
    }

}
