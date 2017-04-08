package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.CommandeLigne;
import com.itsolution.tkbr.repository.CommandeLigneRepository;
import com.itsolution.tkbr.repository.search.CommandeLigneSearchRepository;
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
 * Service Implementation for managing CommandeLigne.
 */
@Service
@Transactional
public class CommandeLigneService {

    private final Logger log = LoggerFactory.getLogger(CommandeLigneService.class);
    
    private final CommandeLigneRepository commandeLigneRepository;

    private final CommandeLigneSearchRepository commandeLigneSearchRepository;

    public CommandeLigneService(CommandeLigneRepository commandeLigneRepository, CommandeLigneSearchRepository commandeLigneSearchRepository) {
        this.commandeLigneRepository = commandeLigneRepository;
        this.commandeLigneSearchRepository = commandeLigneSearchRepository;
    }

    /**
     * Save a commandeLigne.
     *
     * @param commandeLigne the entity to save
     * @return the persisted entity
     */
    public CommandeLigne save(CommandeLigne commandeLigne) {
        log.debug("Request to save CommandeLigne : {}", commandeLigne);
        CommandeLigne result = commandeLigneRepository.save(commandeLigne);
        commandeLigneSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the commandeLignes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommandeLigne> findAll(Pageable pageable) {
        log.debug("Request to get all CommandeLignes");
        Page<CommandeLigne> result = commandeLigneRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one commandeLigne by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public CommandeLigne findOne(Long id) {
        log.debug("Request to get CommandeLigne : {}", id);
        CommandeLigne commandeLigne = commandeLigneRepository.findOne(id);
        return commandeLigne;
    }

    /**
     *  Delete the  commandeLigne by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete CommandeLigne : {}", id);
        commandeLigneRepository.delete(id);
        commandeLigneSearchRepository.delete(id);
    }

    /**
     * Search for the commandeLigne corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CommandeLigne> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CommandeLignes for query {}", query);
        Page<CommandeLigne> result = commandeLigneSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
