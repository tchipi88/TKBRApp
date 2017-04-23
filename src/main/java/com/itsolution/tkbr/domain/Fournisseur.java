package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * A Fournisseur.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fournisseur")
public class Fournisseur extends Tiers {

    @OneToOne(mappedBy = "fournisseur")
    @JsonIgnore
    private CompteAnalytiqueFournisseur compte;

    @OneToMany(mappedBy = "fournisseur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProduitFournisseur> produits = new HashSet<>();

    public Set<ProduitFournisseur> getProduits() {
        return produits;
    }

    public void setProduits(Set<ProduitFournisseur> produits) {
        this.produits = produits;
    }

    public CompteAnalytiqueFournisseur getCompte() {
        return compte;
    }

    public void setCompte(CompteAnalytiqueFournisseur compte) {
        this.compte = compte;
    }

}
