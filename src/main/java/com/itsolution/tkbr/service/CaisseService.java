/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Caisse;
import com.itsolution.tkbr.repository.CaisseRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class CaisseService {

    @Autowired
    CaisseRepository caisseRepository;

    public Caisse getCaisse() throws Exception {
        List<Caisse> caisses = caisseRepository.findAll();
        if (caisses.isEmpty()) {
            Caisse c = new Caisse();
            return caisseRepository.save(c);
        } else {
            return caisses.get(0);
        }
    }

    public Caisse save(Caisse caisse) throws Exception {
        return caisseRepository.save(caisse);
    }
}
