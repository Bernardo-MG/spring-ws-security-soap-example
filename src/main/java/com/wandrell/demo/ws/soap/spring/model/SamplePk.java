package com.wandrell.demo.ws.soap.spring.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.BeanUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * The Class SamplePk.
 */

@Embeddable
public class SamplePk implements Serializable {

    /** The Constant COD1_FRACTION. */
    public static final int COD1_FRACTION = 0;

    /** The Constant COD1_INTEGER. */
    public static final int COD1_INTEGER = 3;

    /** The Constant COD2_FRACTION. */
    public static final int COD2_FRACTION = 0;

    /** The Constant COD2_INTEGER. */
    public static final int COD2_INTEGER = 4;

    /** The Constant serialVersionUID. */
    @Transient
    @XmlTransient
    @JsonIgnore
    private static final long serialVersionUID = 1L;

    /** The cod1. */
    @Column(name = "cod1", precision = COD1_INTEGER, scale = COD1_FRACTION)
    @Digits(integer = COD1_INTEGER, fraction = COD1_FRACTION)
    private BigDecimal cod1;

    /** The cod2. */
    @Column(name = "cod2", precision = COD2_INTEGER, scale = COD2_FRACTION)
    @Digits(integer = COD2_INTEGER, fraction = COD2_FRACTION)
    private BigDecimal cod2;

    /**
     * Instantiates a new sample pk.
     */
    public SamplePk() {
        super();
    }

    /**
     * Instantiates a new sample pk.
     *
     * @param samplePk
     *            the sample pk
     */
    public SamplePk(SamplePk samplePk) {
        BeanUtils.copyProperties(samplePk, this);
    }

    /**
     * Gets the cod1.
     *
     * @return the cod1
     */
    public BigDecimal getCod1() {
        return cod1;
    }

    /**
     * Gets the cod2.
     *
     * @return the cod2
     */
    public BigDecimal getCod2() {
        return cod2;
    }

    /**
     * Sets the cod1.
     *
     * @param cod1
     *            the new cod1
     */
    public void setCod1(BigDecimal cod1) {
        this.cod1 = cod1;
    }

    /**
     * Sets the cod2.
     *
     * @param cod2
     *            the new cod2
     */
    public void setCod2(BigDecimal cod2) {
        this.cod2 = cod2;
    }

}
