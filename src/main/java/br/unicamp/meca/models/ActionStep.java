/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.meca.models;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author rgudwin
 */
public class ActionStep {
    String actionId;
    Map<String,Object> parameters;
    
    public ActionStep() {
        actionId = "";
        parameters = new HashMap<String,Object>();
    }
    
    public String getActionId() {
        return actionId;
    }
    
    public void setActionId(String id) {
        actionId = id;
    }
    
    public Object getParameter(String paramId) {
        return(parameters.get(paramId));
    }
    
    public void set(String paramId, Object param) {
        if (parameters.get(paramId) == null) 
            parameters.put(paramId, param);
        else
            parameters.replace(paramId, param);
    }
    
}
