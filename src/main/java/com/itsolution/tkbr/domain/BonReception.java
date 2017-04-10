/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsolution.tkbr.domain.enumeration.EtatPv;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Basic;
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
import javax.persistence.OneToOne;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "bonreception")
public class BonReception extends AbstractAuditingEntity{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Achat  commande;

    @ManyToOne
    private Employe superviseur;

    @Basic(optional = false)
    @Column
    private LocalDate dateReception;
    @Column
    @Enumerated(EnumType.STRING)
    private EtatPv etat;
    @Lob
    @Column
    private String commentaires;
    
    @OneToMany(mappedBy = "bonReception")
    @JsonIgnore
    private Set<BonReceptionLigne> bonReceptionLigne = new HashSet<>();

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

    public Set<BonReceptionLigne> getBonReceptionLigne() {
        return bonReceptionLigne;
    }

    public void setBonReceptionLigne(Set<BonReceptionLigne> bonReceptionLigne) {
        this.bonReceptionLigne = bonReceptionLigne;
    }
    
    

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BonReception)) {
            return false;
        }
        BonReception other = (BonReception) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.itsolution.tkbr.domain.BonReception[ id=" + id + " ]";
    }

    public Achat getCommande() {
        return commande;
    }

    public void setCommande(Achat commande) {
        this.commande = commande;
    }

    public Employe getSuperviseur() {
        return superviseur;
    }

    public void setSuperviseur(Employe superviseur) {
        this.superviseur = superviseur;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public EtatPv getEtat() {
        return etat;
    }

    public void setEtat(EtatPv etat) {
        this.etat = etat;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }
    
    
}
