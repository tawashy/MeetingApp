/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MeetingApp;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;

/**
 *
 * @author user
 */
@Named(value = "statusController")
@Dependent
public class StatusController {

    /**
     * Creates a new instance of StatusController
     */
    Status Type;
    DataModel statusList;
    StatusHelper helper;
    public StatusController() {
        helper = new StatusHelper();
    }

    public Status getType() {
        return Type;
    }

    public void setType(Status Type) {
        this.Type = Type;
    }

    public DataModel getStatusList() {
        if (statusList == null){
            statusList = new ListDataModel(helper.statusList());
        }
        return statusList;
    }

    public void setStatusList(DataModel statusList) {
        this.statusList = statusList;
    }
    
    
    
}
