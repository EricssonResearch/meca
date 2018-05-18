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


public abstract class PerceptualCodelet extends Codelet {

	protected String id;
	
	/** We need a reference to the SensoryCodelets whose outputs will be read by this PerceptualCodelet*/
	protected ArrayList<String> sensoryCodeletsIds;

	/**
	 * @param id
	 * @param sensoryCodeletsIds
	 */
	public PerceptualCodelet(String id, ArrayList<String> sensoryCodeletsIds) {
		super();
		this.id = id;
		setName(id);
		this.sensoryCodeletsIds = sensoryCodeletsIds;
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
	 * @return the sensoryCodeletsIds
	 */
	public ArrayList<String> getSensoryCodeletsIds() {
		return sensoryCodeletsIds;
	}

	/**
	 * @param sensoryCodeletsIds the sensoryCodeletsIds to set
	 */
	public void setSensoryCodeletsIds(ArrayList<String> sensoryCodeletsIds) {
		this.sensoryCodeletsIds = sensoryCodeletsIds;
	}
	
	
}
