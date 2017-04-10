package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Achat;

import com.itsolution.tkbr.repository.AchatRepository;
import com.itsolution.tkbr.repository.search.AchatSearchRepository;
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
 * REST controller for managing Achat.
 */
@RestController
@RequestMapping("/api")
public class AchatResource {

    private final Logger log = LoggerFactory.getLogger(AchatResource.class);

    private static final String ENTITY_NAME = "achat";
        
    private final AchatRepository achatRepository;

    private final AchatSearchRepository achatSearchRepository;

    public AchatResource(AchatRepository achatRepository, AchatSearchRepository achatSearchRepository) {
        this.achatRepository = achatRepository;
        this.achatSearchRepository = achatSearchRepository;
    }

    /**
     * POST  /achats : Create a new achat.
     *
     * @param achat the achat to create
     * @return the ResponseEntity with status 201 (Created) and with body the new achat, or with status 400 (Bad Request) if the achat has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/achats")
    @Timed
    public ResponseEntity<Achat> createAchat(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to save Achat : {}", achat);
        if (achat.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new achat cannot already have an ID")).body(null);
        }
        Achat result = achatRepository.save(achat);
        achatSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/achats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /achats : Updates an existing achat.
     *
     * @param achat the achat to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated achat,
     * or with status 400 (Bad Request) if the achat is not valid,
     * or with status 500 (Internal Server Error) if the achat couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/achats")
    @Timed
    public ResponseEntity<Achat> updateAchat(@Valid @RequestBody Achat achat) throws URISyntaxException {
        log.debug("REST request to update Achat : {}", achat);
        if (achat.getId() == null) {
            return createAchat(achat);
        }
        Achat result = achatRepository.save(achat);
        achatSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, achat.getId().toString()))
            .body(result);
    }

   /**
     * GET  /achats : get all the achats.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of achats in body
     */
    @GetMapping("/achats")
    @Timed
    public ResponseEntity<List<Achat>> getAllAchats(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Achats");
        Page<Achat> page = achatRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/achats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /achats/:id : get the "id" achat.
     *
     * @param id the id of the achat to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the achat, or with status 404 (Not Found)
     */
    @GetMapping("/achats/{id}")
    @Timed
    public ResponseEntity<Achat> getAchat(@PathVariable Long id) {
        log.debug("REST request to get Achat : {}", id);
        Achat achat = achatRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(achat));
    }

    /**
     * DELETE  /achats/:id : delete the "id" achat.
     *
     * @param id the id of the achat to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/achats/{id}")
    @Timed
    public ResponseEntity<Void> deleteAchat(@PathVariable Long id) {
        log.debug("REST request to delete Achat : {}", id);
        achatRepository.delete(id);
        achatSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/achats?query=:query : search for the achat corresponding
     * to the query.
     *
     * @param query the query of the achat search 
     * @return the result of the search
     */
    @GetMapping("/_search/achats")
    @Timed
    public List<Achat> searchAchats(@RequestParam String query) {
        log.debug("REST request to search Achats for query {}", query);
        return StreamSupport
            .stream(achatSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
