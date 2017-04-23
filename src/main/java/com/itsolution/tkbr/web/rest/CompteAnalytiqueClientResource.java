package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.CompteAnalytiqueClient;

import com.itsolution.tkbr.repository.CompteAnalytiqueClientRepository;
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
 * REST controller for managing CompteAnalytiqueClient.
 */
@RestController
@RequestMapping("/api")
public class CompteAnalytiqueClientResource {

    private final Logger log = LoggerFactory.getLogger(CompteAnalytiqueClientResource.class);

    private static final String ENTITY_NAME = "compteAnalytiqueClient";
        
    private final CompteAnalytiqueClientRepository compteAnalytiqueClientRepository;


    public CompteAnalytiqueClientResource(CompteAnalytiqueClientRepository compteAnalytiqueClientRepository) {
        this.compteAnalytiqueClientRepository = compteAnalytiqueClientRepository;
    }

    /**
     * POST  /compte-analytique-clients : Create a new compteAnalytiqueClient.
     *
     * @param compteAnalytiqueClient the compteAnalytiqueClient to create
     * @return the ResponseEntity with status 201 (Created) and with body the new compteAnalytiqueClient, or with status 400 (Bad Request) if the compteAnalytiqueClient has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/compte-analytique-clients")
    @Timed
    public ResponseEntity<CompteAnalytiqueClient> createCompteAnalytiqueClient(@Valid @RequestBody CompteAnalytiqueClient compteAnalytiqueClient) throws URISyntaxException {
        log.debug("REST request to save CompteAnalytiqueClient : {}", compteAnalytiqueClient);
        if (compteAnalytiqueClient.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new compteAnalytiqueClient cannot already have an ID")).body(null);
        }
        CompteAnalytiqueClient result = compteAnalytiqueClientRepository.save(compteAnalytiqueClient);
        return ResponseEntity.created(new URI("/api/compte-analytique-clients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /compte-analytique-clients : Updates an existing compteAnalytiqueClient.
     *
     * @param compteAnalytiqueClient the compteAnalytiqueClient to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated compteAnalytiqueClient,
     * or with status 400 (Bad Request) if the compteAnalytiqueClient is not valid,
     * or with status 500 (Internal Server Error) if the compteAnalytiqueClient couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/compte-analytique-clients")
    @Timed
    public ResponseEntity<CompteAnalytiqueClient> updateCompteAnalytiqueClient(@Valid @RequestBody CompteAnalytiqueClient compteAnalytiqueClient) throws URISyntaxException {
        log.debug("REST request to update CompteAnalytiqueClient : {}", compteAnalytiqueClient);
        if (compteAnalytiqueClient.getId() == null) {
            return createCompteAnalytiqueClient(compteAnalytiqueClient);
        }
        CompteAnalytiqueClient result = compteAnalytiqueClientRepository.save(compteAnalytiqueClient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, compteAnalytiqueClient.getId().toString()))
            .body(result);
    }

    /**
     * GET  /compte-analytique-clients : get all the compteAnalytiqueClients.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of compteAnalytiqueClients in body
     */
    @GetMapping("/compte-analytique-clients")
    @Timed
    public ResponseEntity<List<CompteAnalytiqueClient>> getAllCompteAnalytiqueClients(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CompteAnalytiqueClients");
        Page<CompteAnalytiqueClient> page = compteAnalytiqueClientRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/compte-analytique-clients");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /compte-analytique-clients/:id : get the "id" compteAnalytiqueClient.
     *
     * @param id the id of the compteAnalytiqueClient to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the compteAnalytiqueClient, or with status 404 (Not Found)
     */
    @GetMapping("/compte-analytique-clients/{id}")
    @Timed
    public ResponseEntity<CompteAnalytiqueClient> getCompteAnalytiqueClient(@PathVariable Long id) {
        log.debug("REST request to get CompteAnalytiqueClient : {}", id);
        CompteAnalytiqueClient compteAnalytiqueClient = compteAnalytiqueClientRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(compteAnalytiqueClient));
    }

    /**
     * DELETE  /compte-analytique-clients/:id : delete the "id" compteAnalytiqueClient.
     *
     * @param id the id of the compteAnalytiqueClient to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/compte-analytique-clients/{id}")
    @Timed
    public ResponseEntity<Void> deleteCompteAnalytiqueClient(@PathVariable Long id) {
        log.debug("REST request to delete CompteAnalytiqueClient : {}", id);
        compteAnalytiqueClientRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
