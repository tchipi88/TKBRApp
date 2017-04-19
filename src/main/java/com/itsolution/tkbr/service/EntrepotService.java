/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Entrepot;
import com.itsolution.tkbr.domain.enumeration.EntrepotType;
import com.itsolution.tkbr.repository.EntrepotRepository;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class EntrepotService {

    @Autowired
    EntrepotRepository entrepotRepository;

    public Entrepot findByType(EntrepotType type) throws Exception {
        Entrepot e = entrepotRepository.findByType(type);
        if (e == null) {
            e = new Entrepot();
            e.setType(type);
            e.setCapactite(0);
            return entrepotRepository.save(e);
        }
        return e;
    }

    

}
