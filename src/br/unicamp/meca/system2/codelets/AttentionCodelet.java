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
 * This class represents the MECA Attention Codelet in System 2. Beware that
 * there is also an Attention Codelet in MECA's System 1. The System 2
 * Specification in MECA includes the definition of an Episodic Subsystem,
 * responsible for higher-level perception with the tracking of time along
 * Perceptual Memory and with the aid of Attention Codelets discover and detect
 * the formation of episodes, and the storage and recovering of these episodes
 * in the Episodic Memory. *
 * <p>
 * Usually, Attention Codelet in System 2 are application-specific, and the MECA
 * software implementation just provides basic template class, which is a
 * wrapper to CST's {@link Codelet}, to be reused while building an application
 * using MECA.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class AttentionCodelet extends Codelet {

}
