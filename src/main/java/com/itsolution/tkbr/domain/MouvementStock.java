package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A MouvementStock.
 */
@Entity
@Table(name = "mouvement_stock")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "mouvementstock")
public class MouvementStock implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantite")
    private Double quantite;

    @NotNull
    @Column(name = "date_transaction", nullable = false)
    private LocalDate dateTransaction;

    @Lob
    @Column(name = "motif_transaction")
    private String motifTransaction;

    @ManyToOne(optional = false)
    @NotNull
    private Entrepot entrepotDepart;

    @ManyToOne(optional = false)
    @NotNull
    private Entrepot entrepotDestination;

    @ManyToOne(optional = false)
    @NotNull
    private Produit produit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getQuantite() {
        return quantite;
    }

    public MouvementStock quantite(Double quantite) {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public LocalDate getDateTransaction() {
        return dateTransaction;
    }

    public MouvementStock dateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
        return this;
    }

    public void setDateTransaction(LocalDate dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getMotifTransaction() {
        return motifTransaction;
    }

    public MouvementStock motifTransaction(String motifTransaction) {
        this.motifTransaction = motifTransaction;
        return this;
    }

    public void setMotifTransaction(String motifTransaction) {
        this.motifTransaction = motifTransaction;
    }

    public Entrepot getEntrepotDepart() {
        return entrepotDepart;
    }

    public MouvementStock entrepotDepart(Entrepot entrepot) {
        this.entrepotDepart = entrepot;
        return this;
    }

    public void setEntrepotDepart(Entrepot entrepot) {
        this.entrepotDepart = entrepot;
    }

    public Entrepot getEntrepotDestination() {
        return entrepotDestination;
    }

    public MouvementStock entrepotDestination(Entrepot entrepot) {
        this.entrepotDestination = entrepot;
        return this;
    }

    public void setEntrepotDestination(Entrepot entrepot) {
        this.entrepotDestination = entrepot;
    }

    public Produit getProduit() {
        return produit;
    }

    public MouvementStock produit(Produit produit) {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MouvementStock mouvementStock = (MouvementStock) o;
        if (mouvementStock.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, mouvementStock.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "MouvementStock{" +
            "id=" + id +
            ", quantite='" + quantite + "'" +
            ", dateTransaction='" + dateTransaction + "'" +
            ", motifTransaction='" + motifTransaction + "'" +
            '}';
    }
}
