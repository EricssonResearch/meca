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

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;

/**
 * This class represents the MECA Motivational Behavioral Codelet. This
 * Behavioral Codelet allows inputs from one of the Motivational Codelets. It
 * outputs necessarily to a MotorCodelet. As the name suggests, the idea behind
 * this behavioral codelet is to provide a behavior generator driven from the
 * motivational system in System 1.
 * <p>
 * Usually, Motivational Behavioral Codelets are application-specific, and the
 * MECA software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class MotivationalBehavioralCodelet extends Codelet {

	protected String id;

	protected String motorCodeletId;

	protected ArrayList<String> motivationalCodeletsIds;

	protected String soarCodeletId;

	/**
	 * Creates a MECA Motivational Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Motivational Behavioral Codelet. Must be unique
	 *            per Motivational Behavioral Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Motivational Behavioral Codelet.
	 * @param motivationalCodeletsIds
	 *            the list of ids of the Motivational Codelets whose outputs
	 *            will be read by this Motivational Behavioral Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Motivational Behavioral Codelet.
	 */
	public MotivationalBehavioralCodelet(String id, String motorCodeletId, ArrayList<String> motivationalCodeletsIds,
			String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.motivationalCodeletsIds = motivationalCodeletsIds;
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Motivational Behavioral Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Motivational Behavioral Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of this Motivational Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Motivational Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Motivational Behavioral Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Motivational Behavioral Codelet.
	 * 
	 * @param motorCodeletId
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}

	/**
	 * Returns the list of ids of the Motivational Codelets whose outputs will
	 * be read by this Motivational Behavioral Codelet.
	 * 
	 * @return the motivationalCodeletsIds
	 */
	public ArrayList<String> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}

	/**
	 * Sets the list of ids of the Motivational Codelets whose outputs will be
	 * read by this Motivational Behavioral Codelet.
	 * 
	 * @param motivationalCodeletsIds
	 *            the Motivational Codelets Ids to set
	 */
	public void setMotivationalCodeletsIds(ArrayList<String> motivationalCodeletsIds) {
		this.motivationalCodeletsIds = motivationalCodeletsIds;
	}
}
