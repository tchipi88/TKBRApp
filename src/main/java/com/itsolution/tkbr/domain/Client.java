package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * A Client.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "client")
public class Client extends Tiers {

    @NotNull
    @Column(nullable = false)
    private boolean locataire = false;

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private CompteAnalytiqueClient compte;

    public CompteAnalytiqueClient getCompte() {
        return compte;
    }

    public void setCompte(CompteAnalytiqueClient compte) {
        this.compte = compte;
    }

    public boolean isLocataire() {
        return locataire;
    }

    public void setLocataire(boolean locataire) {
        this.locataire = locataire;
    }

    
}
