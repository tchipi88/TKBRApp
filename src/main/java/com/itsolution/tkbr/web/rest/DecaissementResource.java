package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Decaissement;

import com.itsolution.tkbr.repository.DecaissementRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Decaissement.
 */
@RestController
@RequestMapping("/api")
public class DecaissementResource {

    private final Logger log = LoggerFactory.getLogger(DecaissementResource.class);

    private static final String ENTITY_NAME = "decaissement";
        
    private final DecaissementRepository decaissementRepository;


    public DecaissementResource(DecaissementRepository decaissementRepository) {
        this.decaissementRepository = decaissementRepository;
    }

    /**
     * POST  /decaissements : Create a new decaissement.
     *
     * @param decaissement the decaissement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new decaissement, or with status 400 (Bad Request) if the decaissement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/decaissements")
    @Timed
    public ResponseEntity<Decaissement> createDecaissement(@Valid @RequestBody Decaissement decaissement) throws URISyntaxException {
        log.debug("REST request to save Decaissement : {}", decaissement);
        if (decaissement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new decaissement cannot already have an ID")).body(null);
        }
        Decaissement result = decaissementRepository.save(decaissement);
        return ResponseEntity.created(new URI("/api/decaissements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /decaissements : Updates an existing decaissement.
     *
     * @param decaissement the decaissement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated decaissement,
     * or with status 400 (Bad Request) if the decaissement is not valid,
     * or with status 500 (Internal Server Error) if the decaissement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/decaissements")
    @Timed
    public ResponseEntity<Decaissement> updateDecaissement(@Valid @RequestBody Decaissement decaissement) throws URISyntaxException {
        log.debug("REST request to update Decaissement : {}", decaissement);
        if (decaissement.getId() == null) {
            return createDecaissement(decaissement);
        }
        Decaissement result = decaissementRepository.save(decaissement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, decaissement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /decaissements : get all the decaissements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of decaissements in body
     */
    @GetMapping("/decaissements")
    @Timed
    public ResponseEntity<List<Decaissement>> getAllDecaissements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Decaissements");
        Page<Decaissement> page = decaissementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/decaissements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /decaissements/:id : get the "id" decaissement.
     *
     * @param id the id of the decaissement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the decaissement, or with status 404 (Not Found)
     */
    @GetMapping("/decaissements/{id}")
    @Timed
    public ResponseEntity<Decaissement> getDecaissement(@PathVariable Long id) {
        log.debug("REST request to get Decaissement : {}", id);
        Decaissement decaissement = decaissementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(decaissement));
    }

    /**
     * DELETE  /decaissements/:id : delete the "id" decaissement.
     *
     * @param id the id of the decaissement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/decaissements/{id}")
    @Timed
    public ResponseEntity<Void> deleteDecaissement(@PathVariable Long id) {
        log.debug("REST request to delete Decaissement : {}", id);
        decaissementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
