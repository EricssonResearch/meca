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

import br.unicamp.meca.system1.codelets.ActivityCodelet;

/**
 * This class represents the MECA Action Sequence Plan. This Action Sequence Plan
 * represents a plan provided by a Behavior Codelet to be executed as a sequence of
 * Actions that will exist in the repertoire of the MECA Agent as ActionFromPlanningCodelet(s).
 * Here, they will be listed in sequence by their ids, and there will be a memory of which of
 * them is the current action that should be undertaken in the plan.
 * 
 * @author A. L. O. Paraense
 * @see ActivityCodelet
 *
 */
public class ActionSequencePlan {
	
	private ActionStep[] actionIdSequence;
	
	private int currentActionIdIndex = 0;

	/**
	 * 
	 * Creates a MECA Action Sequence Plan.
	 * 
	 * @param actionIdSequence
	 * 			the list of ids of ActionFromPlanningCodelet(s) to be executed
	 * 			in sequence following this plan.
	 */
	public ActionSequencePlan(ActionStep[] actionIdSequence) {
		this.actionIdSequence = actionIdSequence;
	}
	
	/**
	 * Returns the id of the current ActionFromPlanningCodelet to be undertaken in the plan.
	 * 
	 * @return currentActionId
	 */
	public ActionStep getCurrentActionStep() {
		return actionIdSequence[currentActionIdIndex];
	}

	/**
	 * @return the actionIdSequence
	 */
	public ActionStep[] getActionStepSequence() {
		return actionIdSequence;
	}

	/**
	 * @param actionIdSequence the actionIdSequence to set
	 */
	public void setActionStepSequence(ActionStep[] actionIdSequence) {
		this.actionIdSequence = actionIdSequence;
	}

	/**
	 * @return the currentActionIdIndex
	 */
	public int getCurrentActionIdIndex() {
		return currentActionIdIndex;
	}

	/**
	 * @param currentActionIdIndex the currentActionIdIndex to set
	 */
	public void setCurrentActionIdIndex(int currentActionIdIndex) {
		this.currentActionIdIndex = currentActionIdIndex;
	}
        
        public void gotoNextAction() {
            if (currentActionIdIndex < actionIdSequence.length-1)
                currentActionIdIndex++;
        }
        
        public String toString() {
            String output = "{ ";
            int i=0;
            for (ActionStep a : actionIdSequence) {
                output += a.toString();
                i++;
                if (i < actionIdSequence.length)
                    output += ", ";
            }
            output += "}";
            return(output);
        }
}
