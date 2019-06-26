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
package br.unicamp.meca.memory;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryContainer;
import br.unicamp.cst.core.entities.MemoryObject;

/**
 * This class represents the MECA's Working Memory.
 * <p>
 * According to theories from cognitive modeling, the Working Memory is a
 * volatile kind of memory used during perception, reasoning, planning and other
 * cognitive functions. In studies with human beings, its capacity in time and
 * space is found to be very short, ranging from 4 to 9 items, and periods up to
 * a few dozen seconds (Baddeley et al., 1975; Cowan, 2001; Miller, 1956).
 * According to Baddeley, (1997, 2000), there are at least three subsystems
 * involved in the implementation of a Working Memory, the Visuo-spatial
 * Sketchpad, the Phonological Loop and the Episodic Buffer, coordinated by a
 * Central Executive which intermediates between them. Regarding brain
 * localization, the regions related to working memory processes are very
 * overlapping, however recent researches point the pre-frontal cortex and basal
 * ganglia as being crucial (Braver et al., 1997; M. J. Frank et al., 2001;
 * McNab and Klingberg, 2008).
 * <p>
 * In the current MECA implementation, we are not relying in this structure. The
 * Episodic Buffer is split from the Working Memory and we don’t have neither
 * the Visuo-spatial Sketchpad neither the Phonologic Loop. In future
 * implementations of MECA, this might change, though. In its current
 * implementation, the Working Memory is a repository of Symbols, which are
 * grounded on Percepts from the Perceptual Memory, and which are used to plan
 * the future, through a process of Computational Imagination.
 * <p>
 * The Working Memory is internally split into many sub-memories:
 * <p>
 * - Current Perception Sub-memory;
 * <p>
 * - Cue Memory;
 * <p>
 * - Episodic Recall Memory;
 * <p>
 * - Imagination Sub-memory;
 * <p>
 * - Global Workspace;
 * <p>
 * - Predicted Situation Sub-memory;
 * <p>
 * - Goals Sub-memory;
 * <p>
 * - Plans Sub-memory;
 * <p>
 * - Executive Plan Sub-memory;
 * <p>
 * - Next Action Sub-memory.
 * <p>
 * References:
 * <p>
 * (Baddeley et al. 1975) Baddeley, A., Thomson, N., and Buchanan, M. Word
 * length and the structure of short-term memory. In: Journal Of Verbal Learning
 * And Verbal Behavior 14.6 (1975), pp. 575-589.
 * <p>
 * (Cowan 2001) Cowan, N. The magical number 4 in short-term memory: a
 * reconsideration of mental storage capacity. In: Behavioral and Brain Sciences
 * 24 (2001), pp. 87-114.
 * <p>
 * (Miller 1956) Miller, G. The Magical Number 7, Plus or Minus Two: Some Limits
 * on Our Capacity for Processing Information. In: Psychological Review 63
 * (1956)
 * <p>
 * (Baddeley 1997) Baddeley, A. Human Memory - Theory and Practice. Revised
 * Edition. Psychology Press, Taylor and Francis Group, 1997.
 * <p>
 * (Baddeley 2000) Baddeley, A. The episodic buffer: a new component of working
 * memory? In: Trends in Cognitive Sciences 4.11 (2000), pp. 417-423.
 * <p>
 * (Braver et al. 1997) Braver, T. S. et al. A parametric study of prefrontal
 * cortex involvement in human working memory. In: NeuroImage 1 (1997), pp.
 * 49-62.
 * <p>
 * (M. J. Frank et al. 2001) Frank, M. J., Loughry, B., and OReilly, R. C.
 * Interactions between frontal cortex and basal ganglia in working memory: a
 * computational model. In: Cognitive, Affective and Behavioral Neuroscience 1.2
 * (2001), pp. 137-60.
 * <p>
 * (McNab et al. 2008) McNab, F. and Klingberg, T. Prefrontal cortex and basal
 * ganglia control access to working memory. In: Nature Neuroscience 11.1
 * (2008), pp. 103-107.
 * 
 * @author A. L. O. Paraense
 * @author E. Froes
 * @author W. Gibaut
 */
public class WorkingMemory {

	/**
	 * The Working Memory Input constant.
	 */
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

