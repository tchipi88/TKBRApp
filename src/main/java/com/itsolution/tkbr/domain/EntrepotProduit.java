/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.service.util.ReadOnly;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
public class EntrepotProduit extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    private Produit produit;
    @ManyToOne
    @NotNull
    private Entrepot entrepot;

    @Column
    @ReadOnly
    private Float stockTheorique;
    @Column
    private Float stockPhysique;
    @Column
    @Formula("stockPhysique-stockTheorique")
    private Float ecart;

    @Column
    @NotNull
    private Float seuilAlerte;
    @Column
    @NotNull
    private Float seuilSurStockage;

    @Formula("stockPhysique>seuilSurStockage")
    private boolean EnSurchauffe;
    @Formula("stockPhysique<seuilAlerte")
    private boolean EnManque;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Entrepot getEntrepot() {
        return entrepot;
    }

    public void setEntrepot(Entrepot entrepot) {
        this.entrepot = entrepot;
    }

    public Float getStockTheorique() {
        return stockTheorique;
    }

    public void setStockTheorique(Float stockTheorique) {
        this.stockTheorique = stockTheorique;
    }

    public Float getStockPhysique() {
        return stockPhysique;
    }

    public void setStockPhysique(Float stockPhysique) {
        this.stockPhysique = stockPhysique;
    }

  
    public Float getEcart() {
        return ecart;
    }

    public void setEcart(Float ecart) {
        this.ecart = ecart;
    }

    public Float getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(Float seuilAlerte) {
        this.seuilAlerte = seuilAlerte;
    }

    public Float getSeuilSurStockage() {
        return seuilSurStockage;
    }

    public void setSeuilSurStockage(Float seuilSurStockage) {
        this.seuilSurStockage = seuilSurStockage;
    }

    public boolean isEnSurchauffe() {
        return EnSurchauffe;
    }

    public void setEnSurchauffe(boolean EnSurchauffe) {
        this.EnSurchauffe = EnSurchauffe;
    }

    public boolean isEnManque() {
        return EnManque;
    }

    public void setEnManque(boolean EnManque) {
        this.EnManque = EnManque;
    }

}
