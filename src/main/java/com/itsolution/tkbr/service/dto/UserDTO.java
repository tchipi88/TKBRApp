package com.itsolution.tkbr.service.dto;

import com.itsolution.tkbr.config.Constants;

import com.itsolution.tkbr.domain.Authority;
import com.itsolution.tkbr.domain.EmployeDepartement;
import com.itsolution.tkbr.domain.EmployeFonction;
import com.itsolution.tkbr.domain.User;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;

    private ZonedDateTime lastModifiedDate;

    private Set<String> authorities;

    private EmployeFonction fonction;
    private EmployeDepartement departement;
    private BigDecimal salaire;
    private String adresse;
    private String telephone;
    private LocalDate dateNaissance;
    private String lieuNaissance;

    public UserDTO() {
        // Empty constructor needed for MapStruct.
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getPrenom(), user.getNom(),
                user.getEmail(), user.getActivated(),
                user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate(),
                user.getAuthorities().stream().map(Authority::getName)
                        .collect(Collectors.toSet()),user.getFonction(),user.getDepartement(),user.getSalaire(),user.getAdresse(),
                        user.getTelephone(),user.getDateNaissance(),user.getLieuNaissance());
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
            String email, boolean activated,
            String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
            Set<String> authorities,EmployeFonction fonction,EmployeDepartement departement,BigDecimal salaire,String adresse,
            String telephone,LocalDate dateNaissance,String lieuNaissance) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
        
        this.fonction=fonction;
        this.departement=departement;
        this.salaire=salaire;
        this.adresse=adresse;
        this.telephone=telephone;
        this.dateNaissance=dateNaissance;
        this.lieuNaissance=lieuNaissance;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public EmployeFonction getFonction() {
        return fonction;
    }

    public void setFonction(EmployeFonction fonction) {
        this.fonction = fonction;
    }

    public EmployeDepartement getDepartement() {
        return departement;
    }

    public void setDepartement(EmployeDepartement departement) {
        this.departement = departement;
    }

    public BigDecimal getSalaire() {
        return salaire;
    }

    public void setSalaire(BigDecimal salaire) {
        this.salaire = salaire;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getLieuNaissance() {
        return lieuNaissance;
    }

    public void setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }
    
    

    @Override
    public String toString() {
        return "UserDTO{"
                + "login='" + login + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", activated=" + activated
                + ", createdBy=" + createdBy
                + ", createdDate=" + createdDate
                + ", lastModifiedBy='" + lastModifiedBy + '\''
                + ", lastModifiedDate=" + lastModifiedDate
                + ", authorities=" + authorities
                + "}";
    }
}
