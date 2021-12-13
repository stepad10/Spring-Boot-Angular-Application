/*
 * EventService
 *
 * 0.1
 *
 * Author: M. Halamka
 */
package cz.profinit.sportTeamManager.service;

import cz.profinit.sportTeamManager.dto.EventDto;
import cz.profinit.sportTeamManager.dto.InvitationDto;
import cz.profinit.sportTeamManager.dto.MessageDto;
import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;

import java.util.List;

/**
 * Service interface for Event business logic.
 */
public interface EventService {

    Event createNewEvent(EventDto eventDto);
    Event updateEvent (EventDto eventDto, Long eventId) throws EntityNotFoundException;
    Event findEventById(Long id) throws EntityNotFoundException;
    Event addNewMessage (String email, String messageStr, Long eventId) throws EntityNotFoundException;
    List<MessageDto> getAllMessages (Long eventId) throws EntityNotFoundException;
    Event changeEventStatus (Long eventId) throws EntityNotFoundException;
    Event addNewInvitation(Long eventId, Invitation invitation) throws EntityNotFoundException;
    List<InvitationDto> getAllInvitations (Long eventId) throws EntityNotFoundException;
}
