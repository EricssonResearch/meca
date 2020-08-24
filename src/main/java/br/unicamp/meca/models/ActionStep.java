/*******************************************************************************
 * Copyright (c) 2019  DCA-FEEC-UNICAMP and Ericsson Research                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut,		       * 
 *     and K. Raizer.	                            			       *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.models;

import br.unicamp.cst.core.entities.Memory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author rgudwin
 */
public abstract class ActionStep {
    String actionId;
    Map<String,Object> parameters;
    
    public ActionStep() {
        actionId = "";
        parameters = new HashMap<String,Object>();
    }
    
    public ActionStep(String acId) {
        this();
        actionId = acId;
    }
    
    public ActionStep(String acId, Map<String,Object> par) {
        this();
        actionId = acId;
        parameters = par;
    }
    
    public String getActionId() {
        return actionId;
    }
    
    public void setActionId(String id) {
        actionId = id;
    }
    
    public Object getParameter(String paramId) {
        if (parameters == null) System.out.println("Parameters is null");
        return(parameters.get(paramId));
    }
    
    public void setParameter(String paramId, Object param) {
        if (parameters.get(paramId) == null) 
            parameters.put(paramId, param);
        else
            parameters.replace(paramId, param);
    }
    
    public Map<String,Object> getParameters() {
        return(parameters);
    }
    
    public void setParameters(Map<String,Object> par) {
        parameters = par;
    }
    
    public int getNumberOfParameters() {
        if(parameters == null) return(-1);
        return(parameters.size());
    }
    
    public void unsetParameter(String param) {
        parameters.remove(param);
    }
    
    public String toString() {
        return(actionId);
    }
    
    public abstract boolean stopCondition(List<Memory> perception);
    
}
