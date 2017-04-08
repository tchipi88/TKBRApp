package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.EntrepotType;

/**
 * A Entrepot.
 */
@Entity
@Table(name = "entrepot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "entrepot")
public class Entrepot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "libelle", nullable = false)
    private String libelle;

    @NotNull
    @Column(name = "localisation", nullable = false)
    private String localisation;

    @Column(name = "capactite")
    private Integer capactite;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private EntrepotType type;

    @ManyToOne
    private Employe responsable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public Entrepot libelle(String libelle) {
        this.libelle = libelle;
        return this;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getLocalisation() {
        return localisation;
    }

    public Entrepot localisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public Integer getCapactite() {
        return capactite;
    }

    public Entrepot capactite(Integer capactite) {
        this.capactite = capactite;
        return this;
    }

    public void setCapactite(Integer capactite) {
        this.capactite = capactite;
    }

    public EntrepotType getType() {
        return type;
    }

    public Entrepot type(EntrepotType type) {
        this.type = type;
        return this;
    }

    public void setType(EntrepotType type) {
        this.type = type;
    }

    public Employe getResponsable() {
        return responsable;
    }

    public Entrepot responsable(Employe employe) {
        this.responsable = employe;
        return this;
    }

    public void setResponsable(Employe employe) {
        this.responsable = employe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Entrepot entrepot = (Entrepot) o;
        if (entrepot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, entrepot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entrepot{" +
            "id=" + id +
            ", libelle='" + libelle + "'" +
            ", localisation='" + localisation + "'" +
            ", capactite='" + capactite + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
