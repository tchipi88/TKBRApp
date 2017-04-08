package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.ProduitFournisseur;

import com.itsolution.tkbr.repository.ProduitFournisseurRepository;
import com.itsolution.tkbr.repository.search.ProduitFournisseurSearchRepository;
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
 * REST controller for managing ProduitFournisseur.
 */
@RestController
@RequestMapping("/api")
public class ProduitFournisseurResource {

    private final Logger log = LoggerFactory.getLogger(ProduitFournisseurResource.class);

    private static final String ENTITY_NAME = "produitFournisseur";
        
    private final ProduitFournisseurRepository produitFournisseurRepository;

    private final ProduitFournisseurSearchRepository produitFournisseurSearchRepository;

    public ProduitFournisseurResource(ProduitFournisseurRepository produitFournisseurRepository, ProduitFournisseurSearchRepository produitFournisseurSearchRepository) {
        this.produitFournisseurRepository = produitFournisseurRepository;
        this.produitFournisseurSearchRepository = produitFournisseurSearchRepository;
    }

    /**
     * POST  /produit-fournisseurs : Create a new produitFournisseur.
     *
     * @param produitFournisseur the produitFournisseur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new produitFournisseur, or with status 400 (Bad Request) if the produitFournisseur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<ProduitFournisseur> createProduitFournisseur(@Valid @RequestBody ProduitFournisseur produitFournisseur) throws URISyntaxException {
        log.debug("REST request to save ProduitFournisseur : {}", produitFournisseur);
        if (produitFournisseur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new produitFournisseur cannot already have an ID")).body(null);
        }
        ProduitFournisseur result = produitFournisseurRepository.save(produitFournisseur);
        produitFournisseurSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/produit-fournisseurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /produit-fournisseurs : Updates an existing produitFournisseur.
     *
     * @param produitFournisseur the produitFournisseur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated produitFournisseur,
     * or with status 400 (Bad Request) if the produitFournisseur is not valid,
     * or with status 500 (Internal Server Error) if the produitFournisseur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<ProduitFournisseur> updateProduitFournisseur(@Valid @RequestBody ProduitFournisseur produitFournisseur) throws URISyntaxException {
        log.debug("REST request to update ProduitFournisseur : {}", produitFournisseur);
        if (produitFournisseur.getId() == null) {
            return createProduitFournisseur(produitFournisseur);
        }
        ProduitFournisseur result = produitFournisseurRepository.save(produitFournisseur);
        produitFournisseurSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, produitFournisseur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /produit-fournisseurs : get all the produitFournisseurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of produitFournisseurs in body
     */
    @GetMapping("/produit-fournisseurs")
    @Timed
    public ResponseEntity<List<ProduitFournisseur>> getAllProduitFournisseurs(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of ProduitFournisseurs");
        Page<ProduitFournisseur> page = produitFournisseurRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/produit-fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /produit-fournisseurs/:id : get the "id" produitFournisseur.
     *
     * @param id the id of the produitFournisseur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the produitFournisseur, or with status 404 (Not Found)
     */
    @GetMapping("/produit-fournisseurs/{id}")
    @Timed
    public ResponseEntity<ProduitFournisseur> getProduitFournisseur(@PathVariable Long id) {
        log.debug("REST request to get ProduitFournisseur : {}", id);
        ProduitFournisseur produitFournisseur = produitFournisseurRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(produitFournisseur));
    }

    /**
     * DELETE  /produit-fournisseurs/:id : delete the "id" produitFournisseur.
     *
     * @param id the id of the produitFournisseur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/produit-fournisseurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteProduitFournisseur(@PathVariable Long id) {
        log.debug("REST request to delete ProduitFournisseur : {}", id);
        produitFournisseurRepository.delete(id);
        produitFournisseurSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/produit-fournisseurs?query=:query : search for the produitFournisseur corresponding
     * to the query.
     *
     * @param query the query of the produitFournisseur search 
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/produit-fournisseurs")
    @Timed
    public ResponseEntity<List<ProduitFournisseur>> searchProduitFournisseurs(@RequestParam String query, @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of ProduitFournisseurs for query {}", query);
        Page<ProduitFournisseur> page = produitFournisseurSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/produit-fournisseurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}