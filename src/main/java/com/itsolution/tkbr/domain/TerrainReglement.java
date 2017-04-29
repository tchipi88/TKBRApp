package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.PaymentMode;


/**
 * A Reglement.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TerrainReglement extends AbstractAuditingEntity{

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
    @NotNull
    private PaymentMode mode;

   

    @ManyToOne(optional = false)
    @NotNull
    private TerrainCommande commande;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public TerrainReglement montant(BigDecimal montant) {
        this.montant = montant;
        return this;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public LocalDate getDateVersement() {
        return dateVersement;
    }

    public TerrainReglement dateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
        return this;
    }

    public void setDateVersement(LocalDate dateVersement) {
        this.dateVersement = dateVersement;
    }

    public PaymentMode getMode() {
        return mode;
    }

    public TerrainReglement mode(PaymentMode mode) {
        this.mode = mode;
        return this;
    }

    public void setMode(PaymentMode mode) {
        this.mode = mode;
    }

    public TerrainCommande getCommande() {
        return commande;
    }

    public void setCommande(TerrainCommande commande) {
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
        TerrainReglement reglement = (TerrainReglement) o;
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
            '}';
    }
}
