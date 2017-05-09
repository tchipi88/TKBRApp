/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Entrepot;
import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.domain.Produit;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the MouvementStock entity.
 */
public interface MouvementStockRepository extends JpaRepository<MouvementStock, Long> {

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBefore(LocalDate dateDebut, LocalDate dateFin);

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBeforeAndProduit(LocalDate dateDebut, LocalDate dateFin, Produit produit);

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBeforeAndEntrepotDepart(LocalDate dateDebut, LocalDate dateFin, Entrepot entrepotDepart);

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBeforeAndEntrepotDepartAndProduit(LocalDate dateDebut, LocalDate dateFin, Entrepot entrepotDepart, Produit produit);

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBeforeAndEntrepotDestination(LocalDate dateDebut, LocalDate dateFin, Entrepot entrepotDestination);

    List<MouvementStock> findByDateTransactionAfterAndDateTransactionBeforeAndEntrepotDestinationAndProduit(LocalDate dateDebut, LocalDate dateFin, Entrepot entrepotDestination, Produit produit);

    public Page<MouvementStock> findAllByDateTransactionBetween(LocalDateTime fromDate, LocalDateTime toDate, Pageable pageable);

}
