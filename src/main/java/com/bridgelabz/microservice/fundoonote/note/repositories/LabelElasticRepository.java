package com.bridgelabz.microservice.fundoonote.note.repositories;

import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.microservice.fundoonote.note.models.Label;


public interface LabelElasticRepository extends ElasticsearchRepository<Label,String>  {

	/**
	 * @param labelId
	 */
	void deleteByLabelId(String labelId);

	/**
	 * @param labelId
	 * @param userId
	 * @return
	 */
	Optional<Label> findByLabelIdAndUserId(String labelId, String userId);

}
