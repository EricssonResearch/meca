/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP and Ericsson Research                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut, S. de Paula,      * 
 *     E. Castro, V. Figueredo and K. Raizer                                   *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.memory.WorkingMemory;

/**
 * This class represents MECA's Goal Codelet. Goals are created in MECA by the
 * Goal Codelet. The Goal Codelet uses the Current Perception in order to
 * generate goals. Basically, it explores the space of possible future states,
 * using an evolutionary technique, and selecting desirable future states as
 * Goals.
 * 
 * @author A. L. O. Paraense
 * @see br.unicamp.cst.motivational.GoalCodelet
 *
 */
public abstract class GoalCodelet extends br.unicamp.cst.motivational.GoalCodelet {

       private Memory actionSequencePlanRequestMemoryContainer;
       private WorkingMemory wm;

	/**
	 * Creates a MECA's Goal Codelet.
	 * 
	 * @param id
	 *            the id of the Goal Codelet. Must be unique per Goal Codelet.
	 */
	public GoalCodelet(String id) {
		super(id);
	}
        
        /**
     * @return the actionSequencePlanRequestMemoryContainer
     */
    public Memory getActionSequencePlanRequestMemoryContainer() {
        return actionSequencePlanRequestMemoryContainer;
    }

    /**
     * @param actionSequencePlanRequestMemoryContainer the actionSequencePlanRequestMemoryContainer to set
     */
    public void setActionSequencePlanRequestMemoryContainer(Memory actionSequencePlanRequestMemoryContainer) {
        this.actionSequencePlanRequestMemoryContainer = actionSequencePlanRequestMemoryContainer;
    }

    /**
     * @return the wm
     */
    public WorkingMemory getWm() {
        return wm;
    }

    /**
     * @param wm the wm to set
     */
    public void setWm(WorkingMemory wm) {
        this.wm = wm;
    }
        
        
}
