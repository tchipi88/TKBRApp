/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.service.dto;


import com.itsolution.tkbr.domain.Entrepot;
import com.itsolution.tkbr.domain.Produit;
import java.io.Serializable;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
public class MouvementStockSearch implements Serializable {

    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private Produit produit;
    private Entrepot entrepotDepart;
    private Entrepot entrepotDestination;
   

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Entrepot getEntrepotDepart() {
        return entrepotDepart;
    }

    public void setEntrepotDepart(Entrepot entrepotDepart) {
        this.entrepotDepart = entrepotDepart;
    }

    public Entrepot getEntrepotDestination() {
        return entrepotDestination;
    }

    public void setEntrepotDestination(Entrepot entrepotDestination) {
        this.entrepotDestination = entrepotDestination;
    }

   

  
    
}
