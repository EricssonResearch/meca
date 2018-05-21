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


public abstract class MotivationalBehavioralCodelet extends Codelet {

	protected String id;

	protected String motorCodeletId;

	protected ArrayList<String> motivationalCodeletsIds;

	/** We need a reference to the SoarCodelet whose outputs will be read by this MotivationalBehavioralCodelet*/
	protected String soarCodeletId;

	public MotivationalBehavioralCodelet(String id, String motorCodeletId, ArrayList<String> motivationalCodeletsIds, String soarCodeletId) {
		super();
		setName(id);
		this.id = id;
		this.motorCodeletId = motorCodeletId;	
		this.motivationalCodeletsIds = motivationalCodeletsIds;
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

	public ArrayList<String> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}

	public void setMotivationalCodeletsIds(ArrayList<String> motivationalCodeletsIds) {
		this.motivationalCodeletsIds = motivationalCodeletsIds;
	}
}
