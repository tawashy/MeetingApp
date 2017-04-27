/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeetingApp;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author mimi
 */
@Named(value = "participantController")
@SessionScoped
public class PartController implements Serializable {

    // these fields map directly to components in the participant.xhtml
    int id;
    String name;
    String email;
    String response;
    String error;
    
    DataModel participants;
    
    
    // this is our class that uses Hibernare to query the host table
    PartHelper helper;
    
    // this is our Host POJO
    Part participant;
    
    /**
     * Creates a new instance of ParticipantController
     */
    public PartController() {
        helper = new PartHelper();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getResponse() {
        if (email != null) {
            
            
            // initializing an actor
            participant = new Part(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.insertParticipant(participant) == 1 ){
                // insert was successful
                email = null;
                response = "Participant Added.";
                return response;
            } else {
                // inser failed
                email = null;
                response = "Participant Not Added.";
            }
        } else {
            // don't display a message when the user hasn't input 
            // a name and email
            response = " ";
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public PartHelper getHelper() {
        return helper;
    }

    public void setHelper(PartHelper helper) {
        this.helper = helper;
    }

    public Part getParticipant() {
        return participant;
    }

    public void setParticipant(Part participant) {
        if (participants == null){
            participants = new ListDataModel(helper.getParticipants());
        }
        this.participant = participant;
    }

    public DataModel getParticipants() {
        return participants;
    }

    public void setParticipants(DataModel participants) {
        this.participants = participants;
    }
    
   public String login(){

         if (email != null) {
            
            // initializing an actor
            participant = new Part(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.searchParticipant(participant) == 1 ){
                // insert was successful
                email = null;
                error = "";
                return "par_meetings";
                
            } else {
                // inser failed
                name = null;
                error = "Name or email not found";
                return "par_login";
            }
        } else {
            // don't dis[lay a message when the user hasn't input 
            // a first and last name
            return "par_login";
        }
        
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
   
   
}