/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeetingApp;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;

/**
 *
 * @author user
 */
@Named(value = "hostController")
@SessionScoped
public class HostController implements Serializable {

    
    // these fields map directlu to components in the host.xhtml
    String email;
    String response;
    String error;
    
    // this is our class that uses Hibernare tp quert the host table
    HostHelper helper;
    
    // this is our POJO
    Host host;
    
    /**
     * Creates a new instance of HostController
     */
    public HostController() {
        helper = new HostHelper();
        error = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
    }

    public String getResponse() {
        if (email != null) {
            
            // initializing an actor
            host = new Host(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.insertHost(host) == 1 ){
                // insert was successful
                email = null;
                response = "Host Added.";
                return response;
            } else {
                // inser failed
                email = null;
                response = "Actor Not Added.";
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
    
    public String login(){

         if (email != null) {
            
            // initializing an actor
            host = new Host(email);
            
            //calling our helper that inserts a row into the actor table
            if (helper.searchHost(host) == 1 ){
                // insert was successful
                email = null;
                error = "";
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
        
}
