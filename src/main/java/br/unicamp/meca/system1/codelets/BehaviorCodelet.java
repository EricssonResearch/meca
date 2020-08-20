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
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.models.ActionSequencePlan;

/**
 * This class represents the MECA Behavior Codelets. This
 * Behavior Codelet allows inputs from one or more of the Motivational Codelets and Perceptual Codelets. It
 * outputs necessarily an ActionSequencePlan. As the name suggests, the idea behind
 * this behavior codelet is to provide a behavior generator driven from the
 * motivational system and the perceptual system in System 1.
 * <p>
 * Usually, Behavior Codelets are application-specific, and the
 * MECA software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class BehaviorCodelet extends Codelet {

	protected String id;
	
	protected ArrayList<String> perceptualCodeletsIds;
	protected ArrayList<Memory> perceptualMemories;

	protected ArrayList<String> motivationalCodeletsIds;
	protected ArrayList<Memory> driveMemories;

	protected String soarCodeletId;
	protected Memory broadcastMemory;
	
	protected ActionSequencePlan actionSequencePlan;
	
	protected Memory actionSequencePlanMemoryContainer;
	
	protected Memory actionSequencePlanRequestMemoryContainer;
	
	/**
	 * Creates a MECA Motivational Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Behavior Codelet. Must be unique
	 *            per Behavior Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs
	 *            will be read by this Behavior Codelet.
	 * @param motivationalCodeletsIds
	 *            the list of ids of the Motivational Codelets whose outputs
	 *            will be read by this Behavior Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Behavior Codelet.
	 * @see Codelet
	 */
	public BehaviorCodelet(String id, ArrayList<String> perceptualCodeletsIds, ArrayList<String> motivationalCodeletsIds,
			String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.motivationalCodeletsIds = motivationalCodeletsIds;
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
		
		if(driveMemories == null || driveMemories.size() == 0)
		{
			driveMemories = new ArrayList<>();

			if(motivationalCodeletsIds!=null){

				for(String motivationalCodeletsId : motivationalCodeletsIds)
				{
					Memory inputDrive = this.getInput(motivationalCodeletsId + "Drive");
					driveMemories.add(inputDrive);
				}
			}
		}
		
		if(broadcastMemory == null) {
			broadcastMemory = this.getBroadcast(soarCodeletId, index);
		}
		
		if(actionSequencePlanMemoryContainer == null)
			actionSequencePlanMemoryContainer = this.getOutput(MecaMind.ACTION_SEQUENCE_PLAN_ID, index);
		
		if(actionSequencePlanRequestMemoryContainer == null)
			actionSequencePlanRequestMemoryContainer = this.getOutput(MecaMind.ACTION_SEQUENCE_PLAN_REQUEST_ID, index);

	}
	
	@Override
	public void calculateActivation() {
		
		double activation = 0;
		
		if (driveMemories!=null && driveMemories.size() > 0){

			for (Memory driveMO: driveMemories) {
				activation += driveMO.getEvaluation();
			}

			activation /= driveMemories.size();

		}
		
		try {

			if(activation<0.0d)
				activation=0.0d;

			if(activation>1.0d)
				activation=1.0d;

			setActivation(activation);

		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * Builds the action sequence plan to be outputed by this BehaviorCodelet
	 * 
	 * @param perceptualMemories the Perceptual Memories coming from Perceptual Codelets.
	 * @return the action sequence plan to be followed.
	 */
	protected abstract ActionSequencePlan buildActionSequencePlan(ArrayList<Memory> perceptualMemories);
	
	@Override
	public void proc() {
		
		actionSequencePlan = buildActionSequencePlan(perceptualMemories);
		
		if(broadcastMemory != null && broadcastMemory.getI()!= null && broadcastMemory.getI() instanceof ActionSequencePlan)
			actionSequencePlan = (ActionSequencePlan) broadcastMemory.getI();
		
		if(actionSequencePlan != null) {
			((MemoryContainer) actionSequencePlanMemoryContainer).setI(actionSequencePlan,getActivation(),id);
			((MemoryContainer) actionSequencePlanRequestMemoryContainer).setI(null,0.0d,id);
		}else {
			((MemoryContainer) actionSequencePlanMemoryContainer).setI(null,0.0d,id);
			((MemoryContainer) actionSequencePlanRequestMemoryContainer).setI(id,getActivation(),id);
		}
	}


	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Behavior Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Behavior Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of this Behavior Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Behavior Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the list of ids of the Motivational Codelets whose outputs will
	 * be read by this Behavior Codelet.
	 * 
	 * @return the motivationalCodeletsIds
	 */
	public ArrayList<String> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}

	/**
	 * Sets the list of ids of the Motivational Codelets whose outputs will be
	 * read by this Behavior Codelet.
	 * 
	 * @param motivationalCodeletsIds
	 *            the Motivational Codelets Ids to set
	 */
	public void setMotivationalCodeletsIds(ArrayList<String> motivationalCodeletsIds) {
		this.motivationalCodeletsIds = motivationalCodeletsIds;
	}

	/**
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * @param perceptualCodeletsIds the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * @return the actionSequencePlan
	 */
	public ActionSequencePlan getActionSequencePlan() {
		return actionSequencePlan;
	}

	/**
	 * @param actionSequencePlan the actionSequencePlan to set
	 */
	public void setActionSequencePlan(ActionSequencePlan actionSequencePlan) {
		this.actionSequencePlan = actionSequencePlan;
	}
}
