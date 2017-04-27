/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeetingApp;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class PartMeetingHelper {
    Session session = null;

    public PartMeetingHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public int inviteParticipant(PartMeeting a){
        int result = 0;
        
        String sql = "insert into part_meeting(MEETING_MEETING_ID, STATUS_STATUS_TYPE, PART_PART_EMAIL) " 
                + "values (:meeting, :status, :participant)";
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(PartMeeting.class);
            
            
            
            // binding values to the placeholders in the query
            q.setParameter("meeting", a.getMeeting().getMeetingId());
            q.setParameter("participant", a.getPart().getPartEmail());
            q.setParameter("status", "MAYBE");
            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return result;
    }
    
    
    public List getParticipants(int meetingId){
        List<PartMeeting> participantList = null;
        String sql = "select * from part_meeting "
                + "where MEETING_MEETING_ID = :meetingId";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(PartMeeting.class);
            // bind values to the query placeholders
            q.setParameter("meetingId", meetingId);
            
            // execute the query and cast the returned List
            // as a List of Films
            participantList = (List<PartMeeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return participantList;
    }
    
    public int deletePart(PartMeeting a){
        int result = 0;
        String sql = "delete from part_meeting "
                + "where PART_PART_EMAIL = :email "
                + "and MEETING_MEETING_ID = :id";
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(PartMeeting.class);

            // binding values to the placeholders in the query
            q.setParameter("id", a.getMeeting().getMeetingId());
            q.setParameter("email", a.getPart().getPartEmail());

            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int updateStatus(PartMeeting a, String status){
        int result = 0;
        String sql = "update part_meeting "
                + "set STATUS_STATUS_TYPE = :status "
                + "where PART_PART_EMAIL = :email "
                + "and MEETING_MEETING_ID = :id";
                
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(PartMeeting.class);

            // binding values to the placeholders in the query
            q.setParameter("status", status);
            q.setParameter("id", a.getMeeting().getMeetingId());
            q.setParameter("email", a.getPart().getPartEmail());

            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public int searchPart(String a){
        List<Part> meetingList = null;
        String sql = "select * from part "
                + "where PART_EMAIL = :email ";
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(Part.class);

            // binding values to the placeholders in the query
            q.setParameter("email", a);

            
            // executing the query 
            meetingList = (List<Part>) q.list();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return meetingList.size();
    }
    
       
    public List getMeetingName(int startID, String partEmail){
        
        List<PartMeeting> meetingList = null;
        
        // create the query, but as a String `meetingdb`.`participant_meeting`
        // :start and :end, are placeholders for actual values
        // passed in as parameters and hard-coded
        String sql = "select * from part_meeting "
                + "where PART_PART_EMAIL = :partEmail limit :start, 5";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(PartMeeting.class);
            // bind values to the query placeholders
            //q.setParameter("start", startID);
            //q.setParameter("end", 5);
            q.setParameter("partEmail", partEmail);
            q.setParameter("start", startID);
            
            // execute the query and cast the returned List
            // as a List of Films
            meetingList = (List<PartMeeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
                
        return meetingList;
    }
    
    public int getNumberMeeting(){
        
        List<PartMeeting> meetingList = null;
        
        // create the query, but as a String
        String sql = "select * from Part_Meeting";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(PartMeeting.class);
            
            // execute the query and cast the returned List
            // as a List of Films
            meetingList = (List<PartMeeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return meetingList.size();
    }
    
        public int getNumberparticipant(int meetingId){
        
        List<PartMeeting> meetingList = null;
        
        // create the query, but as a String
        String sql = "select * from part_meeting "
                + "where MEETING_MEETING_ID = :meetingID";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(PartMeeting.class);
            
            // execute the query and cast the returned List
            // as a List of Films
            q.setParameter("meetingID", meetingId);
            meetingList = (List<PartMeeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return meetingList.size();
    }
    
        public int updateStatus(PartMeeting a){
                        int result = 0;
        
        String sql = "update part_meeting" 
                + "set STATUS_ID = :statusId"
                + "where PART_EMAIL = :partEmail"
                + "and MEETING_ID = :meetingId";
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(PartMeeting.class);
            
            int num = a.getMeeting().getMeetingId();
            String em = a.getPart().getPartEmail();
            // binding values to the placeholders in the query
            q.setParameter("meeting", a.getMeeting().getMeetingId());
            q.setParameter("participant", a.getPart().getPartEmail());
            q.setParameter("status", a.getStatus().getStatusType());
            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
        
        public int insertParticipant(String a){
                int result = 0;
        
        String sql = "insert into part(PART_EMAIL) " 
                + "values (:email)";
        
        try {
            // starting a transaction if on wisn't active
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            
            // creating an actul query that can be executed
            SQLQuery q = session.createSQLQuery(sql);
            //associating our Avtor POJO and table with the query 
            q.addEntity(Part.class);
            
            // binding values to the placeholders in the query
            q.setParameter("email", a);
            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
        
    public int searchParticipant(Part a){
        
         List<Part> participant = null;
        
        // create the query, but as a String
        String sql = "select * from Part "
                + "where PART_EMAIL = :email ";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            
            // associate the Category POJO and table with the query 
            q.addEntity(Part.class);
            

            q.setParameter("email", a.getPartEmail());
            
            // execute the query and cast the returned List
            // as a List of Films
            participant = (List<Part>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return participant.size();
    }
}
