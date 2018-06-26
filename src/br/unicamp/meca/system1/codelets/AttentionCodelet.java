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
import java.util.List;

import br.unicamp.cst.core.entities.Codelet;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.representation.owrl.AbstractObject;

/**
 * This class represents a MECA Attention Codelet in System 1. Attention
 * codelets are specialized kinds of codelets which will work as salience
 * detectors for objects, situations, events or episodes happening at the
 * environment which might be important for defining an action strategy, or
 * behavior. Attention Codelets track percepts in order to detect special
 * situations and send information upstream to System 2. These Attention
 * Codelets are responsible for generating the Current Perception at the Working
 * Memory, where a selected subset of the Perception Memory is made available
 * for System 2 subsystems in a representation suitable to be processed within
 * System 2.
 * 
 * 
 * @author E. Froes
 * @author A. L. O. Paraense
 * @see Codelet
 *
 */
public abstract class AttentionCodelet extends Codelet {

	private String id;
	private Memory inputPerceptsMO;
	private Memory outputFilteredPerceptsMO;

	private List<AbstractObject> inputPercepts;
	private AbstractObject outputFilteredPercepts;
	private List<String> perceptualCodeletsIds;

	/**
	 * Creates a MECA System 1 Attention Codelet.
	 * 
	 * @param id
	 *            the id of the Attention Codelet. Must be unique per Attention
	 *            Codelet.
	 * @param perceptualCodeletsIds
	 *            the list of ids of the Perceptual Codelets whose outputs will
	 *            be read by this Attention Codelet.
	 */
	public AttentionCodelet(String id, ArrayList<String> perceptualCodeletsIds) {
		setName(id);
		setId(id);
		setPerceptualCodeletsIds(perceptualCodeletsIds);
		setInputPercepts(new ArrayList<AbstractObject>());
	}

	@Override
	public void accessMemoryObjects() {

		if (getInputPerceptsMO() == null) {
			for (Memory perceptualMO : this.getInputs()) {
				getInputPercepts().add((AbstractObject) perceptualMO.getI());
			}
		}

		if (getOutputFilteredPerceptsMO() == null) {
			setOutputFilteredPerceptsMO(getOutput(getId()));
		}

	}

	@Override
	public void calculateActivation() {
		try {
			setActivation(0);
		} catch (CodeletActivationBoundsException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void proc() {
		setOutputFilteredPercepts(generateFilteredPercepts(getInputPercepts()));
		getOutputFilteredPerceptsMO().setI(getOutputFilteredPercepts());
	}

	/**
	 * Generates a filtered percept as an AbstractObject.
	 * 
	 * @param inputPercepts
	 *            the list of input percepts.
	 * @return filtered percept as an AbstractObject.
	 */
	public abstract AbstractObject generateFilteredPercepts(List<AbstractObject> inputPercepts);

	/**
	 * Gets the input Percept as a Memory Object.
	 * 
	 * @return the inputPerceptsMO
	 */
	public Memory getInputPerceptsMO() {
		return inputPerceptsMO;
	}

	/**
	 * Sets the input Percept as a Memory Object.
	 * 
	 * @param inputPerceptsMO
	 *            the input Percept as a Memory Object.
	 */
	public void setInputPerceptsMO(Memory inputPerceptsMO) {
		this.inputPerceptsMO = inputPerceptsMO;
	}

	/**
	 * Gets the output filtered percept and a Memory Object.
	 * 
	 * @return the outputFilteredPerceptsMO
	 */
	public Memory getOutputFilteredPerceptsMO() {
		return outputFilteredPerceptsMO;
	}

	/**
	 * Sets the output filtered percept and a Memory Object.
	 * 
	 * @param outputFilteredPerceptsMO
	 *            the outputFilteredPerceptsMO
	 */
	public void setOutputFilteredPerceptsMO(Memory outputFilteredPerceptsMO) {
		this.outputFilteredPerceptsMO = outputFilteredPerceptsMO;
	}

	/**
	 * Gets the list of input percepts.
	 * 
	 * @return the list of input percepts.
	 */
	public List<AbstractObject> getInputPercepts() {
		return inputPercepts;
	}

	/**
	 * Sets the list of input percepts.
	 * 
	 * @param inputPercepts
	 *            the list of input percepts.
	 */
	public void setInputPercepts(List<AbstractObject> inputPercepts) {
		this.inputPercepts = inputPercepts;
	}

	/**
	 * Gets the output filtered percepts.
	 * 
	 * @return the output filtered percepts.
	 */
	public AbstractObject getOutputFilteredPercepts() {
		return outputFilteredPercepts;
	}

	/**
	 * Sets the output filtered percepts.
	 * 
	 * @param outputFilteredPercepts
	 *            the output filtered percepts.
	 */
	public void setOutputFilteredPercepts(AbstractObject outputFilteredPercepts) {
		this.outputFilteredPercepts = outputFilteredPercepts;
	}

	/**
	 * Gets the id of this Attention Codelet.
	 * 
	 * @return the id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the id of this Attention Codelet.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * Gets the list of ids of the Perceptual Codelets whose outputs will be
	 * read by this Attention Codelet.
	 * 
	 * @return the perceptualCodeletsIds
	 */
	public List<String> getPerceptualCodeletsIds() {
		return perceptualCodeletsIds;
	}

	/**
	 * Sets the list of ids of the Perceptual Codelets whose outputs will be
	 * read by this Attention Codelet.
	 * 
	 * @param perceptualCodeletsIds
	 *            the perceptualCodeletsIds to set.
	 */
	public void setPerceptualCodeletsIds(List<String> perceptualCodeletsIds) {
		this.perceptualCodeletsIds = perceptualCodeletsIds;
	}
}
