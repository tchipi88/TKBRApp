/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.service.template.Label;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "local")
public class Local extends Produit{

    
    
    private String adresse;
    
    @Label("Surface(m²)")
    private Float surface;
    
    @Lob
    private String infosComplementaires;

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Float getSurface() {
        return surface;
    }

    public void setSurface(Float surface) {
        this.surface = surface;
    }

    public String getInfosComplementaires() {
        return infosComplementaires;
    }

    public void setInfosComplementaires(String infosComplementaires) {
        this.infosComplementaires = infosComplementaires;
    }
    
    

 

  
    
}
