/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class BonLivraisonLigne extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    @NotNull
    private BonLivraison bonLivraison;

    @OneToOne
    private CommandeLigne commandeLigne;

    private Double quantiteLivree;

    public CommandeLigne getCommandeLigne() {
        return commandeLigne;
    }

    public void setCommandeLigne(CommandeLigne commandeLigne) {
        this.commandeLigne = commandeLigne;
    }

    public Double getQuantiteLivree() {
        return quantiteLivree;
    }

    public void setQuantiteLivree(Double quantiteLivree) {
        this.quantiteLivree = quantiteLivree;
    }

    public BonLivraison getBonLivraison() {
        return bonLivraison;
    }

    public void setBonLivraison(BonLivraison bonLivraison) {
        this.bonLivraison = bonLivraison;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BonLivraisonLigne)) {
            return false;
        }
        BonLivraisonLigne other = (BonLivraisonLigne) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.BonReceptionLigne[ id=" + id + " ]";
    }

}
