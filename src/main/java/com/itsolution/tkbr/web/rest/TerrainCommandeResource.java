package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.TerrainCommande;
import com.itsolution.tkbr.domain.enumeration.TypeCommande;

import com.itsolution.tkbr.repository.TerrainCommandeRepository;
import com.itsolution.tkbr.service.TerrainCommandeService;
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
 * REST controller for managing TerrainCommande.
 */
@RestController
@RequestMapping("/api")
public class TerrainCommandeResource {

    private final Logger log = LoggerFactory.getLogger(TerrainCommandeResource.class);

    private static final String ENTITY_NAME = "terrainCommande";
        
    private final TerrainCommandeRepository terrainCommandeRepository;
    private final TerrainCommandeService terrainCommandeService;


    public TerrainCommandeResource(TerrainCommandeRepository terrainCommandeRepository,TerrainCommandeService terrainCommandeService) {
        this.terrainCommandeRepository = terrainCommandeRepository;
        this.terrainCommandeService=terrainCommandeService;
    }

    /**
     * POST  /terrain-commandes : Create a new terrainCommande.
     *
     * @param terrainCommande the terrainCommande to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrainCommande, or with status 400 (Bad Request) if the terrainCommande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrain-commandes")
    @Timed
    public ResponseEntity<TerrainCommande> createTerrainCommande(@RequestBody TerrainCommande terrainCommande) throws Exception {
        log.debug("REST request to save TerrainCommande : {}", terrainCommande);
        if (terrainCommande.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terrainCommande cannot already have an ID")).body(null);
        }
        TerrainCommande result = terrainCommandeService.create(terrainCommande);
        return ResponseEntity.created(new URI("/api/terrain-commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrain-commandes : Updates an existing terrainCommande.
     *
     * @param terrainCommande the terrainCommande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrainCommande,
     * or with status 400 (Bad Request) if the terrainCommande is not valid,
     * or with status 500 (Internal Server Error) if the terrainCommande couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrain-commandes")
    @Timed
    public ResponseEntity<TerrainCommande> updateTerrainCommande(@RequestBody TerrainCommande terrainCommande) throws Exception {
        log.debug("REST request to update TerrainCommande : {}", terrainCommande);
        if (terrainCommande.getId() == null) {
            return createTerrainCommande(terrainCommande);
        }
        TerrainCommande result = terrainCommandeRepository.save(terrainCommande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrainCommande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /terrain-commandes : get all the terrainCommandes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrainCommandes in body
     */
    @GetMapping("/terrain-commandes")
    @Timed
    public ResponseEntity<List<TerrainCommande>> getAllTerrainCommandes(@ApiParam Pageable pageable,@ApiParam TypeCommande  type) {
        log.debug("REST request to get all TerrainCommandes");
        Page<TerrainCommande> page = terrainCommandeRepository.findByType(pageable,type);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terrain-commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /terrain-commandes/:id : get the "id" terrainCommande.
     *
     * @param id the id of the terrainCommande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrainCommande, or with status 404 (Not Found)
     */
    @GetMapping("/terrain-commandes/{id}")
    @Timed
    public ResponseEntity<TerrainCommande> getTerrainCommande(@PathVariable Long id) {
        log.debug("REST request to get TerrainCommande : {}", id);
        TerrainCommande terrainCommande = terrainCommandeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrainCommande));
    }

    /**
     * DELETE  /terrain-commandes/:id : delete the "id" terrainCommande.
     *
     * @param id the id of the terrainCommande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrain-commandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerrainCommande(@PathVariable Long id) {
        log.debug("REST request to delete TerrainCommande : {}", id);
        terrainCommandeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
