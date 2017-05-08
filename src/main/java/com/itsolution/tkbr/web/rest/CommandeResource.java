package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Commande;
import com.itsolution.tkbr.domain.enumeration.EtatCommande;
import com.itsolution.tkbr.domain.enumeration.TypeCommande;

import com.itsolution.tkbr.repository.CommandeRepository;
import com.itsolution.tkbr.service.CommandeService;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing Commande.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    private static final String ENTITY_NAME = "commande";

    private final CommandeRepository commandeRepository;

    private final CommandeService commandeService;

    public CommandeResource(CommandeRepository commandeRepository, CommandeService commandeService) {
        this.commandeRepository = commandeRepository;
        this.commandeService = commandeService;
    }

    /**
     * POST /commandes : Create a new commande.
     *
     * @param commande the commande to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new commande, or with status 400 (Bad Request) if the commande has
     * already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commandes")
    @Timed
    public ResponseEntity<Commande> createCommande(@RequestBody Commande commande) throws Exception {
        log.debug("REST request to save Commande : {}", commande);
        if (commande.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commande cannot already have an ID")).body(null);
        }
        Commande result = commandeService.create(commande);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /commandes : Updates an existing commande.
     *
     * @param commande the commande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * commande, or with status 400 (Bad Request) if the commande is not valid,
     * or with status 500 (Internal Server Error) if the commande couldnt be
     * updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commandes")
    @Timed
    public ResponseEntity<Commande> updateCommande(@RequestBody Commande commande) throws Exception {
        log.debug("REST request to update Commande : {}", commande);
        if (commande.getId() == null) {
            return createCommande(commande);
        }
        Commande result = commandeService.update(commande);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commande.getId().toString()))
                .body(result);
    }

    /**
     * GET /commandes : get all the commandes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of commandes
     * in body
     */
    @GetMapping("/commandes")
    @Timed
    public ResponseEntity<List<Commande>> getAllCommandes(@ApiParam Pageable pageable, @ApiParam TypeCommande type) {
        log.debug("REST request to get all Commandes");
        Page<Commande> page = commandeRepository.findByType(pageable, type);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /commandes/:id : get the "id" commande.
     *
     * @param id the id of the commande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the
     * commande, or with status 404 (Not Found)
     */
    @GetMapping("/commandes/{id}")
    @Timed
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        Commande commande = commandeRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commande));
    }

    /**
     * DELETE /commandes/:id : delete the "id" commande.
     *
     * @param id the id of the commande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);
        commandeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * GET /print-commande/:id : print facture or bon-livraison for the "id"
     * commande.
     *
     * @param id the id of the commande to print
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/print-commande/{id}")
    @Timed
    public ResponseEntity<byte[]> printCommande(@PathVariable Long id, @ApiParam EtatCommande type) throws Exception {
        log.debug("REST request to print facture Commande : {}", id);
        Resource resource = commandeService.print(commandeRepository.findOne(id), type);
        InputStream in = resource.getInputStream();
        try {
            return new ResponseEntity<>(IOUtils.toByteArray(in), HeaderUtil.downloadAlert(resource), HttpStatus.OK);
        } finally {
            IOUtils.closeQuietly(in);
        }
    }

   /**
     * GET /commandess : get a page of Commandes between the fromDate and toDate.
     *
     * @param fromDate the start of the time period of Commande to get
     * @param toDate the end of the time period of Commande to get
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of
     * Commandes in body
     */
    @GetMapping(path = "/commandes", params = {"fromDate", "toDate"})
    @Timed
    public ResponseEntity<List<Commande>> searchCommandes(
            @RequestParam(value = "fromDate") LocalDate fromDate,
            @RequestParam(value = "toDate") LocalDate toDate,
           // @RequestParam Long fournisseur,
            //@RequestParam Long client,
            @ApiParam Pageable pageable) {
        log.debug("REST request to search for a page of Commandes for  {}  to {}", fromDate, toDate);
        Page<Commande> page = commandeRepository.findAllByDateEmissionBetween(fromDate, toDate, pageable);
//        if (fournisseur != null) {
//            page = commandeRepository.findAllByDateEmissionBetweenAndFournisseurId(fromDate, toDate, pageable, fournisseur);
//        }
//        if (client != null) {
//            page = commandeRepository.findAllByDateEmissionBetweenAndClientId(fromDate, toDate, pageable, client);
//        }
//        if (client == null && fournisseur == null) {
//            page = commandeRepository.findAllByDateEmissionBetween(fromDate, toDate, pageable);
//        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
