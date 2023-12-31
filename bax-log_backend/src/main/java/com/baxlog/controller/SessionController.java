package com.baxlog.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.baxlog.model.Session;
import com.baxlog.repository.SessionRepository;

@CrossOrigin(origins="*")
@RestController
@RequestMapping("/api/baxlog")
public class SessionController {
	@Autowired
	private SessionRepository sessionRepository;

	@GetMapping("/sessions")
	public List<Session> getAllSessions(){
		return sessionRepository.findAll();
	}

	@GetMapping("/sessions/createcheck/{sessionID}")
	public String checkCreateSession(@PathVariable String sessionID){
		String returnMessage = "Success";
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				returnMessage = "This Session ID is occupied";
			}
		}
		return returnMessage;
	}

	@GetMapping("/sessions/joincheck/{sessionID}")
	public String checkJoinSession(@PathVariable String sessionID){
		String returnMessage = "There is no such session!";
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				if (allSessions.get(i).getPersonCount() < 12 && allSessions.get(i).getPersonCount() >0) {
					if(allSessions.get(i).getIsLocked().equals("true") ){
						returnMessage = "Session is locked";
					}else{
						returnMessage = "Success";
					}
				}
				else{
					returnMessage= "Session is full";
				}
			}
		}
		return returnMessage;
	}

	@GetMapping("/sessions/revealcheck/{sessionID}")
	public String checkSessionRevealCard(@PathVariable String sessionID){
		String returnMessage = "Not reveal";
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				if(allSessions.get(i).getIsReveal().equals("true")) {
					returnMessage = "Reveal";
				}
			}
		}
		return returnMessage;
	}
	
	@GetMapping("/sessions/getstory/{sessionID}")
	public String getStory(@PathVariable String sessionID){
		String returnMessage = "Not selected yet";
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				if(!(allSessions.get(i).getCurrentStory().equals(""))) {
					returnMessage = allSessions.get(i).getCurrentStory();
				}
			}
		}
		return returnMessage;
	}

	@PutMapping("/sessions/join/{sessionID}")
	public ResponseEntity<Session> joinSession(@PathVariable String sessionID){
		Session session = new Session();
		Session joinedSession = new Session();
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			int newCount= 0;
			int count=0;
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				if (allSessions.get(i).getPersonCount() < 12 && allSessions.get(i).getPersonCount() >0) {
					count = allSessions.get(i).getPersonCount();
					newCount= count-1;
					allSessions.get(i).setPersonCount(newCount);
					session.setPersonCount(newCount);
					session.setSessionSQLid(allSessions.get(i).getSessionSQLid());
					session.setSessionID(allSessions.get(i).getSessionID());
					session.setSessionAdminID(allSessions.get(i).getSessionAdminID());
					session.setSessionAdmin(allSessions.get(i).getSessionAdmin());
					session.setIsLocked(allSessions.get(i).getIsLocked());
					joinedSession = sessionRepository.save(session);
				}

			}
		}
		return ResponseEntity.ok(joinedSession);
	}

	@PutMapping("/sessions/lock/{sessionID}")
	public ResponseEntity<Session> lockSession(@PathVariable String sessionID){
		Session session = new Session();
		Session lockedSession = new Session();
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				allSessions.get(i).setIsLocked("true");
				session.setIsLocked("true");
				session.setPersonCount(allSessions.get(i).getPersonCount());
				session.setSessionSQLid(allSessions.get(i).getSessionSQLid());
				session.setSessionID(allSessions.get(i).getSessionID());
				session.setSessionAdminID(allSessions.get(i).getSessionAdminID());
				session.setSessionAdmin(allSessions.get(i).getSessionAdmin());
				lockedSession = sessionRepository.save(session);
			}
		}
		return ResponseEntity.ok(lockedSession);
	}

	@PutMapping("/sessions/reveal/{sessionID}")
	public ResponseEntity<Session> revealSessionCards(@PathVariable String sessionID){
		Session session = new Session();
		Session revealedSession = new Session();
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				allSessions.get(i).setIsReveal("true");
				session.setIsReveal("true");
				session.setPersonCount(allSessions.get(i).getPersonCount());
				session.setSessionSQLid(allSessions.get(i).getSessionSQLid());
				session.setSessionID(allSessions.get(i).getSessionID());
				session.setSessionAdminID(allSessions.get(i).getSessionAdminID());
				session.setSessionAdmin(allSessions.get(i).getSessionAdmin());
				session.setIsLocked(allSessions.get(i).getIsLocked());
				revealedSession = sessionRepository.save(session);
			}
		}
		return ResponseEntity.ok(revealedSession);
	}
	
	@PutMapping("/sessions/putstory/{sessionID}")
	public ResponseEntity<Session> putStory(@PathVariable String sessionID, @RequestBody Session story){
		System.out.println(story);
		Session session = new Session();
		Session storiedSession = new Session();
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				allSessions.get(i).setCurrentStory(story.getCurrentStory());
				session.setCurrentStory(story.getCurrentStory());
				session.setPersonCount(allSessions.get(i).getPersonCount());
				session.setSessionSQLid(allSessions.get(i).getSessionSQLid());
				session.setSessionID(allSessions.get(i).getSessionID());
				session.setSessionAdminID(allSessions.get(i).getSessionAdminID());
				session.setSessionAdmin(allSessions.get(i).getSessionAdmin());
				session.setIsLocked(allSessions.get(i).getIsLocked());
				session.setIsReveal(allSessions.get(i).getIsReveal());
				storiedSession = sessionRepository.save(session);
			}
		}
		return ResponseEntity.ok(storiedSession);
	}
	
	@PutMapping("/sessions/reset/{sessionID}")
	public ResponseEntity<Session> resetSession(@PathVariable String sessionID){
		Session session = new Session();
		Session updatedSession = new Session();
		List<Session> allSessions = getAllSessions();
		for(int i=0; i<allSessions.size(); i++) {
			if(allSessions.get(i).getSessionID().equals(sessionID)) {
				session = allSessions.get(i);
				session.setCurrentStory("No story selected");
				session.setIsLocked("false");
				session.setIsReveal("false");
				updatedSession = sessionRepository.save(session);
			}
		}
		return ResponseEntity.ok(updatedSession);
	}

	@PostMapping("/sessions/save")
	public Session createSession(@RequestBody Session session) {
		session.setPersonCount(11);
		return sessionRepository.save(session);
	}
}