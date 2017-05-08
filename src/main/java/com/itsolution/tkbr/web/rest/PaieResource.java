package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Paie;

import com.itsolution.tkbr.repository.PaieRepository;
import com.itsolution.tkbr.service.PaieService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Paie.
 */
@RestController
@RequestMapping("/api")
public class PaieResource {

    private final Logger log = LoggerFactory.getLogger(PaieResource.class);

    private static final String ENTITY_NAME = "paie";
        
    private final PaieRepository paieRepository;
    private final PaieService paieService;


    public PaieResource(PaieRepository paieRepository,PaieService paieService) {
        this.paieRepository = paieRepository;
        this.paieService=paieService;
    }

    /**
     * POST  /paies : Create a new paie.
     *
     * @param paie the paie to create
     * @return the ResponseEntity with status 201 (Created) and with body the new paie, or with status 400 (Bad Request) if the paie has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/paies")
    @Timed
    public ResponseEntity<Paie> createPaie(@Valid @RequestBody Paie paie) throws Exception {
        log.debug("REST request to save Paie : {}", paie);
        if (paie.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new paie cannot already have an ID")).body(null);
        }
        Paie result = paieService.save(paie);
        return ResponseEntity.created(new URI("/api/paies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /paies : Updates an existing paie.
     *
     * @param paie the paie to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated paie,
     * or with status 400 (Bad Request) if the paie is not valid,
     * or with status 500 (Internal Server Error) if the paie couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/paies")
    @Timed
    public ResponseEntity<Paie> updatePaie(@Valid @RequestBody Paie paie) throws Exception {
        log.debug("REST request to update Paie : {}", paie);
        if (paie.getId() == null) {
            return createPaie(paie);
        }
        Paie result = paieService.save(paie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, paie.getId().toString()))
            .body(result);
    }

    /**
     * GET  /paies : get all the paies.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of paies in body
     */
    @GetMapping("/paies")
    @Timed
    public ResponseEntity<List<Paie>> getAllPaies(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Paies");
        Page<Paie> page = paieRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /paies/:id : get the "id" paie.
     *
     * @param id the id of the paie to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the paie, or with status 404 (Not Found)
     */
    @GetMapping("/paies/{id}")
    @Timed
    public ResponseEntity<Paie> getPaie(@PathVariable Long id) {
        log.debug("REST request to get Paie : {}", id);
        Paie paie = paieRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(paie));
    }

    /**
     * DELETE  /paies/:id : delete the "id" paie.
     *
     * @param id the id of the paie to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/paies/{id}")
    @Timed
    public ResponseEntity<Void> deletePaie(@PathVariable Long id) {
        log.debug("REST request to delete Paie : {}", id);
        paieRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   
    /**
     * GET /paies : get a page of paies between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of paies to get
     * @param toDate the end of the time period of paies to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * loyers in body
     */
    @GetMapping(path = "/paies", params = {"fromDate", "toDate"})
    public ResponseEntity<List<Paie>> getByDates(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
            @ApiParam Pageable pageable) {

        Page<Paie> page = paieRepository.findAllByDateVersementBetween(fromDate, toDate, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/paies");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
