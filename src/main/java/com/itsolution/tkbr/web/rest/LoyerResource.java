package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Loyer;

import com.itsolution.tkbr.repository.LoyerRepository;
import com.itsolution.tkbr.service.LoyerService;
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
 * REST controller for managing Loyer.
 */
@RestController
@RequestMapping("/api")
public class LoyerResource {

    private final Logger log = LoggerFactory.getLogger(LoyerResource.class);

    private static final String ENTITY_NAME = "loyer";
        
    private final LoyerRepository loyerRepository;
    
    private final LoyerService loyerService;


    public LoyerResource(LoyerRepository loyerRepository,LoyerService loyerService) {
        this.loyerRepository = loyerRepository;
        this.loyerService=loyerService;
    }

    /**
     * POST  /loyers : Create a new loyer.
     *
     * @param loyer the loyer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new loyer, or with status 400 (Bad Request) if the loyer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/loyers")
    @Timed
    public ResponseEntity<Loyer> createLoyer(@Valid @RequestBody Loyer loyer) throws Exception {
        log.debug("REST request to save Loyer : {}", loyer);
        if (loyer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new loyer cannot already have an ID")).body(null);
        }
        Loyer result = loyerService.save(loyer);
        return ResponseEntity.created(new URI("/api/loyers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /loyers : Updates an existing loyer.
     *
     * @param loyer the loyer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated loyer,
     * or with status 400 (Bad Request) if the loyer is not valid,
     * or with status 500 (Internal Server Error) if the loyer couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/loyers")
    @Timed
    public ResponseEntity<Loyer> updateLoyer(@Valid @RequestBody Loyer loyer) throws Exception {
        log.debug("REST request to update Loyer : {}", loyer);
        if (loyer.getId() == null) {
            return createLoyer(loyer);
        }
        Loyer result = loyerService.save(loyer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, loyer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /loyers : get all the loyers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of loyers in body
     */
    @GetMapping("/loyers")
    @Timed
    public ResponseEntity<List<Loyer>> getAllLoyers(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Loyers");
        Page<Loyer> page = loyerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/loyers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /loyers/:id : get the "id" loyer.
     *
     * @param id the id of the loyer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the loyer, or with status 404 (Not Found)
     */
    @GetMapping("/loyers/{id}")
    @Timed
    public ResponseEntity<Loyer> getLoyer(@PathVariable Long id) {
        log.debug("REST request to get Loyer : {}", id);
        Loyer loyer = loyerRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(loyer));
    }

    /**
     * DELETE  /loyers/:id : delete the "id" loyer.
     *
     * @param id the id of the loyer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/loyers/{id}")
    @Timed
    public ResponseEntity<Void> deleteLoyer(@PathVariable Long id) {
        log.debug("REST request to delete Loyer : {}", id);
        loyerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
