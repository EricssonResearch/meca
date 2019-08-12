/**
 * 
 */
package br.unicamp.meca.mind;

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
		// TODO Auto-generated method stub

	}

}
