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
    
    public ActionStep(String acId) {
        super();
        actionId = acId;
    }
    
    public ActionStep(String acId, Map<String,Object> par) {
        super();
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
        return(parameters.get(paramId));
    }
    
    public void setParameter(String paramId, Object param) {
        if (parameters.get(paramId) == null) 
            parameters.put(paramId, param);
        else
            parameters.replace(paramId, param);
    }
    
}
