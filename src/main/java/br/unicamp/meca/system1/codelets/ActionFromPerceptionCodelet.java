/*******************************************************************************
 * Copyright (c) 2019  DCA-FEEC-UNICAMP and Ericsson Research                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut,				   * 
 *     and K. Raizer.	                            						   *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;

/**
 * This class represents the MECA Action From Perception Codelet. This Action From Perception
 * Codelet allows inputs from one or more of the PerceptualCodelets and 
 * inputs from one or more of the MotivationalCodelets. It outputs
 * necessarily to a MotorCodelet. As the name suggests, the idea behind this
 * action from perception codelet is to provide an action as a purely reaction to the environment in System 1.
 * <p>
 * Usually, Action From Perception Codelets are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class ActionFromPerceptionCodelet extends Codelet {

	protected String id;

	protected ArrayList<String> perceptualCodeletsIds;
	protected ArrayList<Memory> perceptualMemories;
	
	protected ArrayList<String> motivationalCodeletsIds;
	protected ArrayList<Memory> driveMemories;

	protected String soarCodeletId;
	protected Memory broadcastMemory;
	
	protected String motorCodeletId;
	protected Memory motorMemory;
	
	/**
	 * 
	 * Creates a MECA Action From Perception Codelet.
	 * 
	 * @param id
	 *            the id of the Action From Perception Codelet. Must be unique per
	 *            Action From Perception Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Action From Perception Codelet.
	 * @param motivationalCodeletsIds
	 *            the list of ids of the Motivational Codelets whose outputs will
	 *            be read by this Action From Perception Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Action From Perception Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Action From Perception Codelet.
	 */
	public ActionFromPerceptionCodelet(String id, ArrayList<String> perceptualCodeletsIds, ArrayList<String> motivationalCodeletsIds, String motorCodeletId,
			String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.soarCodeletId = soarCodeletId;
		this.motivationalCodeletsIds = motivationalCodeletsIds;
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
		

		if(motorMemory==null && motorCodeletId!=null)
			motorMemory = this.getOutput(motorCodeletId, index);
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

	@Override
	public void proc() {
		
		proc(perceptualMemories, broadcastMemory, motorMemory);
	}
	
	/**
	 * Main method of the Action From Perception Codelet called passing all input and output necessary memories.
	 * 
	 * @param perceptualMemories
	 * 			the input memories coming from perception.
	 * @param broadcastMemory
	 * 			the input memory coming from the conscious planner broadcast.
	 * @param motorMemory
	 * 			the output motor memory.
	 */
	public abstract void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory);
	
	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Action From Perception Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Action From Perception Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Action From Perception Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Action From Perception Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Action From Perception Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Action From Perception Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Action From Perception Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Action From Perception Codelet.
	 * 
	 * @param motorCodeletId
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}

	/**
	 * @return the motivationalCodeletsIds
	 */
	public ArrayList<String> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}
}
