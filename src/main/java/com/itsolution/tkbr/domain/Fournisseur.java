package com.itsolution.tkbr.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Fournisseur.
 */
@Entity
@Table(name = "fournisseur")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "fournisseur")
public class Fournisseur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @OneToMany(mappedBy = "fournisseur")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ProduitFournisseur> produits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Fournisseur nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public Fournisseur email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephonePortable() {
        return telephonePortable;
    }

    public Fournisseur telephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
        return this;
    }

    public void setTelephonePortable(String telephonePortable) {
        this.telephonePortable = telephonePortable;
    }

    public String getTelephoneFixe() {
        return telephoneFixe;
    }

    public Fournisseur telephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
        return this;
    }

    public void setTelephoneFixe(String telephoneFixe) {
        this.telephoneFixe = telephoneFixe;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public Fournisseur siteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
        return this;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public Fournisseur boitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
        return this;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getAdresse() {
        return adresse;
    }

    public Fournisseur adresse(String adresse) {
        this.adresse = adresse;
        return this;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Fournisseur logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Fournisseur logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<ProduitFournisseur> getProduits() {
        return produits;
    }

    public Fournisseur produits(Set<ProduitFournisseur> produitFournisseurs) {
        this.produits = produitFournisseurs;
        return this;
    }

    public Fournisseur addProduits(ProduitFournisseur produitFournisseur) {
        this.produits.add(produitFournisseur);
        produitFournisseur.setFournisseur(this);
        return this;
    }

    public Fournisseur removeProduits(ProduitFournisseur produitFournisseur) {
        this.produits.remove(produitFournisseur);
        produitFournisseur.setFournisseur(null);
        return this;
    }

    public void setProduits(Set<ProduitFournisseur> produitFournisseurs) {
        this.produits = produitFournisseurs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Fournisseur fournisseur = (Fournisseur) o;
        if (fournisseur.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, fournisseur.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Fournisseur{" +
            "id=" + id +
            ", nom='" + nom + "'" +
            ", email='" + email + "'" +
            ", telephonePortable='" + telephonePortable + "'" +
            ", telephoneFixe='" + telephoneFixe + "'" +
            ", siteWeb='" + siteWeb + "'" +
            ", boitePostale='" + boitePostale + "'" +
            ", adresse='" + adresse + "'" +
            ", logo='" + logo + "'" +
            ", logoContentType='" + logoContentType + "'" +
            '}';
    }
}
