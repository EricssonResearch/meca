/**
 * 
 */
package br.unicamp.meca.mind.action;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.meca.system1.codelets.ActivityCodelet;

/**
 * @author andre
 *
 */
public class Test1ActivityCodelet extends ActivityCodelet {

	/**
	 * @param id
	 * @param perceptualCodeletsIds
	 * @param motivationalCodeletsIds
	 * @param motorCodeletId
	 * @param soarCodeletId
	 */
	public Test1ActivityCodelet(String id, ArrayList<String> perceptualCodeletsIds,
			ArrayList<String> motivationalCodeletsIds, String motorCodeletId, String soarCodeletId) {
		super(id, perceptualCodeletsIds, motivationalCodeletsIds, motorCodeletId, soarCodeletId);
	}
        
        @Override
	public void doConclusion(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory) {
             System.out.println("Concluding "+id);
             ((MemoryContainer) motorMemory).setI(id+" - concluded",getActivation(),id);
        }

	@Override
	public void proc(ArrayList<Memory> perceptualMemories, Memory broadcastMemory, Memory motorMemory) {
		if(perceptualMemories == null || perceptualMemories.size() == 0) {
			return;
		}
		
		((MemoryContainer) motorMemory).setI(null,0.0,id);
		
		for(Memory memory: perceptualMemories) {			
			if(memory.getI()!=null && memory.getI() instanceof String) {
				String perceptualContent = (String) memory.getI();
				
				((MemoryContainer) motorMemory).setI(id+" - "+perceptualContent,getActivation(),id);
			}
		}
	}
}
