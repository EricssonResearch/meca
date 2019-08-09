/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP and Ericsson Research                  *
 * All rights reserved. This program and the accompanying materials            *
 * are made available under the terms of the GNU Lesser Public License v3      *
 * which accompanies this distribution, and is available at                    *
 * http://www.gnu.org/licenses/lgpl.html                                       *
 *                                                                             *
 * Contributors:                                                               *
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut, S. de Paula,      * 
 *     E. Castro, V. Figueredo and K. Raizer                                   *
 *                                                                             *
 ******************************************************************************/
package br.unicamp.meca.system1.codelets;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;

/**
 * This class represents the MECA Sensory Codelets, which are responsible for
 * grabbing information from sensors at the environment and feeding the
 * corresponding Memory Objects. Depending on the applications (e.g. robotic
 * applications), sensory codelets will be really reading the sensor values and
 * creating a corresponding representation. In other applications (e.g. in a
 * video-game, an internet agent or a virtual world), sensory codelets will open
 * sockets to other computer applications and will simulate the acquisition of
 * data from the environment.
 * <p>
 * Usually, Sensory Codelets are application-specific, and the MECA software
 * implementation just provides basic template class, which is a wrapper to
 * CST's {@link Codelet}, to be reused while building an application using MECA.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class SensoryCodelet extends Codelet implements ISensoryCodelet{

	protected String id;
	
	protected Memory sensoryMemory;

	/**
	 * Creates a MECA Sensory Codelet.
	 * 
	 * @param id
	 *            the id of the Sensory Codelet. Must be unique per Sensory
	 *            Codelet.
	 */
	public SensoryCodelet(String id) {

		super();
		this.id = id;
		setName(id);
	}
	

	@Override
	public void accessMemoryObjects() {
		int index = 0;

		if(sensoryMemory == null)
			sensoryMemory = this.getOutput(id, index);	

	}

	@Override
	public void calculateActivation() {
		try{

			setActivation(0.0d);

		} catch (CodeletActivationBoundsException e) {

			e.printStackTrace();
		}	
	}
	
	@Override
	public void proc() {
		proc(sensoryMemory);
	}
	
	/**
	 * Calls the main method of this Sensory Codelet providing the memory with
	 * the information coming from the sensors.
	 * 
	 * @param sensoryMemory 
	 * 						the information coming from sensor represented as a memory
	 */
	public abstract void proc(Memory sensoryMemory);

	/**
	 * Returns the id of this Sensory Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Sensory Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

}
