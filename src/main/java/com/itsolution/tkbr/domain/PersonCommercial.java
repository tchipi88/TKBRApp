/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.itsolution.tkbr.domain;

import com.itsolution.tkbr.service.template.Image;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

/**
 *
 * @author tchipi
 */
@MappedSuperclass
public class PersonCommercial extends AbstractAuditingEntity {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

   

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "email")
    private String email;

    @NotNull
    @Column(name = "telephone_portable", nullable = false)
    private String telephonePortable;

    @Column(name = "telephone_fixe")
    private String telephoneFixe;

    @Column(name = "site_web")
    private String siteWeb;

    @Column(name = "boite_postale")
    private String boitePostale;

    @NotNull
    @Column(name = "adresse", nullable = false)
    private String adresse;

    @Lob
    @Column(name = "logo")
    @Image
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;
    
    
    @ManyToOne
    private Employe responsable;
    
    @Column
    private String numeroCommerce;

    public String getNumeroCommerce() {
        return numeroCommerce;
    }

    public void setNumeroCommerce(String numeroCommerce) {
        this.numeroCommerce = numeroCommerce;
    }
    
    

    public Employe getResponsable() {
        return responsable;
    }

    public void setResponsable(Employe responsable) {
        this.responsable = responsable;
    }
    
    

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephonePortable() {
        return telephonePortable;
    }

    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersonCommercial)) {
            return false;
        }
        PersonCommercial other = (PersonCommercial) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

     @Override
    public String toString() {
        return "Personne Commerciale{"
                + "id=" + id
                + ", nom='" + nom + "'"
                + ", email='" + email + "'"
                + ", telephonePortable='" + telephonePortable + "'"
                + ", telephoneFixe='" + telephoneFixe + "'"
                + ", siteWeb='" + siteWeb + "'"
                + ", boitePostale='" + boitePostale + "'"
                + ", adresse='" + adresse + "'"
                + ", logo='" + logo + "'"
                + ", logoContentType='" + logoContentType + "'"
                + '}';
    }

}
