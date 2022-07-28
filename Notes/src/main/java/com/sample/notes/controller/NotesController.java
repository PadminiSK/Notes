package com.sample.notes.controller;

import java.rmi.ServerException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.sample.notes.model.*;
import com.sample.notes.repository.MavNotesRepository;
import com.sample.notes.repository.MavNotesUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sample.notes.repository.NoteUsersRepository;
import com.sample.notes.repository.NotesRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1")
public class NotesController {

	@Autowired
	NoteUsersRepository noteUsersRepo;

	@Autowired
	MavNotesUserRepository mavNotesUserRepository;

	@Autowired
	MavNotesRepository mavNotesRepository;

	@Autowired
	NotesRepository notesRepo;

	// registered the user
	@PostMapping("/create/note-user")
	public ResponseEntity<NoteUsers> create(@RequestBody NoteUsers newUser) throws ServerException {
		newUser.setCreated_at(new Date());
		newUser.setUpdated_at(new Date());
		NoteUsers user = noteUsersRepo.save(newUser);
		if (user == null) {
			throw new ServerException("Error in creating new user");
		} else {
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
	}

	// create notes of user
	@PostMapping("/note-user/{email}/new-note")
	public ResponseEntity<Notes> createNoteForAUser(@PathVariable String email, @RequestBody Notes note)
			throws ServerException {
		Notes newNote = null;

		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				note.setNote_users(noteUser);
				newNote = notesRepo.save(note);
				break;
			}
		}
		if (newNote == null) {
			throw new ServerException("the user is not registered");
		} else {
			return new ResponseEntity<>(newNote, HttpStatus.CREATED);
		}
	}

	// get the notes of user
	@GetMapping("/note-user/fetch-notes/{email}")
	public List<Optional<Notes>> getNotesOfUser(@PathVariable String email) throws ServerException {
		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		List<Notes> notesList = null;
		List<Optional<Notes>> notesListOfUser = new ArrayList<>();
		Optional<Notes> note1;
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				notesList = notesRepo.findAll();
				for (Notes note : notesList) {
					if (noteUser.getUser_Id() == note.getNote_users().getUser_Id()) {
						note1 = notesRepo.findById(note.getId());
						notesListOfUser.add(note1);
					}
				}
				break;
			}
		}
		if (notesListOfUser != null) {
			return notesListOfUser;
		} else {
			throw new ServerException("the user is not registered");
		}
	}

	@PutMapping("/note-user/{email}/update-notes")
	public ResponseEntity<Notes> updateNoteForSpecificUser(@PathVariable String email,
			@RequestBody Notes note) throws ServerException {
		Notes newNote = null;

		List<NoteUsers> noteUserList = noteUsersRepo.findAll();
		for (NoteUsers noteUser : noteUserList) {
			if (noteUser.getEmail_id().equals(email)) {
				Notes specificNote = notesRepo.getById(note.getId());
				if(specificNote != null) {
					specificNote.setNote_users(noteUser);
					specificNote.setNote(note.getNote());
				newNote = notesRepo.save(specificNote);
				break;
				}
			}
		}
		if (newNote == null) {
			throw new ServerException("the user is not registered");
		} else {
			return new ResponseEntity<>(newNote, HttpStatus.OK);
		}
	}

	// fetch all notes (for userId if provided)
	@GetMapping("/notes")
	public List<MavNotes> getNotesByUser(@RequestParam(name = "userId", required = false) Optional<Integer> userId) throws ServerException {
		if(userId.isPresent()) {
			return mavNotesRepository.findAllByUserId(userId.get());
		}
		return mavNotesRepository.findAll();
	}

	//create a new note
	@PostMapping("/notes")
	public MavNotes createNote(@RequestBody MavNotes note) throws ServerException {
		note.setCreated_at(new Date());
		note.setUpdated_at(new Date());
		return mavNotesRepository.save(note);
	}

	// update a note
	@PutMapping("/notes/{noteId}")
	public ResponseEntity<Object> updateNote(@RequestBody MavNotes note, @PathVariable("noteId") Long noteId) throws ServerException {
		Optional<MavNotes> exNote = mavNotesRepository.findById(noteId);
		if(exNote.isPresent()) {
			if(exNote.get().getId() != null) {
				exNote.get().setNote(note.getNote());
				exNote.get().setTitle(note.getTitle());
				exNote.get().setUpdated_at(new Date());
				mavNotesRepository.save(exNote.get());
				return ResponseEntity.status(HttpStatus.OK).build();
			}
		}
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity Not found");
	}

	//create a new user
	@PostMapping("/users")
	public MavNotesUser createUser(@RequestBody MavNotesUser user) throws ServerException {
		user.setCreated_at(new Date());
		user.setUpdated_at(new Date());
		return mavNotesUserRepository.save(user);
	}
	
	@DeleteMapping("/delete/{noteId}")
	public String DeleteNote(@PathVariable Long noteId)
			throws ServerException {
		Optional<Notes> notes=notesRepo.findById(id);
		if(!(notes.isPresent()))
		{
			return "id is not present";
		}
		notesRepo.deleteById(id);
		return "notes successfully deleted";
	}

	// login
	@PostMapping("/auth/local")
	public MavNotesUser login(@RequestBody LoginRequest request) throws ServerException {
		if(request.getIdentifier().isEmpty()) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
		List<MavNotesUser> users = mavNotesUserRepository.findUserByIdentifier(request.getIdentifier());
		if(users.size() == 0) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
		if(!users.get(0).getPassword().equals(request.getPassword())) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid Credentials");
		return users.get(0);
	}
}
