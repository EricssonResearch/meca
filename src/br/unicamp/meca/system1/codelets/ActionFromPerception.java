/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;

/**
 * @author andre
 *
 */
public abstract class ActionFromPerception extends Codelet {

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
	 * Creates a MECA Reactive Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Reactive Behavioral Codelet. Must be unique per
	 *            Reactive Behavioral Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Reactive Behavioral Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Reactive Behavioral Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Reactive Behavioral Codelet.
	 */
	public ActionFromPerception(String id, ArrayList<String> perceptualCodeletsIds, ArrayList<String> motivationalCodeletsIds, String motorCodeletId,
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
					Memory inputDrive = this.getInput(motivationalCodeletsId + "_DRIVE_MO");
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
	 * 
	 * @param perceptualMemories
	 * @param broadcastMemory
	 * @param motorMemory
	 */
	public abstract void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory);
	
	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Reactive Behavioral Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Reactive Behavioral Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Reactive Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Reactive Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
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
