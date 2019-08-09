/**
 * 
 */
package br.unicamp.meca.system1.codelets;

import java.util.List;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;

/**
 * @author andre
 *
 */
public interface ISensoryCodelet {

	/**
	 * Returns the id of this Sensory Codelet.
	 * 
	 * @return the id
	 */
	String getId();
	
	/**
	 * Gets the list of output memories.
	 * 
	 * @return the outputs.
	 */
	List<Memory> getOutputs();

	/**
	 * Add a memory to the output list.
	 * 
	 * @param sensoryMemory
	 *            one output to set.
	 */
	void addOutput(MemoryObject sensoryMemory);

}
