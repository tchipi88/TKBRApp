/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.itsolution.tkbr.service.template.Label;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *
 * @author tchipi
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Terrain extends Produit{
    
    
    private String adresse;
    
    @Label("Surface(m²)")
    private Float surface;
    
    private Float surfaceMorcellee=0.0f;

    private Float longitude;
    
    private Float latitude;
    
    @NotNull
    private boolean  vendu=false;
    
    @ManyToOne
    private Terrain  terrainParent;
    
    @OneToMany(mappedBy = "terrainParent")
    @JsonIgnore
    private List<Terrain>  lots=new ArrayList();

    public Float getSurfaceMorcellee() {
        return surfaceMorcellee;
    }

    public void setSurfaceMorcellee(Float surfaceMorcellee) {
        this.surfaceMorcellee = surfaceMorcellee;
    }

    
    
    

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

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public List<Terrain> getLots() {
        return lots;
    }

    public void setLots(List<Terrain> lots) {
        this.lots = lots;
    }

    public Terrain getTerrainParent() {
        return terrainParent;
    }

    public void setTerrainParent(Terrain terrainParent) {
        this.terrainParent = terrainParent;
    }
    
    
    
    
    
}
