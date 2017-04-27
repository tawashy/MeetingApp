/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeetingApp;

//import com.sun.org.apache.bcel.internal.generic.RETURN;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author user
 */
@Named(value = "meetingController")
@SessionScoped
public class MeetingController implements Serializable {

    int startId;
    String name;
    String description;
    Date date;
    Date time;
    String location;
    String response;
    
    int selectedId;
    String error;
    
    Part participant;
    PartMeeting participantMeeting;
    Status status;
    
    Host host;
    DataModel meetingName;
    
    private int recordCount;
    private int pageSize = 5;
    private Meeting selected;
    
    ListDataModel partMeetingList;
    String partEmail;
    String partMsg;
    
    public MeetingController() {
        helper = new MeetingHelper();
        startId = 0;
        recordCount = helper.getNumberMeeting();
        
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartEmail() {
        return partEmail;
    }

    public void setPartEmail(String partEmail) {
        this.partEmail = partEmail;
    }

    public String getPartMsg() {
        return partMsg;
    }

    public void setPartMsg(String partMsg) {
        this.partMsg = partMsg;
    }
    
    

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    
    public String login(String email){

         if (email != null) {
            
            // initializing an actor
            host = new Host(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.searchHost(host) == 1 ){
                // insert was successful
                email = null;
                error = "";
                refresh();
                return "host_meetings";
                
            } else {
                // inser failed
                email = null;
                error = "Email not found";
                 return "host_login";
            }
        } else {
            // don't display a message when the user hasn't input 
            // a first and last name
            error = " ";
            return "host_login";
        }
        
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    

    public String getResponse() {
        // if the firstname and lastname components in the actor.xhtml
        // have data in them, then insert it into the database
        if (name != null && description != null && date != null && time != null && location != null) {
            // initialize an actor so that it contains the data
            // input in the actor.xhtml
            meeting = new Meeting(host, name, description, date, time, location);

            // call the helper method that inserts the data into
            // the database
            if (helper.insertMeeting(meeting) == 1) {
                // if the insert works
                name = null;
                description = null;
                date = null;
                time = null;
                location = null;
                refresh();
                response = "Meeting Added.";
                return response;
            } else {
                // if the insert doesn't work
                name = null;
                description = null;
                date = null;
                time = null;
                location = null;

                response = "Meeting Not Added.";
                return response;
            }
        } else {
            // if nothing was input into the form
            //don't display a message 
            response = " ";
            return response;
        }
    }
    
    public String redirect(){
        if (response.equalsIgnoreCase("Meeting Added.")){
            response = " ";
            return "host_meetings";
        } else {
            response = " ";
            return "host_add_meeting";
        }
    }

    public void setResponse(String response) {
        this.response = response;
    }

    // this is our class that uses Hibernate to query the database
    MeetingHelper helper;

    // this is our Actor POJO
    Meeting meeting;
    
    
    
    public String next(){
        startId = startId + (pageSize + 1);
        recreateModel();
        return "host_meetings";
    }
    
    public DataModel getMeetingName() {
        if (meetingName == null && host != null){
            meetingName = new ListDataModel(helper.getMeetingName(startId, host.getHostEmail()));
        }
        return meetingName;
    }
    
    

    public void setMeetingName(DataModel meetingName) {
        this.meetingName = meetingName;
    }
    
    private void recreateModel(){
        meetingName = null;
        recordCount = helper.getNumberMeeting();
    }
    
    public String previous(){
        startId = startId - (pageSize + 1);
        recreateModel();
        return "host_meetings";
    }
    
    public String delete(){
        if (helper.getNumberparticipant((Meeting) getMeetingName().getRowData()) > 0){
            if (helper.deleteAllParticipant((Meeting) getMeetingName().getRowData()) > 0){
                if (helper.deleteMeeting((Meeting) getMeetingName().getRowData()) == 1){
                    refresh();
                }
            }
        } else if (helper.deleteMeeting((Meeting) getMeetingName().getRowData()) == 1){
            refresh();
        }
        return "host_meetings";
    }
    

    public void refresh(){
            recreateModel();
            meetingName = new ListDataModel(helper.getMeetingName(startId, host.getHostEmail()));
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
    
    public String prepareView(){
        // get all of the data associated with the selected movie
        selected = (Meeting) getMeetingName().getRowData();
        
        //get participant list 
        partMeetingList = new ListDataModel(helper.getParticipants(selected.getMeetingId()));
        // return the name of the page that will load when the hyperlink
        // is selected
        partEmail = null;
        partMsg = "";
        return "host_meeting_details";
    }
    
    public Meeting getSelected() {
        if (selected == null){
            selected = new Meeting();
        }
        return selected;
    }

    public void setSelected(Meeting selected) {
        this.selected = selected;
    }

    public String deleteParticipant(){
        if (helper.deletePart((PartMeeting) getPartMeetingList().getRowData()) == 1){
            partMeetingList = new ListDataModel(helper.getParticipants(selected.getMeetingId()));
            
        }
        partEmail = null;
        partMsg = "";
        return "host_meeting_details";
    }
    
    
    
    public String invite(){
        /* - check if the number of part is less than 5
                - check if the participant exists 
                    - add to part_meeting
                - add new participant
                    - add to part_meeting
           - Error Msg : limited num of participant. 
        */
        
        if (partEmail != null){
            participant = new Part(partEmail);
            participantMeeting = new PartMeeting(selected, participant, status);
            //- check if the number of part is less than 5
            if (helper.getNumberparticipant(selected) < 5){
                // - check if the participant exists
                if (helper.searchPart(partEmail) == 1){
                    // add part to exists meeting
                    if(helper.inviteParticipant(participantMeeting)== 1){
                        partMeetingList = new ListDataModel(helper.getParticipants(selected.getMeetingId()));
                        partEmail = null;
                        partMsg = "Existd Participant Added to meeting.";
                    }else{
                        partEmail = null;
                        partMsg = "Existd Participant NOT Added to meeting.";
                    }
                    
                } else {
                    // add new participant
                        if (helper.insertParticipant(partEmail) == 1){
                            // add part to exists meeting
                            if (helper.inviteParticipant(participantMeeting) == 1){
                                partMeetingList = new ListDataModel(helper.getParticipants(selected.getMeetingId()));
                                partEmail = null;
                                partMsg = "New Participant Added to meeting.";
                            }else{
                                // msg participant not added to meeting
                                partEmail = null;
                                partMsg = "New Participant NOT Added to meeting.";
                            }
                        }else{
                            partEmail = null;
                            partMsg = "New Participant NOT Added.";
                        }
                }
                
            }else{
                // print a msg to show that the limit of 5 participant have exceeded..
                partEmail = null;
                partMsg = "you can't add more than 5 participants. \n"
                        + "please! delete on or more participants\n"
                        + "to add new ones.";
            }
        }
        
        partEmail = null;

        return "host_meeting_details";
    }
    
    
    public int getSelectedId() {
        return selectedId;
    }

    public void setSelectedId(int selectedId) {
        this.selectedId = selectedId;
    }

    public ListDataModel getPartMeetingList() {
        if (partMeetingList == null){
            partMeetingList = new ListDataModel(helper.getParticipants(selected.getMeetingId()));
        }
        return partMeetingList;
    }

    public void setPartMeetingList(ListDataModel partMeetingList) {
        this.partMeetingList = partMeetingList;
    }
    
    
}
