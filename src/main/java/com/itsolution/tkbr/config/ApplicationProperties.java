package com.itsolution.tkbr.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 *
 * <p>
 * Properties are configured in the application.yml file.
 * </p>
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final TKBR tkbr = new TKBR();

    public TKBR getTkbr() {
        return tkbr;
    }

    public static class TKBR {

        private String nom;

        private String email;

        private String telephonePortable;

        private String telephoneFixe;

        private String siteWeb;

        private String boitePostale;

        private String adresse;

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
        
        

    }
}
