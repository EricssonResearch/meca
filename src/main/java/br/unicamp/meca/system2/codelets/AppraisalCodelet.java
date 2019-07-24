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
package br.unicamp.meca.system2.codelets;

/**
 * This class represents the MECA Appraisal Codelet in System 2. This codelet is
 * used to compare the Current Perception with the Predicted Situation, in order
 * to evaluate if this Predicted Situation is consistent, and adapt the behavior
 * of the Expectation Sub-system, turning these expectations more realistic.
 * 
 * @author A. L. O. Paraense
 * @see br.unicamp.cst.motivational.AppraisalCodelet
 */
public abstract class AppraisalCodelet extends br.unicamp.cst.motivational.AppraisalCodelet {

	/**
	 * Creates the MECA Appraisal Codelet.
	 * 
	 * @param name
	 *            the name/id of this Codelet
	 */
	public AppraisalCodelet(String name) {
		super(name);
	}

}