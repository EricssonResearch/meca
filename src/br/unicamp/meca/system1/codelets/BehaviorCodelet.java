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
 * This class represents the MECA Motivational Behavioral Codelet. This
 * Behavioral Codelet allows inputs from one of the Motivational Codelets. It
 * outputs necessarily to a MotorCodelet. As the name suggests, the idea behind
 * this behavioral codelet is to provide a behavior generator driven from the
 * motivational system in System 1.
 * <p>
 * Usually, Motivational Behavioral Codelets are application-specific, and the
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

	protected ArrayList<String> motivationalCodeletsIds;

	protected String soarCodeletId;
	
	protected ActionSequencePlan actionSequencePlan;
	
	private Memory worldSituation;
	
	private Memory actionSequencePlanMemoryContainer;
	
	private Memory actionSequencePlanRequestMemoryContainer;
	
	protected ArrayList<Memory> drivesMO;

	/**
	 * Creates a MECA Motivational Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Motivational Behavioral Codelet. Must be unique
	 *            per Motivational Behavioral Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Motivational Behavioral Codelet.
	 * @param motivationalCodeletsIds
	 *            the list of ids of the Motivational Codelets whose outputs
	 *            will be read by this Motivational Behavioral Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Motivational Behavioral Codelet.
	 */
	public BehaviorCodelet(String id, ArrayList<String> perceptualCodeletsIds, ArrayList<String> motivationalCodeletsIds,
			String soarCodeletId, ActionSequencePlan actionSequencePlan) {
		super();
		setName(id);
		this.id = id;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.motivationalCodeletsIds = motivationalCodeletsIds;
		this.soarCodeletId = soarCodeletId;
		this.actionSequencePlan = actionSequencePlan;
	}
	
	/**
	 * Track and advance actions in the sequence plan. To be implemented in each object of this class,
	 * according to its action sequence plan.
	 * @param actionSequencePlan
	 * @param worldSituation
	 */
	public abstract void trackActionSequencePlan(Memory worldSituation, ActionSequencePlan actionSequencePlan);
	
	@Override
	public void accessMemoryObjects() {
		
		int index=0;
		
		if(worldSituation==null && perceptualCodeletsIds!=null && perceptualCodeletsIds.size()>0 && perceptualCodeletsIds.get(0)!=null)
			worldSituation = this.getInput(perceptualCodeletsIds.get(0), index); 
		
		if(actionSequencePlanMemoryContainer==null)
			actionSequencePlanMemoryContainer = this.getOutput(MecaMind.ACTION_SEQUENCE_PLAN_ID, index);
		
		if(actionSequencePlanRequestMemoryContainer==null)
			actionSequencePlanRequestMemoryContainer = this.getOutput(MecaMind.ACTION_SEQUENCE_PLAN_REQUEST_ID, index);
		
		if(drivesMO==null||drivesMO.size()==0)
		{
			drivesMO = new ArrayList<>();

			if(getMotivationalCodeletsIds()!=null){

				for(String motivationalCodeletsId : getMotivationalCodeletsIds())
				{
					Memory inputDrive = this.getInput(motivationalCodeletsId + "_DRIVE_MO");
					drivesMO.add(inputDrive);
				}
			}
		}

	}
	
	@Override
	public void calculateActivation() {
		
		double activation = 0;
		
		if (drivesMO!=null && drivesMO.size() > 0){

			for (Memory driveMO: drivesMO) {
				activation += driveMO.getEvaluation();
			}

			activation /= drivesMO.size();

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
	
	@Override
	public void proc() {
		
		trackActionSequencePlan(worldSituation,actionSequencePlan);
		
		if(actionSequencePlan != null) {
			((MemoryContainer) actionSequencePlanMemoryContainer).setI(actionSequencePlan,getActivation(),id);
		}else {
			((MemoryContainer) actionSequencePlanRequestMemoryContainer).setI(id,getActivation(),id);
		}
	}

	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Motivational Behavioral Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Motivational Behavioral Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of this Motivational Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Motivational Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the list of ids of the Motivational Codelets whose outputs will
	 * be read by this Motivational Behavioral Codelet.
	 * 
	 * @return the motivationalCodeletsIds
	 */
	public ArrayList<String> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}

	/**
	 * Sets the list of ids of the Motivational Codelets whose outputs will be
	 * read by this Motivational Behavioral Codelet.
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
