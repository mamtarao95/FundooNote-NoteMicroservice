package com.bridgelabz.microservice.fundoonote.note.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.microservice.fundoonote.note.exceptions.LabelNameAlreadyUsedException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.LabelNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.models.LabelDTO;
import com.bridgelabz.microservice.fundoonote.note.models.NoteDTO;
import com.bridgelabz.microservice.fundoonote.note.models.Response;
import com.bridgelabz.microservice.fundoonote.note.services.LabelService;


@RestController
@RequestMapping("/label") 
public class LabelController {

	@Autowired
	private LabelService labelService;

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws LabelNotFoundException
	 */
	@GetMapping("/getall")
	public Iterable<LabelDTO> getLabels(HttpServletRequest request, @RequestHeader("userId") String userId)
			throws LabelNotFoundException {
		
		return labelService.getLabels(userId);
	}

	/**
	 * @param labelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws LabelNameAlreadyUsedException 
	 */
	@PostMapping("/create")
	public ResponseEntity<LabelDTO> createLabel(@RequestParam String labelName,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException, LabelNameAlreadyUsedException {
		System.out.println(request.getHeader("AUTH_HEADER_TOKEN")+"iiiiiiiiiiiiiiii");
		LabelDTO labelViewDTO = labelService.createLabel(labelName, request.getHeader("AUTH_HEADER_TOKEN"));

		return new ResponseEntity<>(labelViewDTO, HttpStatus.CREATED);
	}

	/**
	 * @param labelId
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteLabel(@RequestParam String labelId, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		
		labelService.deleteLabel(labelId, userId);
		
		Response responseDTO = new Response();
		responseDTO.setMessage("Label deleted Successfully!!");
		responseDTO.setStatus(10);
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelId
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws LabelNameAlreadyUsedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/add")
	public ResponseEntity<Response> addLabel(@RequestParam String labelId, @RequestParam("noteId") String noteId, // pathvar
			@RequestHeader("userId") String userId, HttpServletRequest request) throws NoteNotFoundException,
			UnAuthorizedException, LabelNotFoundException, LabelNameAlreadyUsedException, UserNotFoundException {

		labelService.addLabel(labelId, userId, noteId);

		Response responseDTO = new Response();
		responseDTO.setMessage("Label added Successfully!!");
		responseDTO.setStatus(11);
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param labelId
	 * @param newLabelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/rename")
	public ResponseEntity<Response> renameLabel(@RequestParam String labelId, @RequestParam String newLabelName,
			@RequestHeader("userId") String userId)
			throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		
		labelService.renameLabel(labelId, userId, newLabelName);
		
		Response responseDTO = new Response();
		responseDTO.setMessage("Label renamed Successfully!!");
		responseDTO.setStatus(12);
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param labelName
	 * @param userId
	 * @param request
	 * @return
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/getnotesoflabel")
	public Iterable<NoteDTO> getNotesOfLabel(@RequestBody String labelName, @RequestHeader("userId") String userId,
			HttpServletRequest request) throws UnAuthorizedException, LabelNotFoundException, UserNotFoundException {
		
		return labelService.getNotesOfLabel(labelName, userId);

	}

	

	/**
	 * @param labelId
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@DeleteMapping("/removelabel")
	public ResponseEntity<Response> removeLabel(@RequestParam String labelId, @RequestParam String noteId,
			@RequestHeader("userId") String userId)
			throws UserNotFoundException, LabelNotFoundException, NoteNotFoundException, UnAuthorizedException {
		
		labelService.removeLabel(userId, labelId, noteId);
		
		Response responseDTO = new Response();
		responseDTO.setMessage("Label removed Successfully!!");
		responseDTO.setStatus(13);
		
		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

}
