/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.meca.mind.MecaMind;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;

/**
 * This class represents the MECA Activity Codelet. This Activity
 * Codelet allows inputs from one or more of the PerceptualCodelets,  
 * inputs from one or more of the MotivationalCodelets and the ActionSequencePlan
 * with greatest activation. It outputs
 * necessarily to a MotorCodelet. As the name suggests, the idea behind this
 * activity codelet is to provide an action as a purely reaction to the environment
 * or else based on a plan in System 1.
 * <p>
 * Usually, Activity Codelets are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class ActivityCodelet extends Codelet {
	
	protected String id;

	protected ArrayList<String> perceptualCodeletsIds;
	protected ArrayList<Memory> perceptualMemories;
	
	protected ArrayList<String> motivationalCodeletsIds;
	protected ArrayList<Memory> driveMemories;

	protected String soarCodeletId;
	protected Memory broadcastMemory;
	
	protected Memory actionSequencePlanMemoryContainer;
	
	protected String motorCodeletId;
	protected Memory motorMemory;

	/**
	 * 
	 * Creates a MECA Activity Codelet.
	 * 
	 * @param id
	 *            the id of the Activity Codelet. Must be unique per
	 *            Activity Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Activity Codelet.
	 * @param motivationalCodeletsIds
	 *            the list of ids of the Motivational Codelets whose outputs will
	 *            be read by this Activity Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Activity Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Activity Codelet.
	 */
	public ActivityCodelet(
			String id, 
			ArrayList<String> perceptualCodeletsIds, 
			ArrayList<String> motivationalCodeletsIds, 
			String motorCodeletId,
			String soarCodeletId) {
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
					if (perceptualMemory != null)
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
                                        if (inputDrive != null)
                                            driveMemories.add(inputDrive);
				}
			}
		}
		
		if(broadcastMemory == null) {
			broadcastMemory = this.getBroadcast(soarCodeletId, index);
		}
		

		if(motorMemory==null && motorCodeletId!=null)
			motorMemory = this.getOutput(motorCodeletId, index);
		
		if(actionSequencePlanMemoryContainer == null)
			actionSequencePlanMemoryContainer = this.getInput(MecaMind.ACTION_SEQUENCE_PLAN_ID, index);
	}

	@Override
	public void calculateActivation() {
		double activation = 0;
		
		if(actionSequencePlanMemoryContainer != null && actionSequencePlanMemoryContainer.getI() != null && actionSequencePlanMemoryContainer.getI() instanceof ActionSequencePlan) {

			ActionSequencePlan actionSequencePlan = (ActionSequencePlan) actionSequencePlanMemoryContainer.getI();
			ActionStep currentActionId = actionSequencePlan.getCurrentActionStep();
			
			if(currentActionId != null && currentActionId.getActionId().equalsIgnoreCase(id)) {
				activation = actionSequencePlanMemoryContainer.getEvaluation();
			}else {
				activation = 0.0d;
			}
		}else if (driveMemories!=null && driveMemories.size() > 0){

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
		if(actionSequencePlanMemoryContainer != null && actionSequencePlanMemoryContainer.getI() != null && actionSequencePlanMemoryContainer.getI() instanceof ActionSequencePlan) {
			ActionSequencePlan actionSequencePlan = (ActionSequencePlan) actionSequencePlanMemoryContainer.getI();
			ActionStep currentAction = actionSequencePlan.getCurrentActionStep();
                        String currentActionId = currentAction.getActionId();

			if(currentActionId != null && currentActionId.equalsIgnoreCase(id) && currentAction.executed == false) {
				proc(perceptualMemories, broadcastMemory, motorMemory);
			}
                        else {
                            ActionStep lastActionStep = actionSequencePlan.getLastActionStep();
                            String lastActionId = lastActionStep.getActionId();
                            if (lastActionStep != null && lastActionStep.needsConclusion && lastActionId.equalsIgnoreCase(id)) {
                                System.out.println("CurrentID: "+actionSequencePlan.getCurrentActionIdIndex()+" CurrentActionStep: "+currentActionId+" LastActionStep: "+lastActionStep.getActionId());
                                doConclusion(perceptualMemories, broadcastMemory, motorMemory);
                                lastActionStep.needsConclusion = false;
                            }
                        }
		}else {
			proc(perceptualMemories, broadcastMemory, motorMemory);
		}
	}
	
	/**
	 * Main method of the Activity Codelet called passing all input and output necessary memories.
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
	 * Method to be called to do the Conclusion of an ActionStep, after its activities are over. 
         * Similar to the proc method, but to be called one last time to clean whatever needs to be cleaned at the motorMemory
	 * 
	 * @param perceptualMemories
	 * 			the input memories coming from perception.
	 * @param broadcastMemory
	 * 			the input memory coming from the conscious planner broadcast.
	 * @param motorMemory
	 * 			the output motor memory.
	 */
	public abstract void doConclusion(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory);

	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Activity Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Activity Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Activity Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Activity Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Activity Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Activity Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Activity Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Activity Codelet.
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
        
        public ArrayList<Memory> getPerceptionMemories() {
            return(perceptualMemories);
        }
        
        public ArrayList<Memory> getDriveMemories() {
            return(driveMemories);
        }
        
        public Memory getBroadcastMemory() {
            return(broadcastMemory);
        }
        
        public Memory getActionSequencePlanMemoryContainer() {
            return(actionSequencePlanMemoryContainer);
        }
        
        public Memory getMotorMemory() {
            return(motorMemory);
        }
}
