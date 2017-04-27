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
public class StatusHelper {
    Session session = null;
    public StatusHelper() {
        try {
            this.session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public List statusList (){
        List<Status> sList = null;
        String sql = "select * from status ";
        
        try {
            // if the transaction isn't active, begin it
            if (!this.session.getTransaction().isActive()) {
                session.beginTransaction();
            } 
            // create the actual query that will get executed
            SQLQuery q = session.createSQLQuery(sql);
            
            // associate the Category POJO and table with the query 
            q.addEntity(Status.class);
            // bind values to the query placeholders
            
            // execute the query and cast the returned List
            // as a List of Films
            sList = (List<Status>) q.list();
        } catch (Exception e){
            e.printStackTrace();
        }
        
        return sList;
    }
    
}
