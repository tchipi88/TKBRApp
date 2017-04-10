/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.domain.enumeration.DecaissementMotif;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@Entity
public class Decaissement extends CaisseMouvement{

    @Enumerated(EnumType.STRING)
    @NotNull
    private DecaissementMotif motif;

    public DecaissementMotif getMotif() {
        return motif;
    }

    public void setMotif(DecaissementMotif motif) {
        this.motif = motif;
    }
    
    
    
}
