/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.domain.enumeration.LocationType;
import com.itsolution.tkbr.service.template.Label;
import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Location extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private LocationType type;

    @NotNull
    @ManyToOne(optional = false)
    private Client locataire;

    @NotNull
    @ManyToOne(optional = false)
    private Local local;

    @NotNull
    private LocalDate dateDebut;
    
    @NotNull
    @Label("Durée (Mois)")
    private Integer duree;
    
    

  
    private LocalDate dateFin;

    @NotNull
    private BigDecimal montantLoyer;

  

    private BigDecimal montantDepotGarantie;

    public Integer getDuree() {
        return duree;
    }

    public void setDuree(Integer duree) {
        this.duree = duree;
    }
    

  
    
    

    public BigDecimal getMontantDepotGarantie() {
        return montantDepotGarantie;
    }

    public void setMontantDepotGarantie(BigDecimal montantDepotGarantie) {
        this.montantDepotGarantie = montantDepotGarantie;
    }

    public LocationType getType() {
        return type;
    }

    public void setType(LocationType type) {
        this.type = type;
    }

    public Client getLocataire() {
        return locataire;
    }

    public void setLocataire(Client locataire) {
        this.locataire = locataire;
    }

    public Local getLocal() {
        return local;
    }

    public void setLocal(Local local) {
        this.local = local;
    }

   

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

    public BigDecimal getMontantLoyer() {
        return montantLoyer;
    }

    public void setMontantLoyer(BigDecimal montantLoyer) {
        this.montantLoyer = montantLoyer;
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
        if (!(object instanceof Location)) {
            return false;
        }
        Location other = (Location) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.Bail[ id=" + id + " ]";
    }

}
