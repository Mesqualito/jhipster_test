package com.eigenbaumarkt.test.repository;

import com.eigenbaumarkt.test.domain.Chooser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Chooser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChooserRepository extends JpaRepository<Chooser, Long> {

}
