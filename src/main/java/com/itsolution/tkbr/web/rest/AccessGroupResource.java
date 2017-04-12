package com.itsolution.tkbr.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.itsolution.tkbr.domain.AccessGroup;

import com.itsolution.tkbr.repository.AccessGroupRepository;
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
 * REST controller for managing AccessGroup.
 */
@RestController
@RequestMapping("/api")
public class AccessGroupResource {

    private final Logger log = LoggerFactory.getLogger(AccessGroupResource.class);

    private static final String ENTITY_NAME = "accessGroup";
        
    private final AccessGroupRepository accessGroupRepository;


    public AccessGroupResource(AccessGroupRepository accessGroupRepository) {
        this.accessGroupRepository = accessGroupRepository;
    }

    /**
     * POST  /access-groups : Create a new accessGroup.
     *
     * @param accessGroup the accessGroup to create
     * @return the ResponseEntity with status 201 (Created) and with body the new accessGroup, or with status 400 (Bad Request) if the accessGroup has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/access-groups")
    @Timed
    public ResponseEntity<AccessGroup> createAccessGroup(@Valid @RequestBody AccessGroup accessGroup) throws URISyntaxException {
        log.debug("REST request to save AccessGroup : {}", accessGroup);
        if (accessGroup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new accessGroup cannot already have an ID")).body(null);
        }
        AccessGroup result = accessGroupRepository.save(accessGroup);
        return ResponseEntity.created(new URI("/api/access-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /access-groups : Updates an existing accessGroup.
     *
     * @param accessGroup the accessGroup to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated accessGroup,
     * or with status 400 (Bad Request) if the accessGroup is not valid,
     * or with status 500 (Internal Server Error) if the accessGroup couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/access-groups")
    @Timed
    public ResponseEntity<AccessGroup> updateAccessGroup(@Valid @RequestBody AccessGroup accessGroup) throws URISyntaxException {
        log.debug("REST request to update AccessGroup : {}", accessGroup);
        if (accessGroup.getId() == null) {
            return createAccessGroup(accessGroup);
        }
        AccessGroup result = accessGroupRepository.save(accessGroup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, accessGroup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /access-groups : get all the accessGroups.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of accessGroups in body
     */
    @GetMapping("/access-groups")
    @Timed
    public ResponseEntity<List<AccessGroup>> getAllAccessGroups(@ApiParam Pageable pageable) {
        log.debug("REST request to get all AccessGroups");
        Page<AccessGroup> page = accessGroupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/access-groups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

 

    /**
     * GET  /access-groups/:id : get the "id" accessGroup.
     *
     * @param id the id of the accessGroup to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the accessGroup, or with status 404 (Not Found)
     */
    @GetMapping("/access-groups/{id}")
    @Timed
    public ResponseEntity<AccessGroup> getAccessGroup(@PathVariable Long id) {
        log.debug("REST request to get AccessGroup : {}", id);
        AccessGroup accessGroup = accessGroupRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(accessGroup));
    }

    /**
     * DELETE  /access-groups/:id : delete the "id" accessGroup.
     *
     * @param id the id of the accessGroup to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/access-groups/{id}")
    @Timed
    public ResponseEntity<Void> deleteAccessGroup(@PathVariable Long id) {
        log.debug("REST request to delete AccessGroup : {}", id);
        accessGroupRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

   


}
