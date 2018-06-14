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
public abstract class SensoryCodelet extends Codelet {

	protected String id;

	/**
	 * Creates a MECA Sensory Codelet.
	 * 
	 * @param id
	 */
	public SensoryCodelet(String id) {
		
		super();
		this.id = id;
		setName(id);
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

}
