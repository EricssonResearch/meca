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
 * 
 * This class represents the MECA Perceptual Codelets. The Perceptual Subsystem
 * is the subsystem responsible for abstracting the information coming from
 * Sensory Memory and building more sophisticated representations for what is
 * going on at the environment. There might be increasing layers of abstraction
 * in this process, under which raw data measures are transformed in a higher
 * level understanding of the environment situation.
 * <p>
 * Usually, Perceptual Codelets are application-specific, and the MECA software
 * implementation just provides basic template class, which is a wrapper to
 * CST's {@link Codelet}, to be reused while building an application using MECA.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 */
public abstract class PerceptualCodelet extends Codelet {

	protected String id;

	protected ArrayList<String> sensoryCodeletsIds;

	/**
	 * Creates a MECA Perceptual Codelet.
	 * 
	 * @param id
	 *            the id of the Perceptual Codelet. Must be unique per
	 *            Perceptual Codelet.
	 * @param sensoryCodeletsIds
	 *            the list of Sensory Codelets whose outputs will be read by
	 *            this Perceptual Codelet
	 */
	public PerceptualCodelet(String id, ArrayList<String> sensoryCodeletsIds) {
		super();
		this.id = id;
		setName(id);
		this.sensoryCodeletsIds = sensoryCodeletsIds;
	}

	/**
	 * Returns the id of this Perceptual Codelet.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Perceptual Codelet.
	 * 
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Returns the list of attached Sensory Codelets' ids.
	 * 
	 * @return the sensoryCodeletsIds
	 */
	public ArrayList<String> getSensoryCodeletsIds() {
		return sensoryCodeletsIds;
	}

	/**
	 * Sets the list of attached Sensory Codelets' ids.
	 * 
	 * @param sensoryCodeletsIds
	 *            the sensoryCodeletsIds to set
	 */
	public void setSensoryCodeletsIds(ArrayList<String> sensoryCodeletsIds) {
		this.sensoryCodeletsIds = sensoryCodeletsIds;
	}

}
