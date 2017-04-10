package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.BonLivraison;

import com.itsolution.tkbr.repository.BonLivraisonRepository;
import com.itsolution.tkbr.repository.search.BonLivraisonSearchRepository;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing BonLivraison.
 */
@RestController
@RequestMapping("/api")
public class BonLivraisonResource {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonResource.class);

    private static final String ENTITY_NAME = "bonLivraison";
        
    private final BonLivraisonRepository bonLivraisonRepository;

    private final BonLivraisonSearchRepository bonLivraisonSearchRepository;

    public BonLivraisonResource(BonLivraisonRepository bonLivraisonRepository, BonLivraisonSearchRepository bonLivraisonSearchRepository) {
        this.bonLivraisonRepository = bonLivraisonRepository;
        this.bonLivraisonSearchRepository = bonLivraisonSearchRepository;
    }

    /**
     * POST  /bon-livraisons : Create a new bonLivraison.
     *
     * @param bonLivraison the bonLivraison to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonLivraison, or with status 400 (Bad Request) if the bonLivraison has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-livraisons")
    @Timed
    public ResponseEntity<BonLivraison> createBonLivraison(@Valid @RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to save BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonLivraison cannot already have an ID")).body(null);
        }
        BonLivraison result = bonLivraisonRepository.save(bonLivraison);
        bonLivraisonSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bon-livraisons/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-livraisons : Updates an existing bonLivraison.
     *
     * @param bonLivraison the bonLivraison to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonLivraison,
     * or with status 400 (Bad Request) if the bonLivraison is not valid,
     * or with status 500 (Internal Server Error) if the bonLivraison couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-livraisons")
    @Timed
    public ResponseEntity<BonLivraison> updateBonLivraison(@Valid @RequestBody BonLivraison bonLivraison) throws URISyntaxException {
        log.debug("REST request to update BonLivraison : {}", bonLivraison);
        if (bonLivraison.getId() == null) {
            return createBonLivraison(bonLivraison);
        }
        BonLivraison result = bonLivraisonRepository.save(bonLivraison);
        bonLivraisonSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonLivraison.getId().toString()))
            .body(result);
    }

   /**
     * GET  /bon-livraisons : get all the bonLivraisons.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonLivraisons in body
     */
    @GetMapping("/bon-livraisons")
    @Timed
    public ResponseEntity<List<BonLivraison>> getAllBonLivraisons(@ApiParam Pageable pageable) {
        log.debug("REST request to get all BonLivraisons");
        Page<BonLivraison> page = bonLivraisonRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bon-livraisons");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bon-livraisons/:id : get the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonLivraison, or with status 404 (Not Found)
     */
    @GetMapping("/bon-livraisons/{id}")
    @Timed
    public ResponseEntity<BonLivraison> getBonLivraison(@PathVariable Long id) {
        log.debug("REST request to get BonLivraison : {}", id);
        BonLivraison bonLivraison = bonLivraisonRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonLivraison));
    }

    /**
     * DELETE  /bon-livraisons/:id : delete the "id" bonLivraison.
     *
     * @param id the id of the bonLivraison to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-livraisons/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonLivraison(@PathVariable Long id) {
        log.debug("REST request to delete BonLivraison : {}", id);
        bonLivraisonRepository.delete(id);
        bonLivraisonSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/bon-livraisons?query=:query : search for the bonLivraison corresponding
     * to the query.
     *
     * @param query the query of the bonLivraison search 
     * @return the result of the search
     */
    @GetMapping("/_search/bon-livraisons")
    @Timed
    public List<BonLivraison> searchBonLivraisons(@RequestParam String query) {
        log.debug("REST request to search BonLivraisons for query {}", query);
        return StreamSupport
            .stream(bonLivraisonSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
