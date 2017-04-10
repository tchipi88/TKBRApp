/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.domain.enumeration.EncaissementMotif;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Encaissement extends CaisseMouvement{

 
  @Enumerated(EnumType.STRING)
  @NotNull
  private EncaissementMotif motif;

    public EncaissementMotif getMotif() {
        return motif;
    }

    public void setMotif(EncaissementMotif motif) {
        this.motif = motif;
    }
  
  
    
}
