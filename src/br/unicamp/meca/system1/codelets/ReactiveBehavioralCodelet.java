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
 * This class represents the MECA Reactive Behavioral Codelet. This Behavioral
 * Codelet allows inputs from one or more of the PerceptualCodelets. It outputs
 * necessarily to a MotorCodelet. As the name suggests, the idea behind this
 * behavioral codelet is to provide a reactive behavior generator in System 1.
 * <p>
 * Usually, Reactive Behavioral Codelets are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class ReactiveBehavioralCodelet extends Codelet {

	protected String id;

	protected ArrayList<String> perceptualCodeletsIds;

	protected String motorCodeletId;

	protected String soarCodeletId;

	/**
	 * Creates a MECA Reactive Behavioral Codelet.
	 * 
	 * @param id
	 *            the id of the Reactive Behavioral Codelet. Must be unique per
	 *            Reactive Behavioral Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Reactive Behavioral Codelet.
	 * @param motorCodeletId
	 *            the id of the Motor Codelet which will read the outputs of
	 *            this Reactive Behavioral Codelet.
	 * @param soarCodeletId
	 *            the id of the Soar Codelet whose outputs will be read by this
	 *            Reactive Behavioral Codelet.
	 */
	public ReactiveBehavioralCodelet(String id, ArrayList<String> perceptualCodeletsIds, String motorCodeletId,
			String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the id of the Soar Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}

	/**
	 * Sets the id of the Soar Codelet whose outputs will be read by this
	 * Reactive Behavioral Codelet.
	 * 
	 * @param soarCodeletId
	 *            the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}

	/**
	 * Returns the list of the Perceptual Codelet's ids whose outputs will be
	 * read by this Reactive Behavioral Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of the Perceptual Codelet's ids whose outputs will be read
	 * by this Reactive Behavioral Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}

	/**
	 * Returns the id of this Reactive Behavioral Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Reactive Behavioral Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
	 * 
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * Sets the id of the Motor Codelet which will read the outputs of this
	 * Reactive Behavioral Codelet.
	 * 
	 * @param motorCodeletId
	 *            the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}

}
