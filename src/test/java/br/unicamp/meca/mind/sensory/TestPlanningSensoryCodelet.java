/**
 * 
 */
package br.unicamp.meca.mind.sensory;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.meca.system1.codelets.SensoryCodelet;

/**
 * @author andre
 *
 */
public class TestPlanningSensoryCodelet extends SensoryCodelet {
	
	private String sensoryContents;

	public TestPlanningSensoryCodelet(String id) {
		super(id);
	}

	@Override
	public void proc(Memory sensoryMemory) {
		
		if(sensoryMemory == null) {
			return;
		}
		
		if(sensoryContents == null) {
			return;
		}
		
		sensoryMemory.setI(sensoryContents);
	}

	public void setSensoryContents(String sensoryContents) {
		this.sensoryContents = sensoryContents;
	}
}
