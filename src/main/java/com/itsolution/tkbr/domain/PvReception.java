package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.EtatPv;

/**
 * A PvReception.
 */
@Entity
@Table(name = "pv_reception")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pvreception")
public class PvReception implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @NotNull
    @Column(name = "date_reception", nullable = false)
    private LocalDate dateReception;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat")
    private EtatPv etat;

    @Lob
    @Column(name = "commentaires")
    private String commentaires;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Commande commande;

    @ManyToOne
    private Employe superviseur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public PvReception reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateReception() {
        return dateReception;
    }

    public PvReception dateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
        return this;
    }

    public void setDateReception(LocalDate dateReception) {
        this.dateReception = dateReception;
    }

    public EtatPv getEtat() {
        return etat;
    }

    public PvReception etat(EtatPv etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(EtatPv etat) {
        this.etat = etat;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public PvReception commentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Commande getCommande() {
        return commande;
    }

    public PvReception commande(Commande commande) {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }

    public Employe getSuperviseur() {
        return superviseur;
    }

    public PvReception superviseur(Employe employe) {
        this.superviseur = employe;
        return this;
    }

    public void setSuperviseur(Employe employe) {
        this.superviseur = employe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PvReception pvReception = (PvReception) o;
        if (pvReception.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, pvReception.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PvReception{" +
            "id=" + id +
            ", reference='" + reference + "'" +
            ", dateReception='" + dateReception + "'" +
            ", etat='" + etat + "'" +
            ", commentaires='" + commentaires + "'" +
            '}';
    }
}
