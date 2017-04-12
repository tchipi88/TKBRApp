package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.CaisseCloture;

import com.itsolution.tkbr.repository.CaisseClotureRepository;
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
 * REST controller for managing CaisseCloture.
 */
@RestController
@RequestMapping("/api")
public class CaisseClotureResource {

    private final Logger log = LoggerFactory.getLogger(CaisseClotureResource.class);

    private static final String ENTITY_NAME = "caisseCloture";
        
    private final CaisseClotureRepository caisseClotureRepository;


    public CaisseClotureResource(CaisseClotureRepository caisseClotureRepository) {
        this.caisseClotureRepository = caisseClotureRepository;
    }

    /**
     * POST  /caisse-clotures : Create a new caisseCloture.
     *
     * @param caisseCloture the caisseCloture to create
     * @return the ResponseEntity with status 201 (Created) and with body the new caisseCloture, or with status 400 (Bad Request) if the caisseCloture has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/caisse-clotures")
    @Timed
    public ResponseEntity<CaisseCloture> createCaisseCloture(@Valid @RequestBody CaisseCloture caisseCloture) throws URISyntaxException {
        log.debug("REST request to save CaisseCloture : {}", caisseCloture);
        if (caisseCloture.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new caisseCloture cannot already have an ID")).body(null);
        }
        CaisseCloture result = caisseClotureRepository.save(caisseCloture);
        return ResponseEntity.created(new URI("/api/caisse-clotures/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /caisse-clotures : Updates an existing caisseCloture.
     *
     * @param caisseCloture the caisseCloture to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated caisseCloture,
     * or with status 400 (Bad Request) if the caisseCloture is not valid,
     * or with status 500 (Internal Server Error) if the caisseCloture couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/caisse-clotures")
    @Timed
    public ResponseEntity<CaisseCloture> updateCaisseCloture(@Valid @RequestBody CaisseCloture caisseCloture) throws URISyntaxException {
        log.debug("REST request to update CaisseCloture : {}", caisseCloture);
        if (caisseCloture.getId() == null) {
            return createCaisseCloture(caisseCloture);
        }
        CaisseCloture result = caisseClotureRepository.save(caisseCloture);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, caisseCloture.getId().toString()))
            .body(result);
    }

    /**
     * GET  /caisse-clotures : get all the caisseClotures.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of caisseClotures in body
     */
    @GetMapping("/caisse-clotures")
    @Timed
    public ResponseEntity<List<CaisseCloture>> getAllCaisseClotures(@ApiParam Pageable pageable) {
        log.debug("REST request to get all CaisseClotures");
        Page<CaisseCloture> page = caisseClotureRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/caisse-clotures");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /caisse-clotures/:id : get the "id" caisseCloture.
     *
     * @param id the id of the caisseCloture to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the caisseCloture, or with status 404 (Not Found)
     */
    @GetMapping("/caisse-clotures/{id}")
    @Timed
    public ResponseEntity<CaisseCloture> getCaisseCloture(@PathVariable Long id) {
        log.debug("REST request to get CaisseCloture : {}", id);
        CaisseCloture caisseCloture = caisseClotureRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(caisseCloture));
    }

    /**
     * DELETE  /caisse-clotures/:id : delete the "id" caisseCloture.
     *
     * @param id the id of the caisseCloture to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/caisse-clotures/{id}")
    @Timed
    public ResponseEntity<Void> deleteCaisseCloture(@PathVariable Long id) {
        log.debug("REST request to delete CaisseCloture : {}", id);
        caisseClotureRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