	/**
	 * Creates the MECA's Working Memory.
	 * 
	 * @param id
	 *            the Working Memory Id. Should be unique per Working Memory.
	 */
	public WorkingMemory(String id) {
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

	/**
	 * Gets this Working Memory id.
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the working memory id.
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Cue Memory.
	 * 
	 * @return the Cue Memory.
	 */
	public Memory getCueMemory() {
		return cueMemory;
	}

	/**
	 * Sets the Cue Memory.
	 * 
	 * @param cueMemory
	 *            the Cue memory to set.
	 */
	public void setCueMemory(Memory cueMemory) {
		this.cueMemory = cueMemory;
	}

	/**
	 * Gets the Plans Memory.
	 * 
	 * @return the Plans Memory.
	 */
	public Memory getPlansMemory() {
		return plansMemory;
	}

	/**
	 * Sets the Plans Memory.
	 * 
	 * @param plansMemory
	 *            the plans Memory to set.
	 */
	public void setPlansMemory(Memory plansMemory) {
		this.plansMemory = plansMemory;
	}

	/**
	 * Gets the Episodic Recall Memory.
	 * 
	 * @return the Episodic Recall Memory.
	 */
	public Memory getEpisodicRecallMemory() {
		return episodicRecallMemory;
	}

	/**
	 * Sets the Episodic Recall Memory
	 * 
	 * @param episodicRecallMemory
	 *            the Episodic Recall Memory to set.
	 */
	public void setEpisodicRecallMemory(Memory episodicRecallMemory) {
		this.episodicRecallMemory = episodicRecallMemory;
	}

	/**
	 * Gets the Global Workspace Memory
	 * 
	 * @return the Global Workspace Memory.
	 */
	public Memory getGlobalWorkspaceMemory() {
		return globalWorkspaceMemory;
	}

	/**
	 * Sets the Global Workspace Memory
	 * 
	 * @param globalWorkspaceMemory
	 *            the Global Workspace memory to set.
	 */
	public void setGlobalWorkspaceMemory(Memory globalWorkspaceMemory) {
		this.globalWorkspaceMemory = globalWorkspaceMemory;
	}

	/**
	 * Gets the Executive Plan Memory.
	 * 
	 * @return the Executive Plan Memory.
	 */
	public Memory getExecutivePlanMemory() {
		return executivePlanMemory;
	}

	/**
	 * Sets the Executive Plan memory.
	 * 
	 * @param executivePlanMemory
	 *            the Executive Plan Memory to set.
	 */
	public void setExecutivePlanMemory(Memory executivePlanMemory) {
		this.executivePlanMemory = executivePlanMemory;
	}

	/**
	 * Gets the Imaginations Memory.
	 * 
	 * @return the Imaginations Memory.
	 */
	public Memory getImaginationsMemory() {
		return imaginationsMemory;
	}

	/**
	 * Sets the Imaginations memory.
	 * 
	 * @param imaginationsMemory
	 *            the imaginations memory to set.
	 */
	public void setImaginationsMemory(Memory imaginationsMemory) {
		this.imaginationsMemory = imaginationsMemory;
	}

	/**
	 * Gets the Goals Memory.
	 * 
	 * @return the Goals Memory.
	 */
	public Memory getGoalsMemory() {
		return goalsMemory;
	}

	/**
	 * Sets the Goals Memory.
	 * 
	 * @param goalsMemory
	 *            the Goals Memory to set.
	 */
	public void setGoalsMemory(Memory goalsMemory) {
		this.goalsMemory = goalsMemory;
	}

	/**
	 * Gets the Current Perception Memory.
	 * 
	 * @return the current perception memory.
	 */
	public Memory getCurrentPerceptionMemory() {
		return currentPerceptionMemory;
	}

	/**
	 * Sets the current perception memory.
	 * 
	 * @param currentPerceptionMemory
	 *            the current perception memory to set.
	 */
	public void setCurrentPerceptionMemory(Memory currentPerceptionMemory) {
		this.currentPerceptionMemory = currentPerceptionMemory;
	}

	/**
	 * Gets the predicted situation memory.
	 * 
	 * @return the predicte situation memory.
	 */
	public Memory getPredictedSituationMemory() {
		return predictedSituationMemory;
	}

	/**
	 * Sets the predicted situation memory.
	 * 
	 * @param predictedSituationMemory
	 *            the predicted situation memory to set.
	 */
	public void setPredictedSituationMemory(Memory predictedSituationMemory) {
		this.predictedSituationMemory = predictedSituationMemory;
	}

	/**
	 * Gets the next action memory.
	 * 
	 * @return the next action memory.
	 */
	public Memory getNextActionMemory() {
		return nextActionMemory;
	}

	/**
	 * Sets the next action memory.
	 * 
	 * @param nextActionMemory
	 *            the next action memory to set.
	 */
	public void setNextActionMemory(Memory nextActionMemory) {
		this.nextActionMemory = nextActionMemory;
	}
}
