package com.bridgelabz.microservice.fundoonote.note.controllers;

import java.io.IOException;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.microservice.fundoonote.note.exceptions.LabelNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.MalformedUrlException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.NoteNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.NoteNotTrashedException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.ReminderDateNotValidException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.UnAuthorizedException;
import com.bridgelabz.microservice.fundoonote.note.exceptions.UserNotFoundException;
import com.bridgelabz.microservice.fundoonote.note.models.CreateNoteDTO;
import com.bridgelabz.microservice.fundoonote.note.models.NoteDTO;
import com.bridgelabz.microservice.fundoonote.note.models.Response;
import com.bridgelabz.microservice.fundoonote.note.models.UpdateNoteDTO;
import com.bridgelabz.microservice.fundoonote.note.services.NoteService;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	/**
	 * @param createNoteDTO
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws LabelNotFoundException
	 * @throws ReminderDateNotValidException
	 * @throws UserNotFoundException
	 * @throws IOException
	 * @throws MalformedUrlException
	 */
	@PostMapping("/create")
	public ResponseEntity<NoteDTO> createNote(@RequestBody CreateNoteDTO createNoteDTO, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, LabelNotFoundException, ReminderDateNotValidException,
			UserNotFoundException, IOException, MalformedUrlException {
		System.out.println(request.getHeader("AUTH_HEADER_TOKEN") + " got token in note................");
		NoteDTO note = noteService.createNote(createNoteDTO, request.getHeader("AUTH_HEADER_TOKEN"));

		return new ResponseEntity<>(note, HttpStatus.CREATED);
	}

	/**
	 * @param userId
	 * @param noteId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/delete/{noteId}")
	public ResponseEntity<Response> deleteNote(@RequestHeader("userId") String userId,
			@PathVariable("noteId") String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.trashNote(userId, noteId);

		Response responseDTO = new Response();
		responseDTO.setMessage("Note trashed Successfully!!");
		responseDTO.setStatus(2);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param updateNoteDTO
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/update/{noteId}")
	public ResponseEntity<Response> updateNote(@RequestBody UpdateNoteDTO updateNoteDTO, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.updateNote(updateNoteDTO, request.getHeader("AUTH_HEADER_TOKEN"));

		Response responseDTO = new Response();
		responseDTO.setMessage("Updated Note Successfull!!");
		responseDTO.setStatus(3);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@GetMapping("/get/{noteId}")
	public ResponseEntity<NoteDTO> viewNote(@PathVariable String noteId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {
System.out.println(request.getHeader("AUTH_HEADER_TOKEN")+" pppppppppppppppppp");
		return new ResponseEntity<>(noteService.viewNote(noteId, request.getHeader("AUTH_HEADER_TOKEN")),
				HttpStatus.OK);
	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @param isdelete
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws NoteNotTrashedException
	 * @throws UserNotFoundException
	 */
	@DeleteMapping("/deleteForeverOrRestore/{noteId}")
	public ResponseEntity<Response> deleteOrRestoreTrashedNote(@PathVariable String noteId, HttpServletRequest request,
			@RequestBody boolean isdelete)
			throws NoteNotFoundException, UnAuthorizedException, NoteNotTrashedException, UserNotFoundException {

		noteService.deleteOrRestoreTrashedNote(noteId, request.getHeader("AUTH_HEADER_TOKEN"), isdelete);

		Response responseDTO = new Response();
		responseDTO.setMessage("Deleted trashed Note Successfully!!");
		responseDTO.setStatus(4);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@DeleteMapping("/emptyTrash")
	public ResponseEntity<Response> emptyTrash(HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException {

		noteService.emptyTrash(request.getHeader("AUTH_HEADER_TOKEN"));

		Response responseDTO = new Response();
		responseDTO.setMessage("Trash is emptied Successfully!!");
		responseDTO.setStatus(5);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param userId
	 * @param request
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/removereminder/{noteId}")
	public ResponseEntity<Response> removeRemainder(@PathVariable String noteId, HttpServletRequest request)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.removeReminder(noteId, request.getHeader("AUTH_HEADER_TOKEN"));

		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder removed Successfully!!");
		responseDTO.setStatus(6);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param noteId
	 * @param request
	 * @param reminder
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws ReminderDateNotValidException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/addreminder/{noteId}")
	public ResponseEntity<Response> addReminder(@PathVariable String noteId, HttpServletRequest request,
			@RequestBody Date reminder)
			throws NoteNotFoundException, UnAuthorizedException, ReminderDateNotValidException, UserNotFoundException {

		noteService.addReminder(noteId, request.getHeader("AUTH_HEADER_TOKEN"), reminder);

		Response responseDTO = new Response();
		responseDTO.setMessage("Reminder added Successfully!!");
		responseDTO.setStatus(7);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 */
	@GetMapping("/getall")
	public Iterable<NoteDTO> viewAllNotes(HttpServletRequest request) throws NoteNotFoundException {

		return noteService.viewAllNotes(request.getHeader("AUTH_HEADER_TOKEN"));
	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws NoteNotTrashedException
	 */
	@GetMapping("/getalltrashed")
	public Iterable<NoteDTO> viewAllTrashedNotes(HttpServletRequest request)
			throws NoteNotFoundException, NoteNotTrashedException {

		return noteService.viewAllTrashedNotes(request.getHeader("AUTH_HEADER_TOKEN"));
	}

	/**
	 * @param request
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 */
	@GetMapping("/getarchive")
	public Iterable<NoteDTO> getArchiveNotes(HttpServletRequest request) throws NoteNotFoundException {

		return noteService.getArchiveNotes(request.getHeader("AUTH_HEADER_TOKEN"));
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/setarchive/{noteId}")
	public ResponseEntity<Response> setArchive(HttpServletRequest request, @PathVariable String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.setArchive(request.getHeader("AUTH_HEADER_TOKEN"), noteId);

		Response responseDTO = new Response();
		responseDTO.setMessage("Note Archived Successfully!!");
		responseDTO.setStatus(8);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/unarchive/{noteId}")
	public ResponseEntity<Response> unArchive(HttpServletRequest request, @PathVariable String noteId)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.unArchive(request.getHeader("AUTH_HEADER_TOKEN"), noteId);

		Response responseDTO = new Response();
		responseDTO.setMessage("Note UnArchived Successfully!!");
		responseDTO.setStatus(9);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param request
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 * @throws UserNotFoundException
	 */
	@PutMapping("/pinOrUnpin/{noteId}")
	public ResponseEntity<Response> pinNote(HttpServletRequest request, @PathVariable String noteId, boolean isPin)
			throws NoteNotFoundException, UnAuthorizedException, UserNotFoundException {

		noteService.pinOrUnpinNote(request.getHeader("AUTH_HEADER_TOKEN"), noteId, isPin);

		Response responseDTO = new Response();
		responseDTO.setMessage("Note pinned Successfully!!");
		responseDTO.setStatus(8);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);
	}

	/**
	 * @param color
	 * @param noteId
	 * @param userId
	 * @return
	 * @throws UserNotFoundException
	 * @throws LabelNotFoundException
	 * @throws NoteNotFoundException
	 * @throws UnAuthorizedException
	 */
	@PutMapping("/changecolour/{noteId}")
	public ResponseEntity<Response> changeColour(@RequestParam String color, @PathVariable String noteId,
			HttpServletRequest request) throws UserNotFoundException, NoteNotFoundException, UnAuthorizedException {

		noteService.changeColour(noteId, request.getHeader("AUTH_HEADER_TOKEN"), color);

		Response responseDTO = new Response();
		responseDTO.setMessage("Color changed Successfully!!");
		responseDTO.setStatus(14);

		return new ResponseEntity<>(responseDTO, HttpStatus.OK);

	}

}
