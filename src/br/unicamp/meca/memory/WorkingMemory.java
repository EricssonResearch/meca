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

package br.unicamp.meca.memory;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;

public class WorkingMemory  {

    public static final String WORKING_MEMORY_INPUT = "WORKING_MEMORY_INPUT";

	private String id;
	private Memory cueMemory;
	private Memory plansMemory;
	private Memory episodicRecallMemory;
	private Memory globalWorkspaceMemory;
	private Memory executivePlanMemory;

	private Memory imaginationsMemory;
	private Memory goalsMemory;
	private Memory currentPerceptionMemory;
	private Memory predictedSituationMemory;

	private Memory nextActionMemory;

	public WorkingMemory(String id){
		setId(id);

		setPlansMemory(new MemoryContainer());
        setEpisodicRecallMemory(new MemoryContainer());
        setGlobalWorkspaceMemory(new MemoryContainer());
        setImaginationsMemory(new MemoryContainer());
        setGoalsMemory(new MemoryContainer());

		setCueMemory(new MemoryObject());
		setNextActionMemory(new MemoryObject());
		setExecutivePlanMemory(new MemoryObject());
		setCurrentPerceptionMemory(new MemoryObject());
		setPredictedSituationMemory(new MemoryObject());
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public Memory getCueMemory() {
        return cueMemory;
    }

    public void setCueMemory(Memory cueMemory) {
        this.cueMemory = cueMemory;
    }

    public Memory getPlansMemory() {
        return plansMemory;
    }

    public void setPlansMemory(Memory plansMemory) {
        this.plansMemory = plansMemory;
    }

    public Memory getEpisodicRecallMemory() {
        return episodicRecallMemory;
    }

    public void setEpisodicRecallMemory(Memory episodicRecallMemory) {
        this.episodicRecallMemory = episodicRecallMemory;
    }

    public Memory getGlobalWorkspaceMemory() {
        return globalWorkspaceMemory;
    }

    public void setGlobalWorkspaceMemory(Memory globalWorkspaceMemory) {
        this.globalWorkspaceMemory = globalWorkspaceMemory;
    }

    public Memory getExecutivePlanMemory() {
        return executivePlanMemory;
    }

    public void setExecutivePlanMemory(Memory executivePlanMemory) {
        this.executivePlanMemory = executivePlanMemory;
    }

    public Memory getImaginationsMemory() {
        return imaginationsMemory;
    }

    public void setImaginationsMemory(Memory imaginationsMemory) {
        this.imaginationsMemory = imaginationsMemory;
    }

    public Memory getGoalsMemory() {
        return goalsMemory;
    }

    public void setGoalsMemory(Memory goalsMemory) {
        this.goalsMemory = goalsMemory;
    }

    public Memory getCurrentPerceptionMemory() {
        return currentPerceptionMemory;
    }

    public void setCurrentPerceptionMemory(Memory currentPerceptionMemory) {
        this.currentPerceptionMemory = currentPerceptionMemory;
    }

    public Memory getPredictedSituationMemory() {
        return predictedSituationMemory;
    }

    public void setPredictedSituationMemory(Memory predictedSituationMemory) {
        this.predictedSituationMemory = predictedSituationMemory;
    }

    public Memory getNextActionMemory() {
        return nextActionMemory;
    }

    public void setNextActionMemory(Memory nextActionMemory) {
        this.nextActionMemory = nextActionMemory;
    }
}
