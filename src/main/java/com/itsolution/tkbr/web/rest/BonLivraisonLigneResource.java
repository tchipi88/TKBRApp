package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.BonLivraisonLigne;

import com.itsolution.tkbr.repository.BonLivraisonLigneRepository;
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
 * REST controller for managing BonLivraisonLigne.
 */
@RestController
@RequestMapping("/api")
public class BonLivraisonLigneResource {

    private final Logger log = LoggerFactory.getLogger(BonLivraisonLigneResource.class);

    private static final String ENTITY_NAME = "bonLivraisonLigne";
        
    private final BonLivraisonLigneRepository bonLivraisonLigneRepository;


    public BonLivraisonLigneResource(BonLivraisonLigneRepository bonLivraisonLigneRepository) {
        this.bonLivraisonLigneRepository = bonLivraisonLigneRepository;
    }

    /**
     * POST  /bon-livraison-lignes : Create a new bonLivraisonLigne.
     *
     * @param bonLivraisonLigne the bonLivraisonLigne to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bonLivraisonLigne, or with status 400 (Bad Request) if the bonLivraisonLigne has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/bon-livraison-lignes")
    @Timed
    public ResponseEntity<BonLivraisonLigne> createBonLivraisonLigne(@Valid @RequestBody BonLivraisonLigne bonLivraisonLigne) throws URISyntaxException {
        log.debug("REST request to save BonLivraisonLigne : {}", bonLivraisonLigne);
        if (bonLivraisonLigne.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new bonLivraisonLigne cannot already have an ID")).body(null);
        }
        BonLivraisonLigne result = bonLivraisonLigneRepository.save(bonLivraisonLigne);
        return ResponseEntity.created(new URI("/api/bon-livraison-lignes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bon-livraison-lignes : Updates an existing bonLivraisonLigne.
     *
     * @param bonLivraisonLigne the bonLivraisonLigne to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bonLivraisonLigne,
     * or with status 400 (Bad Request) if the bonLivraisonLigne is not valid,
     * or with status 500 (Internal Server Error) if the bonLivraisonLigne couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/bon-livraison-lignes")
    @Timed
    public ResponseEntity<BonLivraisonLigne> updateBonLivraisonLigne(@Valid @RequestBody BonLivraisonLigne bonLivraisonLigne) throws URISyntaxException {
        log.debug("REST request to update BonLivraisonLigne : {}", bonLivraisonLigne);
        if (bonLivraisonLigne.getId() == null) {
            return createBonLivraisonLigne(bonLivraisonLigne);
        }
        BonLivraisonLigne result = bonLivraisonLigneRepository.save(bonLivraisonLigne);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, bonLivraisonLigne.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bon-livraison-lignes : get all the bonLivraisonLignes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of bonLivraisonLignes in body
     */
    @GetMapping("/bon-livraison-lignes")
    @Timed
    public ResponseEntity<List<BonLivraisonLigne>> getAllBonLivraisonLignes(@ApiParam Pageable pageable) {
        log.debug("REST request to get all BonLivraisonLignes");
        Page<BonLivraisonLigne> page = bonLivraisonLigneRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bon-livraison-lignes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /bon-livraison-lignes/:id : get the "id" bonLivraisonLigne.
     *
     * @param id the id of the bonLivraisonLigne to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bonLivraisonLigne, or with status 404 (Not Found)
     */
    @GetMapping("/bon-livraison-lignes/{id}")
    @Timed
    public ResponseEntity<BonLivraisonLigne> getBonLivraisonLigne(@PathVariable Long id) {
        log.debug("REST request to get BonLivraisonLigne : {}", id);
        BonLivraisonLigne bonLivraisonLigne = bonLivraisonLigneRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(bonLivraisonLigne));
    }

    /**
     * DELETE  /bon-livraison-lignes/:id : delete the "id" bonLivraisonLigne.
     *
     * @param id the id of the bonLivraisonLigne to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/bon-livraison-lignes/{id}")
    @Timed
    public ResponseEntity<Void> deleteBonLivraisonLigne(@PathVariable Long id) {
        log.debug("REST request to delete BonLivraisonLigne : {}", id);
        bonLivraisonLigneRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
