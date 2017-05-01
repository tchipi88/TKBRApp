package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.Authority;
import com.itsolution.tkbr.domain.User;
import com.itsolution.tkbr.repository.AuthorityRepository;
import com.itsolution.tkbr.web.rest.util.HeaderUtil;
import com.itsolution.tkbr.web.rest.util.PaginationUtil;

import io.github.jhipster.web.util.ResponseUtil;
import io.swagger.annotations.ApiParam;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for managing Authority.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);

    private static final String ENTITY_NAME = "authority";
        
    private final AuthorityRepository authorityRepository;


    public AuthorityResource(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /**
     * POST  /authoritys : Create a new authority.
     *
     * @param authority the authority to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authority, or with status 400 (Bad Request) if the authority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/authoritys")
    @Timed
    public ResponseEntity<Authority> createAuthority(@Valid @RequestBody Authority authority) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authority);
        if (authority.getName() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new authority cannot already have an ID")).body(null);
        }
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.created(new URI("/api/authoritys/" + result.getName()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getName().toString()))
            .body(result);
    }

    /**
     * PUT  /authoritys : Updates an existing authority.
     *
     * @param authority the authority to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authority,
     * or with status 400 (Bad Request) if the authority is not valid,
     * or with status 500 (Internal Server Error) if the authority couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/authoritys")
    @Timed
    public ResponseEntity<Authority> updateAuthority(@Valid @RequestBody Authority authority) throws URISyntaxException {
        log.debug("REST request to update Authority : {}", authority);
        if (authority.getName() == null) {
            return createAuthority(authority);
        }       
        Authority result = authorityRepository.save(authority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, authority.getName().toString()))
            .body(result);
    }

   /**
     * GET  /authoritys : get all the authoritys.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of authoritys in body
     */
    @GetMapping("/authoritys")
    @Timed
    public ResponseEntity<List<Authority>> getAllAuthoritys(@ApiParam Pageable pageable) {
        log.debug("REST request to get all Authoritys");
        Page<Authority> page = authorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authoritys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authoritys/:id : get the "id" authority.
     *
     * @param id the id of the authority to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authority, or with status 404 (Not Found)
     */
    @GetMapping("/authoritys/{id}")
    @Timed
    public ResponseEntity<Authority> getAuthority(@PathVariable String id) {
        log.debug("REST request to get Authority : {}", id);
       // Authority authority = authorityRepository.findOne(id); 
        Authority authority = authorityRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(authority));
    }

    /**
     * DELETE  /authoritys/:id : delete the "id" authority.
     *
     * @param id the id of the authority to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/authoritys/{id}")
    @Timed
    public ResponseEntity<Void> deleteAuthority(@PathVariable String id) {
        log.debug("REST request to delete Authority : {}", id);
        authorityRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
  /**********************************************************************
   *				 CODE AJOUTES
   * ********************************************************************
   */
    
  /*  *//**
     * GET  /authoritys : get all the authoritys of strind transform.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of name of authoritys in body
     *//*
    @GetMapping("/authoritys/string")
    @Timed
    public List<String> getAllAuthoritysTransformOfString() {
    	List<String> authoritiesString=new ArrayList<String>();
        log.debug("REST request to get all Authoritys into String");
        List<Authority> liste = authorityRepository.findAll();
        for(int i=0;i<liste.size();i++){
        	authoritiesString.add(liste.get(i).getName());
        }         
        return authoritiesString;
        
    }
    */
   
    

}
