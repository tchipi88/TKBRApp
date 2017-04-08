package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.CommandeType;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "date_emission")
    private LocalDate dateEmission;

    @Min(value = 1)
    @Max(value = 30)
    @Column(name = "delai")
    private Integer delai;

    @Column(name = "prix_ht", precision=10, scale=2)
    private BigDecimal prixHT;

    @Column(name = "prix_ttc", precision=10, scale=2)
    private BigDecimal prixTTC;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private CommandeType type;

    @NotNull
    @Column(name = "date_echeance", nullable = false)
    private LocalDate dateEcheance;

    @Lob
    @Column(name = "commentaires")
    private String commentaires;

    @ManyToOne(optional = false)
    @NotNull
    private Client client;

    @ManyToOne(optional = false)
    @NotNull
    private Fournisseur fournisseur;

    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<CommandeLigne> commandeLignes = new HashSet<>();

    @OneToMany(mappedBy = "commande")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Reglement> reglements = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public Commande reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getDateEmission() {
        return dateEmission;
    }

    public Commande dateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
        return this;
    }

    public void setDateEmission(LocalDate dateEmission) {
        this.dateEmission = dateEmission;
    }

    public Integer getDelai() {
        return delai;
    }

    public Commande delai(Integer delai) {
        this.delai = delai;
        return this;
    }

    public void setDelai(Integer delai) {
        this.delai = delai;
    }

    public BigDecimal getPrixHT() {
        return prixHT;
    }

    public Commande prixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
        return this;
    }

    public void setPrixHT(BigDecimal prixHT) {
        this.prixHT = prixHT;
    }

    public BigDecimal getPrixTTC() {
        return prixTTC;
    }

    public Commande prixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
        return this;
    }

    public void setPrixTTC(BigDecimal prixTTC) {
        this.prixTTC = prixTTC;
    }

    public CommandeType getType() {
        return type;
    }

    public Commande type(CommandeType type) {
        this.type = type;
        return this;
    }

    public void setType(CommandeType type) {
        this.type = type;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public Commande dateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
        return this;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getCommentaires() {
        return commentaires;
    }

    public Commande commentaires(String commentaires) {
        this.commentaires = commentaires;
        return this;
    }

    public void setCommentaires(String commentaires) {
        this.commentaires = commentaires;
    }

    public Client getClient() {
        return client;
    }

    public Commande client(Client client) {
        this.client = client;
        return this;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public Commande fournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
        return this;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Set<CommandeLigne> getCommandeLignes() {
        return commandeLignes;
    }

    public Commande commandeLignes(Set<CommandeLigne> commandeLignes) {
        this.commandeLignes = commandeLignes;
        return this;
    }

    public Commande addCommandeLignes(CommandeLigne commandeLigne) {
        this.commandeLignes.add(commandeLigne);
        commandeLigne.setCommande(this);
        return this;
    }

    public Commande removeCommandeLignes(CommandeLigne commandeLigne) {
        this.commandeLignes.remove(commandeLigne);
        commandeLigne.setCommande(null);
        return this;
    }

    public void setCommandeLignes(Set<CommandeLigne> commandeLignes) {
        this.commandeLignes = commandeLignes;
    }

    public Set<Reglement> getReglements() {
        return reglements;
    }

    public Commande reglements(Set<Reglement> reglements) {
        this.reglements = reglements;
        return this;
    }

    public Commande addReglements(Reglement reglement) {
        this.reglements.add(reglement);
        reglement.setCommande(this);
        return this;
    }

    public Commande removeReglements(Reglement reglement) {
        this.reglements.remove(reglement);
        reglement.setCommande(null);
        return this;
    }

    public void setReglements(Set<Reglement> reglements) {
        this.reglements = reglements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        if (commande.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + id +
            ", reference='" + reference + "'" +
            ", dateEmission='" + dateEmission + "'" +
            ", delai='" + delai + "'" +
            ", prixHT='" + prixHT + "'" +
            ", prixTTC='" + prixTTC + "'" +
            ", type='" + type + "'" +
            ", dateEcheance='" + dateEcheance + "'" +
            ", commentaires='" + commentaires + "'" +
            '}';
    }
}
