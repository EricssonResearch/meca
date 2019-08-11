/**
 * 
 */
package br.unicamp.meca.mind;

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
		// TODO Auto-generated method stub

	}

}
