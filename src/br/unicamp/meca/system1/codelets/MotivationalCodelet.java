/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut, S. de Paula, 
 *     E. Castro and V. Figueredo
 * 
 ******************************************************************************/
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;


public abstract class MotivationalCodelet extends br.unicamp.cst.motivational.MotivationalCodelet {

    private ArrayList<String> sensoryCodeletsIds;
    private HashMap<String, Double> motivationalCodeletsIds;

    public MotivationalCodelet(String id, double level, double priority, double urgencyThreshold, ArrayList<String> sensoryCodeletsIds, HashMap<String, Double> motivationalCodeletsIds)
            throws CodeletActivationBoundsException {

        super(id, level, priority, urgencyThreshold);
        setName(id);

        setSensoryCodeletsIds(sensoryCodeletsIds);
        setMotivationalCodeletsIds(motivationalCodeletsIds);
    }


    @Override
    public void accessMemoryObjects() {

        if (getSensoryVariables().size() == 0) {
            for (Memory sensoryMO : getInputs()) {
                if (!sensoryMO.getName().contains("DRIVE"))
                    getSensoryVariables().add(sensoryMO);
            }
        }

        if (getDrivesRelevance().size() == 0) {
            for (Memory driveMO : getInputs()) {
                if (driveMO.getName().contains("DRIVE")) {
                    getDrivesRelevance().putAll((Map<? extends Memory, ? extends Double>) driveMO.getI());
                }
            }
        }

        if(getOutputDriveMO()==null)
        {
            setOutputDriveMO(this.getOutputs().get(0));
        }

    }

    public ArrayList<String> getSensoryCodeletsIds() {
        return sensoryCodeletsIds;
    }

    public void setSensoryCodeletsIds(ArrayList<String> sensoryCodeletsIds) {
        this.sensoryCodeletsIds = sensoryCodeletsIds;
    }

    public HashMap<String, Double> getMotivationalCodeletsIds() {
        return motivationalCodeletsIds;
    }

    public void setMotivationalCodeletsIds(HashMap<String, Double> motivationalCodeletsIds) {
        this.motivationalCodeletsIds = motivationalCodeletsIds;
    }
}
