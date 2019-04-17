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
import java.util.HashMap;
import java.util.Map;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;

/**
 * This class represents the MECA Motivational Codelet in System 1. The standard
 * dataflow for the System 1 Motivational Subsystem starts from Sensory Memory
 * Objects flowing through Motivational Codelets to generate Drives.
 * These Drives are then used by Motivational
 * Behavior Codelets in order contribute with behaviors to be selected in the
 * Dynamical Subsumption scheme.
 * 
 * @author E. Froes
 * @author A. L. O. Paraense
 * @see br.unicamp.cst.motivational.MotivationalCodelet
 */
public abstract class MotivationalCodelet extends br.unicamp.cst.motivational.MotivationalCodelet {

	protected ArrayList<String> sensoryCodeletsIds;
	
	protected HashMap<String, Double> motivationalCodeletsIds;

	/**
	 * Creates a MECA Motivational Codelet.
	 * 
	 * @param id
	 *            the id of the Motivational Codelet. Must be unique per
	 *            Motivational Codelet.
	 * @param level
	 *            the level of this Motivational Codelet.
	 * @param priority
	 *            the priority of this Motivational Codelet.
	 * @param urgencyThreshold
	 *            the urgency threshold of this Motivational Codelet.
	 * @param sensoryCodeletsIds
	 *            the sensory codelets ids whose outputs are read by this
	 *            Motivational Codelet.
	 * @param motivationalCodeletsIds
	 *            the motivational codelets Ids
	 * @throws CodeletActivationBoundsException
	 *             if activation set to less than 0 (zero)  or greater than 1 (one).
	 */
	public MotivationalCodelet(String id, double level, double priority, double urgencyThreshold,
			ArrayList<String> sensoryCodeletsIds, HashMap<String, Double> motivationalCodeletsIds)
			throws CodeletActivationBoundsException {

		super(id, level, priority, urgencyThreshold);
		setName(id);

		setSensoryCodeletsIds(sensoryCodeletsIds);
		setMotivationalCodeletsIds(motivationalCodeletsIds);
	}

	@Override
	public void accessMemoryObjects() {

		if (getSensoryVariables().size() == 0) {
			for (Memory sensoryMO : getInputs()) {
				if (!sensoryMO.getName().contains("DRIVE"))
					getSensoryVariables().add(sensoryMO);
			}
		}

		if (getDrivesRelevance().size() == 0) {
			for (Memory driveMO : getInputs()) {
				if (driveMO.getName().contains("DRIVE")) {
					getDrivesRelevance().putAll((Map<? extends Memory, ? extends Double>) driveMO.getI());
				}
			}
		}

		if (getOutputDriveMO() == null) {
			setOutputDriveMO(this.getOutputs().get(0));
		}

	}

	/**
	 * Gets the Sensory Codelets Ids whose outputs are read by this Motivational
	 * Codelet.
	 * 
	 * @return the sensoryCodeletsIds
	 */
	public ArrayList<String> getSensoryCodeletsIds() {
		return sensoryCodeletsIds;
	}

	/**
	 * Sets the Sensory Codelets Ids whose outputs are read by this Motivational
	 * Codelet.
	 * 
	 * @param sensoryCodeletsIds
	 *            the Sensory Codelets Ids to set
	 */
	public void setSensoryCodeletsIds(ArrayList<String> sensoryCodeletsIds) {
		this.sensoryCodeletsIds = sensoryCodeletsIds;
	}

	/**
	 * Gets the Motivational Codelets Ids.
	 * 
	 * @return motivationalCodeletsIds
	 */
	public HashMap<String, Double> getMotivationalCodeletsIds() {
		return motivationalCodeletsIds;
	}

	/**
	 * Sets the Motivational Codelets Ids.
	 * 
	 * @param motivationalCodeletsIds
	 *            the Motivational Codelets Ids to set
	 */
	public void setMotivationalCodeletsIds(HashMap<String, Double> motivationalCodeletsIds) {
		this.motivationalCodeletsIds = motivationalCodeletsIds;
	}
}
