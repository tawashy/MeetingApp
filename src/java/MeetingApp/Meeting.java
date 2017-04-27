package MeetingApp;
// Generated Apr 15, 2017 9:22:30 PM by Hibernate Tools 4.3.1


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Meeting generated by hbm2java
 */
public class Meeting  implements java.io.Serializable {


     private Integer meetingId;
     private Host host;
     private String meetingName;
     private String meetingDesc;
     private Date meetingDate;
     private Date meetingTime;
     private String meetingLocation;
     private Set<PartMeeting> partMeetings = new HashSet<PartMeeting>(0);

    public Meeting() {
    }

	
    public Meeting(Host host, String meetingName, String meetingDesc, Date meetingDate, Date meetingTime, String meetingLocation) {
        this.host = host;
        this.meetingName = meetingName;
        this.meetingDesc = meetingDesc;
        this.meetingDate = meetingDate;
        this.meetingTime = meetingTime;
        this.meetingLocation = meetingLocation;
    }
    public Meeting(Host host, String meetingName, String meetingDesc, Date meetingDate, Date meetingTime, String meetingLocation, Set<PartMeeting> partMeetings) {
       this.host = host;
       this.meetingName = meetingName;
       this.meetingDesc = meetingDesc;
       this.meetingDate = meetingDate;
       this.meetingTime = meetingTime;
       this.meetingLocation = meetingLocation;
       this.partMeetings = partMeetings;
    }
   
    public Integer getMeetingId() {
        return this.meetingId;
    }
    
    public void setMeetingId(Integer meetingId) {
        this.meetingId = meetingId;
    }
    public Host getHost() {
        return this.host;
    }
    
    public void setHost(Host host) {
        this.host = host;
    }
    public String getMeetingName() {
        return this.meetingName;
    }
    
    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }
    public String getMeetingDesc() {
        return this.meetingDesc;
    }
    
    public void setMeetingDesc(String meetingDesc) {
        this.meetingDesc = meetingDesc;
    }
    public Date getMeetingDate() {
        return this.meetingDate;
    }
    
    public void setMeetingDate(Date meetingDate) {
        this.meetingDate = meetingDate;
    }
    public Date getMeetingTime() {
        return this.meetingTime;
    }
    
    public void setMeetingTime(Date meetingTime) {
        this.meetingTime = meetingTime;
    }
    public String getMeetingLocation() {
        return this.meetingLocation;
    }
    
    public void setMeetingLocation(String meetingLocation) {
        this.meetingLocation = meetingLocation;
    }
    public Set<PartMeeting> getPartMeetings() {
        return this.partMeetings;
    }
    
    public void setPartMeetings(Set<PartMeeting> partMeetings) {
        this.partMeetings = partMeetings;
    }




}


