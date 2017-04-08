package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Commande;
import com.itsolution.tkbr.repository.CommandeRepository;
import com.itsolution.tkbr.repository.search.CommandeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Commande.
 */
@Service
@Transactional
public class CommandeService {

    private final Logger log = LoggerFactory.getLogger(CommandeService.class);
    
    private final CommandeRepository commandeRepository;

    private final CommandeSearchRepository commandeSearchRepository;

    public CommandeService(CommandeRepository commandeRepository, CommandeSearchRepository commandeSearchRepository) {
        this.commandeRepository = commandeRepository;
        this.commandeSearchRepository = commandeSearchRepository;
    }

    /**
     * Save a commande.
     *
     * @param commande the entity to save
     * @return the persisted entity
     */
    public Commande save(Commande commande) {
        log.debug("Request to save Commande : {}", commande);
        Commande result = commandeRepository.save(commande);
        commandeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the commandes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Commande> findAll(Pageable pageable) {
        log.debug("Request to get all Commandes");
        Page<Commande> result = commandeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one commande by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Commande findOne(Long id) {
        log.debug("Request to get Commande : {}", id);
        Commande commande = commandeRepository.findOne(id);
        return commande;
    }

    /**
     *  Delete the  commande by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Commande : {}", id);
        commandeRepository.delete(id);
        commandeSearchRepository.delete(id);
    }

    /**
     * Search for the commande corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Commande> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Commandes for query {}", query);
        Page<Commande> result = commandeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
