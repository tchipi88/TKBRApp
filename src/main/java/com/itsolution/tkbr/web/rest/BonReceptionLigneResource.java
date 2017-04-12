package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.BonReceptionLigne;

import com.itsolution.tkbr.repository.BonReceptionLigneRepository;
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
 * REST controller for managing BonReceptionLigne.
 */
@RestController
@RequestMapping("/api")
public class BonReceptionLigneResource {

    private final Logger log = LoggerFactory.getLogger(BonReceptionLigneResource.class);

    private static final String ENTITY_NAME = "bonReceptionLigne";
        
    private final BonReceptionLigneRepository bonReceptionLigneRepository;


    public BonReceptionLigneResource(BonReceptionLigneRepository bonReceptionLigneRepository) {
        this.bonReceptionLigneRepository = bonReceptionLigneRepository;
    }

    /**
     * POST  /bon-reception-lignes : Create a new bonReceptionLigne.
     *
     * @param bonReceptionLigne the bonReceptionLigne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonReceptionLigne, or with status 400 (Bad Request) if the bonReceptionLigne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-reception-lignes")
    @Timed
    public ResponseEntity<BonReceptionLigne> createBonReceptionLigne(@Valid @RequestBody BonReceptionLigne bonReceptionLigne) throws URISyntaxException {
        log.debug("REST request to save BonReceptionLigne : {}", bonReceptionLigne);
        if (bonReceptionLigne.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonReceptionLigne cannot already have an ID")).body(null);
        }
        BonReceptionLigne result = bonReceptionLigneRepository.save(bonReceptionLigne);
        return ResponseEntity.created(new URI("/api/bon-reception-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-reception-lignes : Updates an existing bonReceptionLigne.
     *
     * @param bonReceptionLigne the bonReceptionLigne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonReceptionLigne,
     * or with status 400 (Bad Request) if the bonReceptionLigne is not valid,
     * or with status 500 (Internal Server Error) if the bonReceptionLigne couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-reception-lignes")
    @Timed
    public ResponseEntity<BonReceptionLigne> updateBonReceptionLigne(@Valid @RequestBody BonReceptionLigne bonReceptionLigne) throws URISyntaxException {
        log.debug("REST request to update BonReceptionLigne : {}", bonReceptionLigne);
        if (bonReceptionLigne.getId() == null) {
            return createBonReceptionLigne(bonReceptionLigne);
        }
        BonReceptionLigne result = bonReceptionLigneRepository.save(bonReceptionLigne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonReceptionLigne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bon-reception-lignes : get all the bonReceptionLignes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonReceptionLignes in body
     */
    @GetMapping("/bon-reception-lignes")
    @Timed
    public ResponseEntity<List<BonReceptionLigne>> getAllBonReceptionLignes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all BonReceptionLignes");
        Page<BonReceptionLigne> page = bonReceptionLigneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bon-reception-lignes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /bon-reception-lignes/:id : get the "id" bonReceptionLigne.
     *
     * @param id the id of the bonReceptionLigne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonReceptionLigne, or with status 404 (Not Found)
     */
    @GetMapping("/bon-reception-lignes/{id}")
    @Timed
    public ResponseEntity<BonReceptionLigne> getBonReceptionLigne(@PathVariable Long id) {
        log.debug("REST request to get BonReceptionLigne : {}", id);
        BonReceptionLigne bonReceptionLigne = bonReceptionLigneRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonReceptionLigne));
    }

    /**
     * DELETE  /bon-reception-lignes/:id : delete the "id" bonReceptionLigne.
     *
     * @param id the id of the bonReceptionLigne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-reception-lignes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonReceptionLigne(@PathVariable Long id) {
        log.debug("REST request to delete BonReceptionLigne : {}", id);
        bonReceptionLigneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
