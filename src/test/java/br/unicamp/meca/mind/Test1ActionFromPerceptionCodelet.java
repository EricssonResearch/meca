/**
 * 
 */
package br.unicamp.meca.mind;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Memory;
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
		// TODO Auto-generated method stub

	}

}
