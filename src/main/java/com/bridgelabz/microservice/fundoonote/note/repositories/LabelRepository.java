package com.bridgelabz.microservice.fundoonote.note.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bridgelabz.microservice.fundoonote.note.models.Label;



public interface LabelRepository extends MongoRepository<Label,String> {

	/**
	 * @param userId
	 * @return
	 */
	List<Label> findAllByUserId(String userId);

	/**
	 * @param labelName
	 * @return
	 */
	Optional<Label> findByLabelName(String labelName);


	/**
	 * @param labelId
	 * @param userId
	 * @return
	 */
	Optional<Label> findByLabelIdAndUserId(String labelId, String userId);

	/**
	 * @param labelId
	 */
	void deleteByLabelId(String labelId);

	/**
	 * @param labelName
	 * @param userId
	 * @return
	 */
	Optional<Label> findByLabelNameAndUserId(String labelName,String userId);


	
	


}
