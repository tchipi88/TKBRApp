package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.TerrainSuivi;

import com.itsolution.tkbr.repository.TerrainSuiviRepository;
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
 * REST controller for managing TerrainSuivi.
 */
@RestController
@RequestMapping("/api")
public class TerrainSuiviResource {

    private final Logger log = LoggerFactory.getLogger(TerrainSuiviResource.class);

    private static final String ENTITY_NAME = "terrainSuivi";
        
    private final TerrainSuiviRepository terrainSuiviRepository;


    public TerrainSuiviResource(TerrainSuiviRepository terrainSuiviRepository) {
        this.terrainSuiviRepository = terrainSuiviRepository;
    }

    /**
     * POST  /terrain-suivis : Create a new terrainSuivi.
     *
     * @param terrainSuivi the terrainSuivi to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrainSuivi, or with status 400 (Bad Request) if the terrainSuivi has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrain-suivis")
    @Timed
    public ResponseEntity<TerrainSuivi> createTerrainSuivi(@Valid @RequestBody TerrainSuivi terrainSuivi) throws URISyntaxException {
        log.debug("REST request to save TerrainSuivi : {}", terrainSuivi);
        if (terrainSuivi.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terrainSuivi cannot already have an ID")).body(null);
        }
        TerrainSuivi result = terrainSuiviRepository.save(terrainSuivi);
        return ResponseEntity.created(new URI("/api/terrain-suivis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrain-suivis : Updates an existing terrainSuivi.
     *
     * @param terrainSuivi the terrainSuivi to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrainSuivi,
     * or with status 400 (Bad Request) if the terrainSuivi is not valid,
     * or with status 500 (Internal Server Error) if the terrainSuivi couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrain-suivis")
    @Timed
    public ResponseEntity<TerrainSuivi> updateTerrainSuivi(@Valid @RequestBody TerrainSuivi terrainSuivi) throws URISyntaxException {
        log.debug("REST request to update TerrainSuivi : {}", terrainSuivi);
        if (terrainSuivi.getId() == null) {
            return createTerrainSuivi(terrainSuivi);
        }
        TerrainSuivi result = terrainSuiviRepository.save(terrainSuivi);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrainSuivi.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrain-suivis : get all the terrainSuivis.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrainSuivis in body
     */
    @GetMapping("/terrain-suivis")
    @Timed
    public ResponseEntity<List<TerrainSuivi>> getAllTerrainSuivis(@ApiParam Pageable pageable) {
        log.debug("REST request to get all TerrainSuivis");
        Page<TerrainSuivi> page = terrainSuiviRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terrain-suivis");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /terrain-suivis/:id : get the "id" terrainSuivi.
     *
     * @param id the id of the terrainSuivi to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrainSuivi, or with status 404 (Not Found)
     */
    @GetMapping("/terrain-suivis/{id}")
    @Timed
    public ResponseEntity<TerrainSuivi> getTerrainSuivi(@PathVariable Long id) {
        log.debug("REST request to get TerrainSuivi : {}", id);
        TerrainSuivi terrainSuivi = terrainSuiviRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrainSuivi));
    }

    /**
     * DELETE  /terrain-suivis/:id : delete the "id" terrainSuivi.
     *
     * @param id the id of the terrainSuivi to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrain-suivis/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerrainSuivi(@PathVariable Long id) {
        log.debug("REST request to delete TerrainSuivi : {}", id);
        terrainSuiviRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
