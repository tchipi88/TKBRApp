/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsolution.tkbr.domain.enumeration.TypeCommande;
import com.itsolution.tkbr.service.util.ReadOnly;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TerrainCommande extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_emission")
    @NotNull
    private LocalDate dateEmission;

    @ManyToOne(optional = false)
    @NotNull
    private Fournisseur fournisseur;

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TypeCommande type;

    @ManyToOne(optional = false)
    @NotNull
    private Terrain terrain;

    @Column(name = "prix_ht", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal prixHT;

    @Column(name = "prix_ttc", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal prixTTC;

    @Column(name = "montant_paye", precision = 10, scale = 2)
    @ReadOnly
    private BigDecimal montantPaye;
    @Formula("prix_ttc-montant_paye")
    private BigDecimal montantRestant;
    
    @Lob
    @Column(name = "commentaires")
    private String commentaires;
    
    @ManyToOne
    private TerrainSuivi  suiviDossier;

    public TerrainSuivi getSuiviDossier() {
        return suiviDossier;
    }

    public void setSuiviDossier(TerrainSuivi suiviDossier) {
        this.suiviDossier = suiviDossier;
    }
    
    
    
    
    
    
    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    private Set<TerrainReglement> reglements = new HashSet<>();

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
    
    

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public TypeCommande getType() {
        return type;
    }

    public void setType(TypeCommande type) {
        this.type = type;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setTerrain(Terrain terrain) {
        this.terrain = terrain;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public BigDecimal getPrixTTC() {
        return prixTTC;
    }

    public void setPrixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
    }

    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }

    public BigDecimal getMontantRestant() {
        return montantRestant;
    }

    public void setMontantRestant(BigDecimal montantRestant) {
        this.montantRestant = montantRestant;
    }

    public Set<TerrainReglement> getReglements() {
        return reglements;
    }

    public void setReglements(Set<TerrainReglement> reglements) {
        this.reglements = reglements;
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
        if (!(object instanceof TerrainCommande)) {
            return false;
        }
        TerrainCommande other = (TerrainCommande) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.CommandeTerrain[ id=" + id + " ]";
    }

}
