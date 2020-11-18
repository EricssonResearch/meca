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
 * This class represents the MECA Activity Tracking Codelet. This
 * Activity Tracking Codelet allows inputs from one or more of the Perceptual Codelets. The idea behind
 * this codelet is to track the steps of an Action Sequence Plan in System 1.
 * <p>
 * Usually, Activity Tracking Codelets are application-specific, and the
 * MECA software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public class ActivityTrackingCodelet extends Codelet {
	
	protected String id;
	
	protected ArrayList<String> perceptualCodeletsIds;
	protected ArrayList<Memory> perceptualMemories;
	
	protected Memory actionSequencePlanMemoryContainer;
	
	protected ActionSequencePlan actionSequencePlan;
	
	/**
	 * Creates a MECA Activity Tracking Codelet.
	 * 
	 * @param id
	 *            the id of the Activity Tracking. Must be unique
	 *            per Activity Tracking.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs
	 *            will be read by this Activity Tracking.
	 * @see Codelet
	 * @see ActionSequencePlan
	 */
	public ActivityTrackingCodelet(String id, ArrayList<String> perceptualCodeletsIds) {
		super();
		setName(id);
		this.id = id;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
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
		
		if(actionSequencePlanMemoryContainer == null)
			actionSequencePlanMemoryContainer = this.getInput(MecaMind.ACTION_SEQUENCE_PLAN_ID, index);

	}

	@Override
	public void calculateActivation() {
		try {
			setActivation(0.0d);
		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Track and advance actions in the sequence plan.
     * 
     * @param actionSequencePlan
     * 				the ActionSequencePlan.
     * @param perceptualMemories
     *            the list Perceptual Memories coming from Perceptual Codelets.
     */
    public void trackActionSequencePlan(ArrayList<Memory> perceptualMemories, ActionSequencePlan actionSequencePlan) {
        if(actionSequencePlan != null && 
          actionSequencePlan.getActionStepSequence() != null && 
          perceptualMemories != null && 
          perceptualMemories.size() > 0) {
          ActionStep currentActionStep = actionSequencePlan.getCurrentActionStep();
          ActionStep lastActionStep = actionSequencePlan.getLastExecutedActionStep();
          if ((actionSequencePlan.getCurrentActionIdIndex() == 0 
                || lastActionStep.needsConclusion == false) // you can go to next step if it is the first step of the plan or if the last step is clear
                && (currentActionStep != null // then just check if current ActionStep is not null to avoid breaking the next tests
                && currentActionStep.executed == false // the ActionStep was not already executed
                && currentActionStep.stopCondition(perceptualMemories)) ) { // and the ActionStep reached its final destination 
                    currentActionStep.needsConclusion = true;
                    actionSequencePlan.gotoNextAction();       
          }
        }
    }
	
	@Override
	public void proc() {
		if (actionSequencePlanMemoryContainer != null) {
                    actionSequencePlan = (ActionSequencePlan) actionSequencePlanMemoryContainer.getI();
                    trackActionSequencePlan(perceptualMemories, actionSequencePlan);
                }    
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the actionSequencePlanMemoryContainer
	 */
	public Memory getActionSequencePlanMemoryContainer() {
		return actionSequencePlanMemoryContainer;
	}

	/**
	 * @param actionSequencePlanMemoryContainer the actionSequencePlanMemoryContainer to set
	 */
	public void setActionSequencePlanMemoryContainer(Memory actionSequencePlanMemoryContainer) {
		this.actionSequencePlanMemoryContainer = actionSequencePlanMemoryContainer;
	}
}
