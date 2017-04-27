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
 * @author user
 */
@Named(value = "participantMeetingController")
@SessionScoped
public class PartMeetingController implements Serializable {

    
    //DataModel partMeetingList;
    DataModel meetingList;
    PartMeetingHelper helper;

    //POJO
    PartMeeting participantMeeting;
    Meeting meeting;
    Status status;
    Part participant;
    Host host;

    // Data passed from another controller
    String statusType;
    String partEmail;
    
    String error;
    // 
    String statusRespone;
    String response;
    private int recordCount;
    private int pageSize = 5;
    private PartMeeting selected;

    int startId;
    
    
    /**
     * Creates a new instance of ParticipantMeetingController
     */
    public PartMeetingController() {
        meeting = new Meeting();
        helper = new PartMeetingHelper();
        startId = 0;
        recordCount = helper.getNumberMeeting() + 1;
        statusRespone = "";
    }
    
    private void recreateModel(){
        meetingList = null;
        recordCount = helper.getNumberMeeting() + 1;
    }
    
    public String next(){
        startId = startId + (pageSize + 1);
        recreateModel();
        return "par_meetings";
    }
    public String previous(){
        startId = startId - (pageSize + 1);
        recreateModel();
        return "par_meetings";
    }

    public String getResponse() {
        if (meeting.getMeetingId() != 0 && partEmail != null) {
            
            // create objects
            // if number of participant is greater than 5 ... Reject the insertion.
            //if (helper.getNumberparticipant(meeting.getMeetingId())   ){
                
            //}
            participant = new Part(partEmail);
            status = new Status("");
            
            // initializing an actor
            participantMeeting = new PartMeeting(meeting, participant, status);
            
            //calling our helper that inserts a row into the actor table
            if (helper.inviteParticipant(participantMeeting) == 1 ){
                // insert was successful
                meeting = null;
                participant = null;
                partEmail = null;
                status = null;
                response = "participant Added to meeting.";
                return response;
            } else {
                // inser failed
                meeting = null;
                participant = null;
                partEmail = null;
                status = null;
                response = "Participant Not Added to Meeting.";
            }
        } else {
            // don't dis[lay a message when the user hasn't input 
            // a first and last name
            response = " ";
        }
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
    
    public String prepareView(){
        // get all of the data associated with the selected movie
        selected = (PartMeeting) getMeetingList().getRowData();

        // return the name of the page that will load when the hyperlink
        // is selected
        return "par_meeting_details";
    }

    public String statusUpdate() {
        if(helper.updateStatus(selected, statusType) == 1){
             meetingList = new ListDataModel(helper.getMeetingName(startId, participant.getPartEmail()));
        }
        return "par_meetings";
    }

    public String getStatusType() {
        return statusType;
    }

    public void setStatusType(String statusType) {
        this.statusType = statusType;
    }
    
    public DataModel getMeetingList() {
        if (meetingList == null && participant.getPartEmail() != null){
            meetingList = new ListDataModel(helper.getMeetingName(startId, participant.getPartEmail()));
        }
        
        return meetingList;
    }

    public void setMeetingList(DataModel meetingList) {
        this.meetingList = meetingList;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    
    public void refreshMeeting(){
        recreateModel();
        meetingList = new ListDataModel(helper.getMeetingName(startId, participant.getPartEmail()));
    }

    
    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public boolean isHasNextPage(){
        if (startId + pageSize < recordCount){
            return true;
        }
        return false;
    }
    
    public boolean isHasPreviousPage(){
        if (startId - pageSize > 0){
            return true;
        }
        return false;
    }

    public Meeting getMeeting() {
        return meeting;
    }

    public void setMeeting(Meeting meeting) {
        this.meeting = meeting;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }
    
    public String login(String email){
         if (email != null) {
            
            // initializing an actor
            participant = new Part(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.searchParticipant(participant) == 1 ){
                // insert was successful
                
                recordCount = 0;
                startId = 0;
                refreshMeeting();
                
                error = "";
                return "par_meetings";
                
            } else {
                // inser failed
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
    
    public PartMeeting getSelected() { 
         if (selected == null){
            selected = new PartMeeting();
        }
        return selected;
    }

    public void setSelected(PartMeeting selected) {
        this.selected = selected;
    }

    public String getPartEmail() {
        return partEmail;
    }

    public void setPartEmail(String partEmail) {
        this.partEmail = partEmail;
    }    
    
}
