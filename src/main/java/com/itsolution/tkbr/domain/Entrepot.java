package com.itsolution.tkbr.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

import com.itsolution.tkbr.domain.enumeration.EntrepotType;
import com.itsolution.tkbr.service.template.Libelle;

/**
 * A Entrepot.
 */
@Entity
@Table(name = "entrepot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Entrepot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "capactite")
    private Integer capactite;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    @Libelle
    private EntrepotType type;

    @ManyToOne
    private Employe responsable;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
            ", capactite='" + capactite + "'" +
            ", type='" + type + "'" +
            '}';
    }
}
