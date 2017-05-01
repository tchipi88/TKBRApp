/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Fournisseur;
import com.itsolution.tkbr.domain.Produit;
import com.itsolution.tkbr.domain.ProduitFournisseur;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the ProduitFournisseur entity.
 */
public interface ProduitFournisseurRepository extends JpaRepository<ProduitFournisseur, Long> {

    public ProduitFournisseur findByFournisseurAndProduit(Fournisseur fournisseur, Produit produit);

    public List<ProduitFournisseur> findByFournisseurId(Long id);

    

}




