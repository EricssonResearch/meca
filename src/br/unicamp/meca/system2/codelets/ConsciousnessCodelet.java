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
package br.unicamp.meca.system2.codelets;

import br.unicamp.cst.consciousness.SpotlightBroadcastController;
import br.unicamp.cst.core.entities.CodeRack;


public abstract class ConsciousnessCodelet extends SpotlightBroadcastController {

	public ConsciousnessCodelet(CodeRack codeRack) {
		super(codeRack);
	}
}
