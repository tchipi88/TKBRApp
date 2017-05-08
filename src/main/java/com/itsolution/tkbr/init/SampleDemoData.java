/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.init;

import com.itsolution.tkbr.domain.Client;
import com.itsolution.tkbr.domain.Fournisseur;
import com.itsolution.tkbr.domain.Produit;
import com.itsolution.tkbr.domain.ProduitCategorie;
import com.itsolution.tkbr.repository.ClientRepository;
import com.itsolution.tkbr.repository.EmployeRepository;
import com.itsolution.tkbr.repository.FournisseurRepository;
import com.itsolution.tkbr.repository.ProduitCategorieRepository;
import com.itsolution.tkbr.repository.ProduitRepository;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import org.fluttercode.datafactory.impl.DataFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tchipi
 */
@Service
public class SampleDemoData implements DemoData {

    @Autowired
    FournisseurRepository FournisseurRepository;
    @Autowired
    ClientRepository ClientRepository;
    @Autowired
    ProduitRepository ProduitRepository;
    @Autowired
    EmployeRepository EmployeRepository;
    @Autowired
    ProduitCategorieRepository  produitCategorieRepository;

  

    @Override
    public void populateData(HttpServletRequest req) throws Exception {

        DataFactory df = new DataFactory();
        
        produitCategorieRepository.save(new ProduitCategorie("Test"));

//        //creation des produits
        for (int i = 0; i < 50; i++) {
            Produit p = new Produit();
            p.setDenomination("Produit"+(i+1));
            p.setCategorie(produitCategorieRepository.findByLibelle("Test"));
            p.setPrix(BigDecimal.TEN);
            ProduitRepository.save(p);
        }
        //creation des fournisseurs
        for (int i = 0; i < 15; i++) {
            Fournisseur f = new Fournisseur();
            f.setNom(df.getBusinessName());
            f.setEmail(df.getEmailAddress());
            f.setAdresse(df.getAddress());
            f.setTelephonePortable("237 6" + df.getNumberText(8));
            FournisseurRepository.save(f);

            //creation des clients
            Client cl = new Client();
            cl.setNom(df.getBusinessName());
            cl.setEmail(df.getEmailAddress());
            cl.setAdresse(df.getAddress());
            cl.setTelephonePortable("237 6" + df.getNumberText(8));
            ClientRepository.save(cl);
        }

       

    }

}
