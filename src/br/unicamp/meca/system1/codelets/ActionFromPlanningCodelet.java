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
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.models.ActionSequencePlan;

/**
 * This class represents the MECA Action From Planning Codelet. This Action From Planning
 * Codelet allows inputs from one or more of the PerceptualCodelets and the ActionSequencePlan
 * with greatest activation. It outputs
 * necessarily to a MotorCodelet. As the name suggests, the idea behind this
 * action from planning codelet is to provide an action following a specific plan from the behavior generator in System 1.
 * <p>
 * Usually, Action From Planning Codelets are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class ActionFromPlanningCodelet extends Codelet {

	protected String id;

	protected ArrayList<String> perceptualCodeletsIds;
	protected ArrayList<Memory> perceptualMemories;

	protected String soarCodeletId;
	protected Memory broadcastMemory;
	
	protected Memory actionSequencePlanMemoryContainer;
	
	protected String motorCodeletId;
	protected Memory motorMemory;

	/**
	 * Creates a MECA Action From Planning Codelet.
	 * 
	 * @param id
	 *            the id of the Action From Planning Codelet. Must be unique per
	 *            Reactive Behavioral Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Action From Planning Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Action From Planning Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Action From Planning Codelet.
	 */
	public ActionFromPlanningCodelet(String id, ArrayList<String> perceptualCodeletsIds, String motorCodeletId,
			String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.soarCodeletId = soarCodeletId;
	}
	
	@Override
	public void accessMemoryObjects() {
		
		int index=0;
		
		if(perceptualMemories == null || perceptualMemories.size() == 0) {
			
			perceptualMemories = new ArrayList<>();
			
			if(perceptualCodeletsIds != null) {
				
				for(String perceptualCodeletId : perceptualCodeletsIds) {
					Memory perceptualMemory = this.getInput(perceptualCodeletId, index);
					perceptualMemories.add(perceptualMemory);
				}
			}
		}
		
		if(broadcastMemory == null) {
			broadcastMemory = this.getBroadcast(soarCodeletId, index);
		}
		
		if(actionSequencePlanMemoryContainer == null)
			actionSequencePlanMemoryContainer = this.getInput(MecaMind.ACTION_SEQUENCE_PLAN_ID, index);

		if(motorMemory==null && motorCodeletId!=null)
			motorMemory = this.getOutput(motorCodeletId, index);

	}
	
	@Override
	public void calculateActivation() {
		
		try {
			if(actionSequencePlanMemoryContainer != null && actionSequencePlanMemoryContainer.getI() != null && actionSequencePlanMemoryContainer.getI() instanceof ActionSequencePlan) {

				ActionSequencePlan actionSequencePlan = (ActionSequencePlan) actionSequencePlanMemoryContainer.getI();
				String currentActionId = actionSequencePlan.getCurrentActionId();

				if(currentActionId != null && currentActionId.equalsIgnoreCase(id)) {
					setActivation(actionSequencePlanMemoryContainer.getEvaluation());
				}else {
					setActivation(0.0d);
				}
			}

		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void proc() {

		if(actionSequencePlanMemoryContainer != null && actionSequencePlanMemoryContainer.getI() != null && actionSequencePlanMemoryContainer.getI() instanceof ActionSequencePlan) {

			ActionSequencePlan actionSequencePlan = (ActionSequencePlan) actionSequencePlanMemoryContainer.getI();
			String currentActionId = actionSequencePlan.getCurrentActionId();

			if(currentActionId != null && currentActionId.equalsIgnoreCase(id)) {
				proc(perceptualMemories, broadcastMemory, motorMemory);
			}
		}
	}
	
	/**
	 * Main method of this Action From Planning Codelet called passing all the input and output memories necessary.
	 * 
	 * @param perceptualMemories
	 * 			the input memories coming from perception.
	 * @param broadcastMemory
	 * 			the input memories coming from the conscious broadcast of the planner.
	 * @param motorMemory
	 * 			the output motor memory to be dispatched to the actuators.
	 */
	public abstract void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory);
	
	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Action From Planning Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Action From Planning Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Action From Planning Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Action From Planning Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Action From Planning Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Action From Planning Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Action From Planning Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Action From Planning Codelet.
	 * 
	 * @param motorCodeletId
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}
}
