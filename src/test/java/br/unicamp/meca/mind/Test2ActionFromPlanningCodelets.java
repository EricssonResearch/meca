/**
 * 
 */
package br.unicamp.meca.mind;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.ActionFromPlanningCodelet;

/**
 * @author andre
 *
 */
public class Test2ActionFromPlanningCodelets extends ActionFromPlanningCodelet {

	public Test2ActionFromPlanningCodelets(String id, ArrayList<String> perceptualCodeletsIds, String motorCodeletId,
			String soarCodeletId) {
		super(id, perceptualCodeletsIds, motorCodeletId, soarCodeletId);
	}

	@Override
	public void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory) {
		// TODO Auto-generated method stub

	}

}
