/**
 * 
 */
package br.unicamp.meca.mind.behavior;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.models.ActionSequencePlan;
import br.unicamp.meca.models.ActionStep;
import br.unicamp.meca.system1.codelets.BehaviorCodelet;

/**
 * @author andre
 *
 */
public class Test1AndTest2BehaviorCodelet extends BehaviorCodelet {

	public Test1AndTest2BehaviorCodelet(String id, ArrayList<String> perceptualCodeletsIds,
			ArrayList<String> motivationalCodeletsIds, String soarCodeletId) {
		super(id, perceptualCodeletsIds, motivationalCodeletsIds, soarCodeletId);
	}

	@Override
	protected ActionSequencePlan buildActionSequencePlan(ArrayList<Memory> perceptualMemories) {
		
		ActionStep as1 = new ActionStep("Test1ActionFromPlanningCodelet");
        ActionStep as2 = new ActionStep("Test2ActionFromPlanningCodelet");
        ActionSequencePlan test1Test2ActionSequence = new ActionSequencePlan(new ActionStep[] {as1,as2});
        
		return test1Test2ActionSequence;
	}

}
