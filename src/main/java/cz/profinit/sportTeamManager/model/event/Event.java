/*
 * Event
 *
 * 0.1
 *
 * Author: M. Halamka
 */

package cz.profinit.sportTeamManager.model.event;

import cz.profinit.sportTeamManager.model.entity.Entity;
import cz.profinit.sportTeamManager.model.invitation.Invitation;
import cz.profinit.sportTeamManager.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Class representing Event entity. Contains date, place, maxPersonAttendance, isCanceled, createdBy, listOfMessages and listOfInvitations
 */
@Data
@AllArgsConstructor
public class Event extends Entity {

    private LocalDateTime date;
    private Integer capacity;
    private Boolean isCanceled;
    private Place place;
    final private User createdBy;
    final private List<Message> MessageList;
    final private List <Invitation> InvitationList;

    /**
     * Adds new message to the Event
     * @param message Message that needs to be saved.
     */
    public void addNewMessage (Message message){
        if(!MessageList.contains(message)){
            MessageList.add(message);
        }
    }

    /**
     * Adds new invitation to the Event
     * @param invitation Invitation that needs to be saved.
     */
    public void addNewInvitation (Invitation invitation){
        if(!InvitationList.contains(invitation)){
            InvitationList.add(invitation);
        }
    }
}
