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

import br.unicamp.cst.core.entities.Codelet;

/**
 * This class represents MECA's Expectation Codelet. An Expectation Codelet,
 * supposed to run some sort of HTM like algorithm, will get information from
 * the Working Memory (with all of its sub-memories), and should be generating a
 * Predicted Situation, which is to be compared in the near future with the
 * Current Perception in order to evolve its predictive abilities. Right now,
 * the implementation of the Expectation Codelet is just a template for a future
 * to be implemented algorithm based on HTM.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class ExpectationCodelet extends Codelet {

}
