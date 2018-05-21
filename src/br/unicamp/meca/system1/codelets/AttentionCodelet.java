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
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.exceptions.CodeletActivationBoundsException;
import br.unicamp.cst.representation.owrl.AbstractObject;

import java.util.ArrayList;
import java.util.List;


public abstract class AttentionCodelet extends Codelet {


    private String id;
    private Memory inputPerceptsMO;
    private Memory outputFilteredPerceptsMO;

    private List<AbstractObject> inputPercepts;
    private AbstractObject outputFilteredPercepts;
    private List<String> perceptualCodeletsIds;

    public AttentionCodelet(String id, ArrayList<String> perceptualCodeletsIds){
        setName(id);
        setId(id);
        setPerceptualCodeletsIds(perceptualCodeletsIds);
        setInputPercepts(new ArrayList<AbstractObject>());
        //setOutputFilteredPercepts(new ArrayList<AbstractObject>());
    }


    @Override
    public void accessMemoryObjects(){

        if(getInputPerceptsMO() == null){
            for (Memory perceptualMO : this.getInputs()) {
                getInputPercepts().add((AbstractObject) perceptualMO.getI());
            }
        }

        if(getOutputFilteredPerceptsMO() == null){
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


    public abstract AbstractObject generateFilteredPercepts(List<AbstractObject> inputPercepts);

    public Memory getInputPerceptsMO() {
        return inputPerceptsMO;
    }

    public void setInputPerceptsMO(Memory inputPerceptsMO) {
        this.inputPerceptsMO = inputPerceptsMO;
    }

    public Memory getOutputFilteredPerceptsMO() {
        return outputFilteredPerceptsMO;
    }

    public void setOutputFilteredPerceptsMO(Memory outputFilteredPerceptsMO) {
        this.outputFilteredPerceptsMO = outputFilteredPerceptsMO;
    }

    public List<AbstractObject> getInputPercepts() {
        return inputPercepts;
    }

    public void setInputPercepts(List<AbstractObject> inputPercepts) {
        this.inputPercepts = inputPercepts;
    }

    public AbstractObject getOutputFilteredPercepts() {
        return outputFilteredPercepts;
    }

    public void setOutputFilteredPercepts(AbstractObject outputFilteredPercepts) {
        this.outputFilteredPercepts = outputFilteredPercepts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getPerceptualCodeletsIds() {
        return perceptualCodeletsIds;
    }

    public void setPerceptualCodeletsIds(List<String> perceptualCodeletsIds) {
        this.perceptualCodeletsIds = perceptualCodeletsIds;
    }
}
