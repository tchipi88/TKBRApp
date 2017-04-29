package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Terrain;

import com.itsolution.tkbr.repository.TerrainRepository;
import com.itsolution.tkbr.repository.search.TerrainSearchRepository;
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
 * REST controller for managing Terrain.
 */
@RestController
@RequestMapping("/api")
public class TerrainResource {

    private final Logger log = LoggerFactory.getLogger(TerrainResource.class);

    private static final String ENTITY_NAME = "terrain";
        
    private final TerrainRepository terrainRepository;

    private final TerrainSearchRepository terrainSearchRepository;

    public TerrainResource(TerrainRepository terrainRepository, TerrainSearchRepository terrainSearchRepository) {
        this.terrainRepository = terrainRepository;
        this.terrainSearchRepository = terrainSearchRepository;
    }

    /**
     * POST  /terrains : Create a new terrain.
     *
     * @param terrain the terrain to create
     * @return the ResponseEntity with status 201 (Created) and with body the new terrain, or with status 400 (Bad Request) if the terrain has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/terrains")
    @Timed
    public ResponseEntity<Terrain> createTerrain(@Valid @RequestBody Terrain terrain) throws URISyntaxException {
        log.debug("REST request to save Terrain : {}", terrain);
        if (terrain.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new terrain cannot already have an ID")).body(null);
        }
        Terrain result = terrainRepository.save(terrain);
        terrainSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/terrains/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /terrains : Updates an existing terrain.
     *
     * @param terrain the terrain to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated terrain,
     * or with status 400 (Bad Request) if the terrain is not valid,
     * or with status 500 (Internal Server Error) if the terrain couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/terrains")
    @Timed
    public ResponseEntity<Terrain> updateTerrain(@Valid @RequestBody Terrain terrain) throws URISyntaxException {
        log.debug("REST request to update Terrain : {}", terrain);
        if (terrain.getId() == null) {
            return createTerrain(terrain);
        }
        Terrain result = terrainRepository.save(terrain);
        terrainSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, terrain.getId().toString()))
            .body(result);
    }

   /**
     * GET  /terrains : get all the terrains.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of terrains in body
     */
    @GetMapping("/terrains")
    @Timed
    public ResponseEntity<List<Terrain>> getAllTerrains(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Terrains");
        Page<Terrain> page = terrainRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/terrains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /terrains/:id : get the "id" terrain.
     *
     * @param id the id of the terrain to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the terrain, or with status 404 (Not Found)
     */
    @GetMapping("/terrains/{id}")
    @Timed
    public ResponseEntity<Terrain> getTerrain(@PathVariable Long id) {
        log.debug("REST request to get Terrain : {}", id);
        Terrain terrain = terrainRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(terrain));
    }

    /**
     * DELETE  /terrains/:id : delete the "id" terrain.
     *
     * @param id the id of the terrain to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/terrains/{id}")
    @Timed
    public ResponseEntity<Void> deleteTerrain(@PathVariable Long id) {
        log.debug("REST request to delete Terrain : {}", id);
        terrainRepository.delete(id);
        terrainSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/terrains?query=:query : search for the terrain corresponding
     * to the query.
     *
     * @param query the query of the terrain search 
     * @return the result of the search
     */
    @GetMapping("/_search/terrains")
    @Timed
    public ResponseEntity<List<Terrain>> searchTerrains(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search Terrains for query {}", query);
      Page<Terrain> page = terrainSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/terrains");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




}
