/*
 * InvitationRepository
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.repositories.invitation;

import cz.profinit.sportTeamManager.exceptions.EntityNotFoundException;
import cz.profinit.sportTeamManager.model.event.Event;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;

public interface InvitationRepository {

    void insertInvitation(Invitation invitation);
    void updateInvitation(Invitation invitation);
    Invitation findInvitationById(Long id) throws EntityNotFoundException;
    Invitation findInvitationByEventIdAndUserEmail(Long eventId, String userEmail) throws EntityNotFoundException;
    boolean deleteInvitation(User user, Event event) throws EntityNotFoundException;
    boolean isUserInvitedToEvent(String userEmail, Long eventId) throws EntityNotFoundException;

}
