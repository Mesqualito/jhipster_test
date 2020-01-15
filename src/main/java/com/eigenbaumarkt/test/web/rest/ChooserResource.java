package com.eigenbaumarkt.test.web.rest;

import com.eigenbaumarkt.test.domain.Chooser;
import com.eigenbaumarkt.test.service.ChooserService;
import com.eigenbaumarkt.test.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.eigenbaumarkt.test.domain.Chooser}.
 */
@RestController
@RequestMapping("/api")
public class ChooserResource {

    private final Logger log = LoggerFactory.getLogger(ChooserResource.class);

    private static final String ENTITY_NAME = "chooser";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChooserService chooserService;

    public ChooserResource(ChooserService chooserService) {
        this.chooserService = chooserService;
    }

    /**
     * {@code POST  /choosers} : Create a new chooser.
     *
     * @param chooser the chooser to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new chooser, or with status {@code 400 (Bad Request)} if the chooser has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/choosers")
    public ResponseEntity<Chooser> createChooser(@RequestBody Chooser chooser) throws URISyntaxException {
        log.debug("REST request to save Chooser : {}", chooser);
        if (chooser.getId() != null) {
            throw new BadRequestAlertException("A new chooser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Chooser result = chooserService.save(chooser);
        return ResponseEntity.created(new URI("/api/choosers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /choosers} : Updates an existing chooser.
     *
     * @param chooser the chooser to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated chooser,
     * or with status {@code 400 (Bad Request)} if the chooser is not valid,
     * or with status {@code 500 (Internal Server Error)} if the chooser couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/choosers")
    public ResponseEntity<Chooser> updateChooser(@RequestBody Chooser chooser) throws URISyntaxException {
        log.debug("REST request to update Chooser : {}", chooser);
        if (chooser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Chooser result = chooserService.save(chooser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, chooser.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /choosers} : get all the choosers.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of choosers in body.
     */
    @GetMapping("/choosers")
    public List<Chooser> getAllChoosers() {
        log.debug("REST request to get all Choosers");
        return chooserService.findAll();
    }

    /**
     * {@code GET  /choosers/:id} : get the "id" chooser.
     *
     * @param id the id of the chooser to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the chooser, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/choosers/{id}")
    public ResponseEntity<Chooser> getChooser(@PathVariable Long id) {
        log.debug("REST request to get Chooser : {}", id);
        Optional<Chooser> chooser = chooserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(chooser);
    }

    /**
     * {@code DELETE  /choosers/:id} : delete the "id" chooser.
     *
     * @param id the id of the chooser to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/choosers/{id}")
    public ResponseEntity<Void> deleteChooser(@PathVariable Long id) {
        log.debug("REST request to delete Chooser : {}", id);
        chooserService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
