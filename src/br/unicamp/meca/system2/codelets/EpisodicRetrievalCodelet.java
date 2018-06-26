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
 * This class represents MECA's Episodic Retrieval Codelet. As soon as many
 * Episodes are already stored in the Episodic Memory, the Episodic Retrieval
 * Codelet can perform its abilities. The behavior of the Episodic Retrieval
 * Codelet is quite straightforward. Basically, it collects a cue from the Cue
 * Memory (which is basically populated during the working of the Planning
 * Subystem), tries to recover pertinent episodes from the Episodic Memory and
 * brings these episodes to the Episodic Recall Memory within the Working
 * Memory, from where they become available for the Planning Subsystem.
 * <p>
 * Usually, Episodic Retrieval Codelets in System 2 are application-specific, and
 * the MECA software implementation just provides basic template class, which is
 * a wrapper to CST's {@link Codelet}, to be reused while building an
 * application using MECA.
 * 
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class EpisodicRetrievalCodelet extends Codelet {

}
