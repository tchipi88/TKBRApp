package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.ReglementMode;

import com.itsolution.tkbr.domain.enumeration.ReglementEtat;

/**
 * A Reglement.
 */
@Entity
@Table(name = "reglement")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "reglement")
public class Reglement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "montant", precision=10, scale=2, nullable = false)
    private BigDecimal montant;

    @NotNull
    @Column(name = "date_versement", nullable = false)
    private LocalDate dateVersement;

    @Enumerated(EnumType.STRING)
    @Column(name = "mode")
    private ReglementMode mode;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private ReglementEtat etat;

    @ManyToOne(optional = false)
    @NotNull
    private Commande commande;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public Reglement montant(BigDecimal montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public Reglement dateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
        return this;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    public ReglementMode getMode() {
        return mode;
    }

    public Reglement mode(ReglementMode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(ReglementMode mode) {
        this.mode = mode;
    }

    public ReglementEtat getEtat() {
        return etat;
    }

    public Reglement etat(ReglementEtat etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(ReglementEtat etat) {
        this.etat = etat;
    }

    public Commande getCommande() {
        return commande;
    }

    public Reglement commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Reglement reglement = (Reglement) o;
        if (reglement.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, reglement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Reglement{" +
            "id=" + id +
            ", montant='" + montant + "'" +
            ", dateVersement='" + dateVersement + "'" +
            ", mode='" + mode + "'" +
            ", etat='" + etat + "'" +
            '}';
    }
}
