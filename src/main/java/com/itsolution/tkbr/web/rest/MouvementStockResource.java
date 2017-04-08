package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.MouvementStock;
import com.itsolution.tkbr.service.MouvementStockService;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MouvementStock.
 */
@RestController
@RequestMapping("/api")
public class MouvementStockResource {

    private final Logger log = LoggerFactory.getLogger(MouvementStockResource.class);

    private static final String ENTITY_NAME = "mouvementStock";
        
    private final MouvementStockService mouvementStockService;

    public MouvementStockResource(MouvementStockService mouvementStockService) {
        this.mouvementStockService = mouvementStockService;
    }

    /**
     * POST  /mouvement-stocks : Create a new mouvementStock.
     *
     * @param mouvementStock the mouvementStock to create
     * @return the ResponseEntity with status 201 (Created) and with body the new mouvementStock, or with status 400 (Bad Request) if the mouvementStock has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<MouvementStock> createMouvementStock(@Valid @RequestBody MouvementStock mouvementStock) throws URISyntaxException {
        log.debug("REST request to save MouvementStock : {}", mouvementStock);
        if (mouvementStock.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new mouvementStock cannot already have an ID")).body(null);
        }
        MouvementStock result = mouvementStockService.save(mouvementStock);
        return ResponseEntity.created(new URI("/api/mouvement-stocks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mouvement-stocks : Updates an existing mouvementStock.
     *
     * @param mouvementStock the mouvementStock to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated mouvementStock,
     * or with status 400 (Bad Request) if the mouvementStock is not valid,
     * or with status 500 (Internal Server Error) if the mouvementStock couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<MouvementStock> updateMouvementStock(@Valid @RequestBody MouvementStock mouvementStock) throws URISyntaxException {
        log.debug("REST request to update MouvementStock : {}", mouvementStock);
        if (mouvementStock.getId() == null) {
            return createMouvementStock(mouvementStock);
        }
        MouvementStock result = mouvementStockService.save(mouvementStock);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, mouvementStock.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mouvement-stocks : get all the mouvementStocks.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of mouvementStocks in body
     */
    @GetMapping("/mouvement-stocks")
    @Timed
    public ResponseEntity<List<MouvementStock>> getAllMouvementStocks(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MouvementStocks");
        Page<MouvementStock> page = mouvementStockService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mouvement-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mouvement-stocks/:id : get the "id" mouvementStock.
     *
     * @param id the id of the mouvementStock to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the mouvementStock, or with status 404 (Not Found)
     */
    @GetMapping("/mouvement-stocks/{id}")
    @Timed
    public ResponseEntity<MouvementStock> getMouvementStock(@PathVariable Long id) {
        log.debug("REST request to get MouvementStock : {}", id);
        MouvementStock mouvementStock = mouvementStockService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(mouvementStock));
    }

    /**
     * DELETE  /mouvement-stocks/:id : delete the "id" mouvementStock.
     *
     * @param id the id of the mouvementStock to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/mouvement-stocks/{id}")
    @Timed
    public ResponseEntity<Void> deleteMouvementStock(@PathVariable Long id) {
        log.debug("REST request to delete MouvementStock : {}", id);
        mouvementStockService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/mouvement-stocks?query=:query : search for the mouvementStock corresponding
     * to the query.
     *
     * @param query the query of the mouvementStock search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/mouvement-stocks")
    @Timed
    public ResponseEntity<List<MouvementStock>> searchMouvementStocks(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of MouvementStocks for query {}", query);
        Page<MouvementStock> page = mouvementStockService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/mouvement-stocks");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
