/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Entrepot;
import com.itsolution.tkbr.domain.Fournisseur;
import com.itsolution.tkbr.repository.EntrepotRepository;
import com.itsolution.tkbr.repository.FournisseurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author tchipi
 */
@Service
public class FournisseurService {

    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    EntrepotService entrepotService;
    @Autowired
    EntrepotRepository entrepotRepository;

    @Transactional
    public Fournisseur create(Fournisseur fournisseur) throws Exception {
        entrepotService.findByLibelle(fournisseur.getNom());
        return fournisseurRepository.save(fournisseur);

    }

    public Fournisseur update(Fournisseur fournisseur) throws Exception {
        Fournisseur oldValue = fournisseurRepository.findOne(fournisseur.getId());
        if (!oldValue.getNom().equals(fournisseur.getNom())) {
            Entrepot e = entrepotService.findByLibelle(oldValue.getNom());
            e.setLibelle(fournisseur.getNom());
            entrepotRepository.save(e);
        }
        return fournisseurRepository.save(fournisseur);

    }

}
