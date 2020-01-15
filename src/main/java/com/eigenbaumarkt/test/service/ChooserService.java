package com.eigenbaumarkt.test.service;

import com.eigenbaumarkt.test.domain.Chooser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Chooser}.
 */
public interface ChooserService {

    /**
     * Save a chooser.
     *
     * @param chooser the entity to save.
     * @return the persisted entity.
     */
    Chooser save(Chooser chooser);

    /**
     * Get all the choosers.
     *
     * @return the list of entities.
     */
    List<Chooser> findAll();


    /**
     * Get the "id" chooser.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Chooser> findOne(Long id);

    /**
     * Delete the "id" chooser.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
