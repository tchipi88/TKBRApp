package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.Employe;
import com.itsolution.tkbr.repository.EmployeRepository;
import com.itsolution.tkbr.repository.search.EmployeSearchRepository;
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
 * Service Implementation for managing Employe.
 */
@Service
@Transactional
public class EmployeService {

    private final Logger log = LoggerFactory.getLogger(EmployeService.class);
    
    private final EmployeRepository employeRepository;

    private final EmployeSearchRepository employeSearchRepository;

    public EmployeService(EmployeRepository employeRepository, EmployeSearchRepository employeSearchRepository) {
        this.employeRepository = employeRepository;
        this.employeSearchRepository = employeSearchRepository;
    }

    /**
     * Save a employe.
     *
     * @param employe the entity to save
     * @return the persisted entity
     */
    public Employe save(Employe employe) {
        log.debug("Request to save Employe : {}", employe);
        Employe result = employeRepository.save(employe);
        employeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the employes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Employe> findAll(Pageable pageable) {
        log.debug("Request to get all Employes");
        Page<Employe> result = employeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one employe by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public Employe findOne(Long id) {
        log.debug("Request to get Employe : {}", id);
        Employe employe = employeRepository.findOne(id);
        return employe;
    }

    /**
     *  Delete the  employe by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Employe : {}", id);
        employeRepository.delete(id);
        employeSearchRepository.delete(id);
    }

    /**
     * Search for the employe corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Employe> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Employes for query {}", query);
        Page<Employe> result = employeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
