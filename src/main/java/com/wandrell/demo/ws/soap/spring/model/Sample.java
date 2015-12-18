package com.wandrell.demo.ws.soap.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Sample entity.
 */
@Entity
@Table(name = "sample")
public class Sample implements Serializable {

    /** The Constant DESCRIPTION_LENGTH. */
    public static final int DESCRIPTION_LENGTH = 30;

    /** The Constant EXTRA_LENGTH. */
    public static final int EXTRA_LENGTH = 50;

    /** The Constant serialVersionUID. */
    @Transient
    @XmlTransient
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /** The description. */
    @Column(name = "description", length = DESCRIPTION_LENGTH)
    private String description;

    /** The extra. */
    @Column(name = "extra", length = EXTRA_LENGTH)
    private String extra;

    /** The sample pk. */
    @EmbeddedId
    @Valid
    private SamplePk samplePk;

    /**
     * Instantiates a new sample.
     */
    public Sample() {
        super();
    }

    /**
     * Instantiates a new sample.
     *
     * @param sample
     *            the sample
     */
    public Sample(Sample sample) {
        super();
        BeanUtils.copyProperties(sample, this);
    }

    /**
     * Gets the description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the extra.
     *
     * @return the extra
     */
    public String getExtra() {
        return extra;
    }

    /**
     * Gets the sample pk.
     *
     * @return the sample pk
     */
    public SamplePk getSamplePk() {
        return samplePk;
    }

    /**
     * Sets the description.
     *
     * @param decentro
     *            the new description
     */
    public void setDescription(String decentro) {
        this.description = decentro;
    }

    /**
     * Sets the extra.
     *
     * @param dlocalid
     *            the new extra
     */
    public void setExtra(String dlocalid) {
        this.extra = dlocalid;
    }

    /**
     * Sets the sample pk.
     *
     * @param samplePk
     *            the new sample pk
     */
    public void setSamplePk(SamplePk samplePk) {
        this.samplePk = samplePk;
    }

}
