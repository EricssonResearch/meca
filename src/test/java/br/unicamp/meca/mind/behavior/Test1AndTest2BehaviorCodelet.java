/**
 * 
 */
package br.unicamp.meca.mind.behavior;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.system1.codelets.BehaviorCodelet;

/**
 * @author andre
 *
 */
public class Test1AndTest2BehaviorCodelet extends BehaviorCodelet {

	public Test1AndTest2BehaviorCodelet(String id, ArrayList<String> perceptualCodeletsIds,
			ArrayList<String> motivationalCodeletsIds, String soarCodeletId, ActionSequencePlan actionSequencePlan) {
		super(id, perceptualCodeletsIds, motivationalCodeletsIds, soarCodeletId, actionSequencePlan);
	}

	@Override
	public void trackActionSequencePlan(ArrayList<Memory> perceptualMemories, ActionSequencePlan actionSequencePlan) {
		
		if(actionSequencePlan == null || actionSequencePlan.getActionIdSequence() == null) {
			return;
		}
					
		actionSequencePlan.setCurrentActionIdIndex(0);
		
		//In this test, will never go on to the second action. We could have limit on perceptual memory to move on, though.
	}

}
