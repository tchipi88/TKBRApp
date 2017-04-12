package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.EmployeDepartement;

import com.itsolution.tkbr.repository.EmployeDepartementRepository;
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
 * REST controller for managing EmployeDepartement.
 */
@RestController
@RequestMapping("/api")
public class EmployeDepartementResource {

    private final Logger log = LoggerFactory.getLogger(EmployeDepartementResource.class);

    private static final String ENTITY_NAME = "employeDepartement";
        
    private final EmployeDepartementRepository employeDepartementRepository;


    public EmployeDepartementResource(EmployeDepartementRepository employeDepartementRepository) {
        this.employeDepartementRepository = employeDepartementRepository;
    }

    /**
     * POST  /employe-departements : Create a new employeDepartement.
     *
     * @param employeDepartement the employeDepartement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeDepartement, or with status 400 (Bad Request) if the employeDepartement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employe-departements")
    @Timed
    public ResponseEntity<EmployeDepartement> createEmployeDepartement(@Valid @RequestBody EmployeDepartement employeDepartement) throws URISyntaxException {
        log.debug("REST request to save EmployeDepartement : {}", employeDepartement);
        if (employeDepartement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new employeDepartement cannot already have an ID")).body(null);
        }
        EmployeDepartement result = employeDepartementRepository.save(employeDepartement);
        return ResponseEntity.created(new URI("/api/employe-departements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employe-departements : Updates an existing employeDepartement.
     *
     * @param employeDepartement the employeDepartement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeDepartement,
     * or with status 400 (Bad Request) if the employeDepartement is not valid,
     * or with status 500 (Internal Server Error) if the employeDepartement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employe-departements")
    @Timed
    public ResponseEntity<EmployeDepartement> updateEmployeDepartement(@Valid @RequestBody EmployeDepartement employeDepartement) throws URISyntaxException {
        log.debug("REST request to update EmployeDepartement : {}", employeDepartement);
        if (employeDepartement.getId() == null) {
            return createEmployeDepartement(employeDepartement);
        }
        EmployeDepartement result = employeDepartementRepository.save(employeDepartement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeDepartement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employe-departements : get all the employeDepartements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of employeDepartements in body
     */
    @GetMapping("/employe-departements")
    @Timed
    public ResponseEntity<List<EmployeDepartement>> getAllEmployeDepartements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all EmployeDepartements");
        Page<EmployeDepartement> page = employeDepartementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employe-departements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /employe-departements/:id : get the "id" employeDepartement.
     *
     * @param id the id of the employeDepartement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeDepartement, or with status 404 (Not Found)
     */
    @GetMapping("/employe-departements/{id}")
    @Timed
    public ResponseEntity<EmployeDepartement> getEmployeDepartement(@PathVariable Long id) {
        log.debug("REST request to get EmployeDepartement : {}", id);
        EmployeDepartement employeDepartement = employeDepartementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeDepartement));
    }

    /**
     * DELETE  /employe-departements/:id : delete the "id" employeDepartement.
     *
     * @param id the id of the employeDepartement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employe-departements/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeDepartement(@PathVariable Long id) {
        log.debug("REST request to delete EmployeDepartement : {}", id);
        employeDepartementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
