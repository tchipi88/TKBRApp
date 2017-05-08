/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.repository;

import com.itsolution.tkbr.domain.Loyer;
import java.time.LocalDate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author tchipi
 */
/**
 * Spring Data JPA repository for the Loyer entity.
 */
public interface LoyerRepository extends JpaRepository<Loyer, Long> {

    public Page<Loyer> findAllByDateVersementBetween(LocalDate fromDate, LocalDate toDate, Pageable pageable);

    

}




