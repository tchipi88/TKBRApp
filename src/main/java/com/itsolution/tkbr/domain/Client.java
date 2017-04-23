package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;

/**
 * A Client.
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "client")
public class Client extends Tiers {

    @OneToOne(mappedBy = "client")
    @JsonIgnore
    private CompteAnalytiqueClient compte;

    public CompteAnalytiqueClient getCompte() {
        return compte;
    }

    public void setCompte(CompteAnalytiqueClient compte) {
        this.compte = compte;
    }

    
  
}
