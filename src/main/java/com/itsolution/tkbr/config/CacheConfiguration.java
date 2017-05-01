package com.itsolution.tkbr.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(com.itsolution.tkbr.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Authority.class.getName()+".accessGroups", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.AccessGroup.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.AccessGroup.class.getName()+".access", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Access.class.getName(), jcacheConfiguration);
             cm.createCache(com.itsolution.tkbr.domain.Access.class.getName()+".groupes", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.PersistentToken.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.User.class.getName() + ".persistentTokens", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Fournisseur.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Client.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.CommandeLigne.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Compte.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Employe.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.EmployeFonction.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Entrepot.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Commande.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Commande.class.getName() + ".commandeLignes", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Commande.class.getName() + ".reglements", jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.MouvementStock.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Produit.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.ProduitCategorie.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.ProduitFournisseur.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Reglement.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Unite.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.EmployeDepartement.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.CompteAnalytique.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.EcritureCompteAnalytique.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Local.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Terrain.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.TerrainCommande.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.TerrainReglement.class.getName(), jcacheConfiguration);
            cm.createCache(com.itsolution.tkbr.domain.Tiers.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
