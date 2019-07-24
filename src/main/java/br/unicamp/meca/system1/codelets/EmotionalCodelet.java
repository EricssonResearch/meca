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

import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;

/**
 * This class represents MECA Emotional Codelet. Emotional Codelets change the
 * Drives intensity landscape, resulting in a change of priorities in order to
 * better attend the critical situation.
 * <p>
 * In MECA, we will be following Canamero's approach to emotions, together with
 * Sun's proposal to a motivational system. Under this view, emotions work as
 * temporary cognitive distortions on system drives, resulting in a change in
 * priorities, due to the recognition of critical situations.
 * 
 * @author A. L. O. Paraense
 *
 */
public abstract class EmotionalCodelet extends br.unicamp.cst.motivational.EmotionalCodelet {

	/**
	 * Create a MECA Emotional Codelet.
	 * 
	 * @param id
	 *            the id of the Emotional Codelet. Must be unique per Emotional
	 *            Codelet.
	 * @throws CodeletActivationBoundsException
	 *             if activation set to less than zero or greater than 1.
	 */
	public EmotionalCodelet(String id) throws CodeletActivationBoundsException {
		super(id);
		setName(id);
	}
}
