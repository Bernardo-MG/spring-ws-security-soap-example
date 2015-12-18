package com.wandrell.demo.ws.soap.spring.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.wandrell.demo.ws.soap.spring.model.Sample;
import com.wandrell.demo.ws.soap.spring.model.SamplePk;

/**
 * The Interface SampleRepository.
 */
public interface SampleRepository extends JpaRepository<Sample, SamplePk>,
        JpaSpecificationExecutor<Sample> {

    /**
     * Find by sample pk in.
     *
     * @param samplePkSet
     *            the sample pk set
     * @return the list
     */
    public List<Sample> findBySamplePkIn(final Set<SamplePk> samplePkSet);

}
