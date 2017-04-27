
package MeetingApp;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

/**
 *
 * @author user
 */
public class MeetingHelper {
    
    Session session = null;
    
    public MeetingHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public int deleteMeeting(Meeting a){
        int result = 0;
        String sql = "delete from meeting "
                + "where MEETING_ID = :meetingID";
        
        try {
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            SQLQuery q = session.createSQLQuery(sql);
            // associate the Meeting POJO and table with the query 
            q.addEntity(Meeting.class);
            // bind values to the query placeholders
            q.setParameter("meetingID", a.getMeetingId());

            // execute the query
            result = q.executeUpdate();
            // commit the changes to the database
            // this is what allows the changes to be
            // truely viewed in the database
            // but it also makes the transaction inactive
            // which means it will have to be started again
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public int insertMeeting(Meeting a) {
        int result = 0;
        String sql = "insert into meeting(MEETING_NAME, MEETING_DESC, MEETING_DATE, MEETING_TIME, MEETING_LOCATION, HOST_EMAIL) "
                + "values (:name, :description, :date, :time, :locayion, :host)";
        try {
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            }
            SQLQuery q = session.createSQLQuery(sql);
            // associate the Meeting POJO and table with the query 
            q.addEntity(Meeting.class);
            // bind values to the query placeholders
            q.setParameter("name", a.getMeetingName());
            q.setParameter("description", a.getMeetingDesc());
            q.setParameter("date", a.getMeetingDate());
            q.setParameter("time", a.getMeetingTime());
            q.setParameter("locayion", a.getMeetingLocation());
            q.setParameter("host", a.getHost().getHostEmail());
            // execute the query
            result = q.executeUpdate();
            // commit the changes to the database
            // this is what allows the changes to be
            // truely viewed in the database
            // but it also makes the transaction inactive
            // which means it will have to be started again
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    
    public List getMeetingName(int startID, String hostEmail){
        
        List<Meeting> meetingList = null;
        
        // create the query, but as a String
        // :start and :end, are placeholders for actual values
        // passed in as parameters and hard-coded
        String sql = "select * from meeting "
                + "where HOST_EMAIL = :hostEmail "
                + "limit :start, :end";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Meeting.class);
            // bind values to the query placeholders
            q.setParameter("start", startID);
            q.setParameter("end", 5);
            q.setParameter("hostEmail", hostEmail);
            
            // execute the query and cast the returned List
            // as a List of Films
            meetingList = (List<Meeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
                
        return meetingList;
    }
    
    public int getNumberMeeting(){
        
        List<Meeting> meetingList = null;
        
        // create the query, but as a String
        String sql = "select * from meeting";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Meeting.class);
            
            // execute the query and cast the returned List
            // as a List of Films
            meetingList = (List<Meeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return meetingList.size();
    }
    
    
    // Host ...
    public int searchHost(Host a){
        
        List<Host> host = null;
        
        // create the query, but as a String
        String sql = "select * from Host where HOST_EMAIL = :email";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            
            // associate the Category POJO and table with the query 
            q.addEntity(Host.class);
            
            q.setParameter("email", a.getHostEmail());
            
            // execute the query and cast the returned List
            // as a List of Films
            host = (List<Host>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return host.size();
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
    
    public int getNumberparticipant(Meeting a){
        
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
            q.setParameter("meetingID", a.getMeetingId());
            meetingList = (List<PartMeeting>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return meetingList.size();
    }
    
    public int deleteAllParticipant(Meeting a){
        int result = 0;
        
        String sql = "delete from part_meeting "
                + "where MEETING_MEETING_ID = :id";
        
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
            q.setParameter("id", a.getMeetingId());

            
            // executing the query 
            result = q.executeUpdate();
            
            // commiting the query to the database
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

        return result;
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
    
}
