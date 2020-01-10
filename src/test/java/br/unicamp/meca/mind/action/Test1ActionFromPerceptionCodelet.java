/**
 * 
 */
package br.unicamp.meca.mind.action;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.meca.system1.codelets.ActionFromPerceptionCodelet;

/**
 * @author andre
 *
 */
public class Test1ActionFromPerceptionCodelet extends ActionFromPerceptionCodelet {

	public Test1ActionFromPerceptionCodelet(String id, ArrayList<String> perceptualCodeletsIds,
			ArrayList<String> motivationalCodeletsIds, String motorCodeletId, String soarCodeletId) {
		super(id, perceptualCodeletsIds, motivationalCodeletsIds, motorCodeletId, soarCodeletId);
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
				
				((MemoryContainer) motorMemory).setI("Test1ActionFromPerception - "+perceptualContent,getActivation(),id);
			}
		}
	}

}
