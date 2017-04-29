package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.TerrainReglement;

import com.itsolution.tkbr.repository.TerrainReglementRepository;
import com.itsolution.tkbr.service.TerrainReglementService;
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
 * REST controller for managing TerrainReglement.
 */
@RestController
@RequestMapping("/api")
public class TerrainReglementResource {

    private final Logger log = LoggerFactory.getLogger(TerrainReglementResource.class);

    private static final String ENTITY_NAME = "terrainReglement";
        
    private final TerrainReglementRepository terrainReglementRepository;
    private final TerrainReglementService terrainReglementService;


    public TerrainReglementResource(TerrainReglementRepository terrainReglementRepository,TerrainReglementService terrainReglementService) {
        this.terrainReglementRepository = terrainReglementRepository;
        this.terrainReglementService=terrainReglementService;
    }

    /**
     * POST  /terrain-reglements : Create a new terrainReglement.
     *
     * @param terrainReglement the terrainReglement to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrainReglement, or with status 400 (Bad Request) if the terrainReglement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrain-reglements")
    @Timed
    public ResponseEntity<TerrainReglement> createTerrainReglement(@Valid @RequestBody TerrainReglement terrainReglement) throws Exception {
        log.debug("REST request to save TerrainReglement : {}", terrainReglement);
        if (terrainReglement.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terrainReglement cannot already have an ID")).body(null);
        }
        TerrainReglement result = terrainReglementService.save(terrainReglement);
        return ResponseEntity.created(new URI("/api/terrain-reglements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrain-reglements : Updates an existing terrainReglement.
     *
     * @param terrainReglement the terrainReglement to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrainReglement,
     * or with status 400 (Bad Request) if the terrainReglement is not valid,
     * or with status 500 (Internal Server Error) if the terrainReglement couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrain-reglements")
    @Timed
    public ResponseEntity<TerrainReglement> updateTerrainReglement(@Valid @RequestBody TerrainReglement terrainReglement) throws Exception {
        log.debug("REST request to update TerrainReglement : {}", terrainReglement);
        if (terrainReglement.getId() == null) {
            return createTerrainReglement(terrainReglement);
        }
        TerrainReglement result = terrainReglementService.save(terrainReglement);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrainReglement.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrain-reglements : get all the terrainReglements.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrainReglements in body
     */
    @GetMapping("/terrain-reglements")
    @Timed
    public ResponseEntity<List<TerrainReglement>> getAllTerrainReglements(@ApiParam Pageable pageable) {
        log.debug("REST request to get all TerrainReglements");
        Page<TerrainReglement> page = terrainReglementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terrain-reglements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /terrain-reglements/:id : get the "id" terrainReglement.
     *
     * @param id the id of the terrainReglement to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrainReglement, or with status 404 (Not Found)
     */
    @GetMapping("/terrain-reglements/{id}")
    @Timed
    public ResponseEntity<TerrainReglement> getTerrainReglement(@PathVariable Long id) {
        log.debug("REST request to get TerrainReglement : {}", id);
        TerrainReglement terrainReglement = terrainReglementRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrainReglement));
    }

    /**
     * DELETE  /terrain-reglements/:id : delete the "id" terrainReglement.
     *
     * @param id the id of the terrainReglement to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrain-reglements/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerrainReglement(@PathVariable Long id) {
        log.debug("REST request to delete TerrainReglement : {}", id);
        terrainReglementRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

     /**
     * GET  /reglementss/:id : get the "id" commande.
     *
     * @param id the id of the commande
     * @return la liste des reglements associés à la commande passé en arguments with status 200 (OK) and with body the commandeLigne, or with status 404 (Not Found)
     */
    @GetMapping("/terrain-reglementss/{id}")
    @Timed
    public List<TerrainReglement> getCommandeLigneByCommande(@PathVariable Long id) {
        log.debug("REST request to get Reglements to Commande : {}", id);
        return  terrainReglementRepository.findByCommandeId(id);
    }


}
