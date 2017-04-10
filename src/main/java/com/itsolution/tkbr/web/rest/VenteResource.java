package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Vente;

import com.itsolution.tkbr.repository.VenteRepository;
import com.itsolution.tkbr.repository.search.VenteSearchRepository;
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
 * REST controller for managing Vente.
 */
@RestController
@RequestMapping("/api")
public class VenteResource {

    private final Logger log = LoggerFactory.getLogger(VenteResource.class);

    private static final String ENTITY_NAME = "vente";
        
    private final VenteRepository venteRepository;

    private final VenteSearchRepository venteSearchRepository;

    public VenteResource(VenteRepository venteRepository, VenteSearchRepository venteSearchRepository) {
        this.venteRepository = venteRepository;
        this.venteSearchRepository = venteSearchRepository;
    }

    /**
     * POST  /ventes : Create a new vente.
     *
     * @param vente the vente to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vente, or with status 400 (Bad Request) if the vente has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/ventes")
    @Timed
    public ResponseEntity<Vente> createVente(@Valid @RequestBody Vente vente) throws URISyntaxException {
        log.debug("REST request to save Vente : {}", vente);
        if (vente.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new vente cannot already have an ID")).body(null);
        }
        Vente result = venteRepository.save(vente);
        venteSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/ventes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /ventes : Updates an existing vente.
     *
     * @param vente the vente to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vente,
     * or with status 400 (Bad Request) if the vente is not valid,
     * or with status 500 (Internal Server Error) if the vente couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/ventes")
    @Timed
    public ResponseEntity<Vente> updateVente(@Valid @RequestBody Vente vente) throws URISyntaxException {
        log.debug("REST request to update Vente : {}", vente);
        if (vente.getId() == null) {
            return createVente(vente);
        }
        Vente result = venteRepository.save(vente);
        venteSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vente.getId().toString()))
            .body(result);
    }

   /**
     * GET  /ventes : get all the ventes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of ventes in body
     */
    @GetMapping("/ventes")
    @Timed
    public ResponseEntity<List<Vente>> getAllVentes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Ventes");
        Page<Vente> page = venteRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/ventes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /ventes/:id : get the "id" vente.
     *
     * @param id the id of the vente to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vente, or with status 404 (Not Found)
     */
    @GetMapping("/ventes/{id}")
    @Timed
    public ResponseEntity<Vente> getVente(@PathVariable Long id) {
        log.debug("REST request to get Vente : {}", id);
        Vente vente = venteRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vente));
    }

    /**
     * DELETE  /ventes/:id : delete the "id" vente.
     *
     * @param id the id of the vente to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/ventes/{id}")
    @Timed
    public ResponseEntity<Void> deleteVente(@PathVariable Long id) {
        log.debug("REST request to delete Vente : {}", id);
        venteRepository.delete(id);
        venteSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/ventes?query=:query : search for the vente corresponding
     * to the query.
     *
     * @param query the query of the vente search 
     * @return the result of the search
     */
    @GetMapping("/_search/ventes")
    @Timed
    public List<Vente> searchVentes(@RequestParam String query) {
        log.debug("REST request to search Ventes for query {}", query);
        return StreamSupport
            .stream(venteSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
