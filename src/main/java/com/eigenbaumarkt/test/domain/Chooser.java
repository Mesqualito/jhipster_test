package com.eigenbaumarkt.test.domain;

import com.eigenbaumarkt.test.domain.enumeration.Enums;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A Chooser.
 */
@Entity
@Table(name = "chooser")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Chooser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "enumeration")
    private Enums enumeration;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Enums getEnumeration() {
        return enumeration;
    }

    public Chooser enumeration(Enums enumeration) {
        this.enumeration = enumeration;
        return this;
    }

    public void setEnumeration(Enums enumeration) {
        this.enumeration = enumeration;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Chooser)) {
            return false;
        }
        return id != null && id.equals(((Chooser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Chooser{" +
            "id=" + getId() +
            ", enumeration='" + getEnumeration() + "'" +
            "}";
    }
}
