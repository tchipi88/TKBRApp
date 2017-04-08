package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.CommandeLigne;
import com.itsolution.tkbr.service.CommandeLigneService;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

/**
 * REST controller for managing CommandeLigne.
 */
@RestController
@RequestMapping("/api")
public class CommandeLigneResource {

    private final Logger log = LoggerFactory.getLogger(CommandeLigneResource.class);

    private static final String ENTITY_NAME = "commandeLigne";
        
    private final CommandeLigneService commandeLigneService;

    public CommandeLigneResource(CommandeLigneService commandeLigneService) {
        this.commandeLigneService = commandeLigneService;
    }

    /**
     * POST  /commande-lignes : Create a new commandeLigne.
     *
     * @param commandeLigne the commandeLigne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commandeLigne, or with status 400 (Bad Request) if the commandeLigne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commande-lignes")
    @Timed
    public ResponseEntity<CommandeLigne> createCommandeLigne(@Valid @RequestBody CommandeLigne commandeLigne) throws URISyntaxException {
        log.debug("REST request to save CommandeLigne : {}", commandeLigne);
        if (commandeLigne.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commandeLigne cannot already have an ID")).body(null);
        }
        CommandeLigne result = commandeLigneService.save(commandeLigne);
        return ResponseEntity.created(new URI("/api/commande-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commande-lignes : Updates an existing commandeLigne.
     *
     * @param commandeLigne the commandeLigne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commandeLigne,
     * or with status 400 (Bad Request) if the commandeLigne is not valid,
     * or with status 500 (Internal Server Error) if the commandeLigne couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commande-lignes")
    @Timed
    public ResponseEntity<CommandeLigne> updateCommandeLigne(@Valid @RequestBody CommandeLigne commandeLigne) throws URISyntaxException {
        log.debug("REST request to update CommandeLigne : {}", commandeLigne);
        if (commandeLigne.getId() == null) {
            return createCommandeLigne(commandeLigne);
        }
        CommandeLigne result = commandeLigneService.save(commandeLigne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commandeLigne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commande-lignes : get all the commandeLignes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commandeLignes in body
     */
    @GetMapping("/commande-lignes")
    @Timed
    public ResponseEntity<List<CommandeLigne>> getAllCommandeLignes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of CommandeLignes");
        Page<CommandeLigne> page = commandeLigneService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commande-lignes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commande-lignes/:id : get the "id" commandeLigne.
     *
     * @param id the id of the commandeLigne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commandeLigne, or with status 404 (Not Found)
     */
    @GetMapping("/commande-lignes/{id}")
    @Timed
    public ResponseEntity<CommandeLigne> getCommandeLigne(@PathVariable Long id) {
        log.debug("REST request to get CommandeLigne : {}", id);
        CommandeLigne commandeLigne = commandeLigneService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commandeLigne));
    }

    /**
     * DELETE  /commande-lignes/:id : delete the "id" commandeLigne.
     *
     * @param id the id of the commandeLigne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commande-lignes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommandeLigne(@PathVariable Long id) {
        log.debug("REST request to delete CommandeLigne : {}", id);
        commandeLigneService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commande-lignes?query=:query : search for the commandeLigne corresponding
     * to the query.
     *
     * @param query the query of the commandeLigne search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/commande-lignes")
    @Timed
    public ResponseEntity<List<CommandeLigne>> searchCommandeLignes(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of CommandeLignes for query {}", query);
        Page<CommandeLigne> page = commandeLigneService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commande-lignes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}