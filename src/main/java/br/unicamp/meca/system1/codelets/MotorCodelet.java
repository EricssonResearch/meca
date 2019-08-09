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
 * This class represents the MECA Motor Codelets. Motor codelets simply pick up
 * the result Memory Object from the Motor Memory and reacts directly at the
 * environment. This can be done by simply capturing actuator values and feeding
 * actuators, or by some special protocol interacting with external software or
 * hardware.
 * <p>
 * Usually, Motor Codelets are application-specific, and the MECA software
 * implementation just provides basic template class, which is a wrapper to
 * CST's {@link Codelet}, to be reused while building an application using MECA.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 */
public abstract class MotorCodelet extends Codelet implements IMotorCodelet{

	protected String id;
	
	protected Memory motorMemory;

	/**
	 * Creates a MECA Motor Codelet.
	 * 
	 * @param id
	 *            the id of the Motor Codelet. Must be unique per Motor Codelet.
	 */
	public MotorCodelet(String id) {
		super();
		this.id = id;
		setName(id);
	}
	
	@Override
	public void accessMemoryObjects() {
		
		int index=0;

		if(motorMemory==null)
			motorMemory = this.getInput(id, index);

	}

	@Override
	public void calculateActivation() {
		
		try {

			setActivation(0.0d);

		} catch (CodeletActivationBoundsException e){		

			e.printStackTrace();
		}
		
	}
	
	@Override
	public void proc() {
		proc(motorMemory);
	}
	
	/**
	 * Main method of the motor codelet called providing
	 * the motor memory.
	 * 
	 * @param motorMemory 
	 * 						the input motor memory.
	 */
	public abstract void proc(Memory motorMemory);

	/**
	 * Returns the id of this Motor Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Motor Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
}