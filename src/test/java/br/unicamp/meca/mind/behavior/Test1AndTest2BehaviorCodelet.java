/**
 * 
 */
package br.unicamp.meca.mind.behavior;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;
import br.unicamp.meca.models.ActionStepTest;
import br.unicamp.meca.system1.codelets.BehaviorCodelet;

/**
 * @author andre
 *
 */
public class Test1AndTest2BehaviorCodelet extends BehaviorCodelet {
	
	private ActionSequencePlan actionSequencePlan;

	public Test1AndTest2BehaviorCodelet(String id, ArrayList<String> perceptualCodeletsIds,
			ArrayList<String> motivationalCodeletsIds, String soarCodeletId) {
		super(id, perceptualCodeletsIds, motivationalCodeletsIds, soarCodeletId);
	}

	@Override
	protected ActionSequencePlan buildActionSequencePlan(ArrayList<Memory> perceptualMemories) {
		
		if(actionSequencePlan == null) {
			ActionStep as1 = new ActionStepTest("Test2Activity");
	        ActionStep as2 = new ActionStepTest("Test3Activity");
	        actionSequencePlan = new ActionSequencePlan(new ActionStep[] {as1,as2});
		}
		    
		return actionSequencePlan;
	}

}
