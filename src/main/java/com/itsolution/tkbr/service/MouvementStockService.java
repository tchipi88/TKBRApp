package com.itsolution.tkbr.service;

import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.repository.MouvementStockRepository;
import com.itsolution.tkbr.repository.search.MouvementStockSearchRepository;
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
 * Service Implementation for managing MouvementStock.
 */
@Service
@Transactional
public class MouvementStockService {

    private final Logger log = LoggerFactory.getLogger(MouvementStockService.class);
    
    private final MouvementStockRepository mouvementStockRepository;

    private final MouvementStockSearchRepository mouvementStockSearchRepository;

    public MouvementStockService(MouvementStockRepository mouvementStockRepository, MouvementStockSearchRepository mouvementStockSearchRepository) {
        this.mouvementStockRepository = mouvementStockRepository;
        this.mouvementStockSearchRepository = mouvementStockSearchRepository;
    }

    /**
     * Save a mouvementStock.
     *
     * @param mouvementStock the entity to save
     * @return the persisted entity
     */
    public MouvementStock save(MouvementStock mouvementStock) {
        log.debug("Request to save MouvementStock : {}", mouvementStock);
        MouvementStock result = mouvementStockRepository.save(mouvementStock);
        mouvementStockSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the mouvementStocks.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MouvementStock> findAll(Pageable pageable) {
        log.debug("Request to get all MouvementStocks");
        Page<MouvementStock> result = mouvementStockRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one mouvementStock by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true)
    public MouvementStock findOne(Long id) {
        log.debug("Request to get MouvementStock : {}", id);
        MouvementStock mouvementStock = mouvementStockRepository.findOne(id);
        return mouvementStock;
    }

    /**
     *  Delete the  mouvementStock by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete MouvementStock : {}", id);
        mouvementStockRepository.delete(id);
        mouvementStockSearchRepository.delete(id);
    }

    /**
     * Search for the mouvementStock corresponding to the query.
     *
     *  @param query the query of the search
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<MouvementStock> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MouvementStocks for query {}", query);
        Page<MouvementStock> result = mouvementStockSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
