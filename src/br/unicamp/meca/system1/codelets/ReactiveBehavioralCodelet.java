/*******************************************************************************
 * Copyright (c) 2018  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     R. R. Gudwin, A. L. O. Paraense, E. Froes, W. Gibaut, S. de Paula, 
 *     E. Castro and V. Figueredo
 * 
 ******************************************************************************/
package br.unicamp.meca.system1.codelets;

import java.util.ArrayList;

import br.unicamp.cst.core.entities.Codelet;


public abstract class ReactiveBehavioralCodelet extends Codelet {

	protected String id;

	/** We need a reference to the PerceptualCodelets whose outputs will be read by this ReactiveBehavioralCodelet*/
	protected ArrayList<String> perceptualCodeletsIds;

	/** We need a reference to the MotorCodelet which will read the outputs of this ReactiveBehavioralCodelet*/
	protected String motorCodeletId;

	/** We need a reference to the SoarCodelet whose outputs will be read by this ReactiveBehavioralCodelet*/
	protected String soarCodeletId;

	/**
	 * @param id
	 * @param motorCodeletId
	 */
	public ReactiveBehavioralCodelet(String id, ArrayList<String> perceptualCodeletsIds, String motorCodeletId, String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;
		this.perceptualCodeletsIds = perceptualCodeletsIds;
		this.soarCodeletId = soarCodeletId;
	}


	/**
	 * @return the soarCodeletId
	 */
	public String getSoarCodeletId() {
		return soarCodeletId;
	}


	/**
	 * @param soarCodeletId the soarCodeletId to set
	 */
	public void setSoarCodeletId(String soarCodeletId) {
		this.soarCodeletId = soarCodeletId;
	}


	/**
	 * @return the perceptualCodeletsIds
	 */
	public ArrayList<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * @param perceptualCodeletsIds the perceptualCodeletsIds to set
	 */
	public void setPerceptualCodeletsIds(ArrayList<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}


	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the motorCodeletId
	 */
	public String getMotorCodeletId() {
		return motorCodeletId;
	}

	/**
	 * @param motorCodeletId the motorCodeletId to set
	 */
	public void setMotorCodeletId(String motorCodeletId) {
		this.motorCodeletId = motorCodeletId;
	}	

}
