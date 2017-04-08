package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Reglement;
import com.itsolution.tkbr.repository.ReglementRepository;
import com.itsolution.tkbr.repository.search.ReglementSearchRepository;
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
 * Service Implementation for managing Reglement.
 */
@Service
@Transactional
public class ReglementService {

    private final Logger log = LoggerFactory.getLogger(ReglementService.class);
    
    private final ReglementRepository reglementRepository;

    private final ReglementSearchRepository reglementSearchRepository;

    public ReglementService(ReglementRepository reglementRepository, ReglementSearchRepository reglementSearchRepository) {
        this.reglementRepository = reglementRepository;
        this.reglementSearchRepository = reglementSearchRepository;
    }

    /**
     * Save a reglement.
     *
     * @param reglement the entity to save
     * @return the persisted entity
     */
    public Reglement save(Reglement reglement) {
        log.debug("Request to save Reglement : {}", reglement);
        Reglement result = reglementRepository.save(reglement);
        reglementSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the reglements.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Reglement> findAll(Pageable pageable) {
        log.debug("Request to get all Reglements");
        Page<Reglement> result = reglementRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one reglement by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Reglement findOne(Long id) {
        log.debug("Request to get Reglement : {}", id);
        Reglement reglement = reglementRepository.findOne(id);
        return reglement;
    }

    /**
     *  Delete the  reglement by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Reglement : {}", id);
        reglementRepository.delete(id);
        reglementSearchRepository.delete(id);
    }

    /**
     * Search for the reglement corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Reglement> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Reglements for query {}", query);
        Page<Reglement> result = reglementSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
