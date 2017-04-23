/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.domain.enumeration.CompteAnalytiqueClientType;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CompteAnalytiqueClient extends CompteAnalytique{

  
    @NotNull
    @OneToOne
    private Client client;
    
    @Enumerated
    private CompteAnalytiqueClientType  type;

    public CompteAnalytiqueClientType getType() {
        return type;
    }

    public void setType(CompteAnalytiqueClientType type) {
        this.type = type;
    }
    
    

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }


    
    
    
}
