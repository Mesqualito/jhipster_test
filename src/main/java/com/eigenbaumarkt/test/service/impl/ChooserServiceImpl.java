package com.eigenbaumarkt.test.service.impl;

import com.eigenbaumarkt.test.domain.Chooser;
import com.eigenbaumarkt.test.repository.ChooserRepository;
import com.eigenbaumarkt.test.service.ChooserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Chooser}.
 */
@Service
@Transactional
public class ChooserServiceImpl implements ChooserService {

    private final Logger log = LoggerFactory.getLogger(ChooserServiceImpl.class);

    private final ChooserRepository chooserRepository;

    public ChooserServiceImpl(ChooserRepository chooserRepository) {
        this.chooserRepository = chooserRepository;
    }

    /**
     * Save a chooser.
     *
     * @param chooser the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Chooser save(Chooser chooser) {
        log.debug("Request to save Chooser : {}", chooser);
        return chooserRepository.save(chooser);
    }

    /**
     * Get all the choosers.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<Chooser> findAll() {
        log.debug("Request to get all Choosers");
        return chooserRepository.findAll();
    }


    /**
     * Get one chooser by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Chooser> findOne(Long id) {
        log.debug("Request to get Chooser : {}", id);
        return chooserRepository.findById(id);
    }

    /**
     * Delete the chooser by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Chooser : {}", id);
        chooserRepository.deleteById(id);
    }
}
