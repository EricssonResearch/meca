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

/**
 * 
 * This class represents the Random Behavioral Codelets. In this specific
 * Behavioral Codelet, there is no input driving the behaviors, just outputs,
 * which as the name anticipates are randomly generated. The main idea behind
 * having such random behavior generator is to be able to scape local minimum.
 * <p>
 * Usually, Random Behavioral Codelets are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class RandomBehavioralCodelet extends Codelet {

	protected String id;

	protected String motorCodeletId;

	protected String soarCodeletId;

	/**
	 * Creates a MECA Random Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Random Behavioral Codelet. Must be unique per
	 *            Random Behavioral Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will be affected by the
	 *            Memory Container which will receive this Random Behavioral
	 *            Codelet's output
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Random Behavioral Codelet
	 */
	public RandomBehavioralCodelet(String id, String motorCodeletId, String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of the attached Soar Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the attached Soar Codelet
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of this Random Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Random Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the attached Motor Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the attached Motor Codelet.
	 * 
	 * @param motorCodeletId
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}

}
