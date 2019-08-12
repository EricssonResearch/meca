/**
 * 
 */
package br.unicamp.meca.mind.action;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.meca.system1.codelets.ActionFromPlanningCodelet;

/**
 * @author andre
 *
 */
public class Test1ActionFromPlanningCodelet extends ActionFromPlanningCodelet {

	public Test1ActionFromPlanningCodelet(String id, ArrayList<String> perceptualCodeletsIds, String motorCodeletId,
			String soarCodeletId) {
		super(id, perceptualCodeletsIds, motorCodeletId, soarCodeletId);
	}

	@Override
	public void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory) {
		if(perceptualMemories == null || perceptualMemories.size() == 0) {
			return;
		}
		
		motorMemory.setI(null);
		
		for(Memory memory: perceptualMemories) {			
			if(memory.getI()!=null && memory.getI() instanceof String) {
				String perceptualContent = (String) memory.getI();
				
				((MemoryContainer) motorMemory).setI("Test1ActionFromPlanning - "+perceptualContent,getActivation(),id);
			}
		}
	}
}
