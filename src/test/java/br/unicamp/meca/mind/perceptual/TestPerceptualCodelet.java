/**
 * 
 */
package br.unicamp.meca.mind.perceptual;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;

/**
 * @author andre
 *
 */
public class TestPerceptualCodelet extends PerceptualCodelet {

	public TestPerceptualCodelet(String id, ArrayList<String> sensoryCodeletsIds) {
		super(id, sensoryCodeletsIds);
	}

	@Override
	public void proc(ArrayList<Memory> sensoryMemories, Memory perceptualMemory) {
		
		if(perceptualMemory == null) {
			return;
		}
		
		if(sensoryMemories == null || sensoryMemories.size() == 0) {
			return;
		}
		
		perceptualMemory.setI(null);
		
		for(Memory memory : sensoryMemories) {
			if(memory.getI() != null) {
				Object rawSensoring = memory.getI();
				
				//Let's fake we found a dog in the rawSensoring
				
				perceptualMemory.setI("A black dog");
			}
		}

	}

}
