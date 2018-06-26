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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.motivational.Goal;
import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.meca.memory.WorkingMemory;

/**
 * This class represents MECA's Soar Codelet. This is a binding for CST's JSoar
 * Codelet.
 * 
 * @author A. L. O. Paraense
 * @author E. Froes
 * @author W. Gibaut
 * @see br.unicamp.cst.bindings.soar.JSoarCodelet
 */
public abstract class SoarCodelet extends br.unicamp.cst.bindings.soar.JSoarCodelet {

	private String id;

	private String pathToCommands;

	private WorkingMemory workingMemory;

	private List<Object> rawPlan = null;

	private Memory workingMemoryOutputMO;

	private Memory workingMemoryInputMO;

	private String _agentName;

	private File _productionPath;

	private boolean startSOARDebugger;

	/**
	 * Creates the MECA Soar Codelet.
	 * 
	 * @param id
	 *            the id of theSoar Codelet. Must be unique per Soar Codelet.
	 */
	public SoarCodelet(String id) {

		this.setId(id);
		setName(id);

		rawPlan = new ArrayList<>();
		setPathToCommands("");
	}

	/**
	 * Creates the MECA Soar Codelet.
	 * 
	 * @param id
	 *            the id of theSoar Codelet. Must be unique per Soar Codelet.
	 * @param path_to_commands
	 *            the path to commands
	 * @param _agentName
	 *            the agent name
	 * @param _productionPath
	 *            the production path
	 * @param startSOARDebugger
	 *            if should start SOAR debugger
	 */
	public SoarCodelet(String id, String path_to_commands, String _agentName, File _productionPath,
			Boolean startSOARDebugger) {

		this.setId(id);
		setName(id);
		this._agentName = _agentName;
		this._productionPath = _productionPath;
		this.startSOARDebugger = startSOARDebugger;

		rawPlan = new ArrayList<>();
		setPathToCommands(path_to_commands);
		initSoarPlugin(_agentName, _productionPath, startSOARDebugger);
	}

	@Override
	public void accessMemoryObjects() {

		if (workingMemoryInputMO == null) {
			workingMemoryInputMO = this.getInput(WorkingMemory.WORKING_MEMORY_INPUT);
			workingMemory = (WorkingMemory) workingMemoryInputMO.getI();
		}

		if (workingMemoryOutputMO == null)
			workingMemoryOutputMO = this.getOutput(id);
	}

	@Override
	public void proc() {

		AbstractObject il = processWorkingMemoryInput();
		if (il.getCompositeList().size() == 0)
			return;

		setInputLinkAO(il);

		if (getDebugState() == 0)
			getJsoar().step();

		ArrayList<Object> commandList = getOutputInObject(getPathToCommands());

		if (commandList != null) {
			Collections.addAll(rawPlan, commandList);

			workingMemory.getPlansMemory().setI(rawPlan);

			workingMemoryOutputMO.setI(workingMemory);

			fromPlanToAction();

		} else {

			System.out.println("Error in SoarCodelet proc() ... commandList is null ");
		}

	}

	/**
	 * Goes from plan to action
	 */
	public abstract void fromPlanToAction();

	/**
	 * Processes the working memory input.
	 * 
	 * @return an abstract object represent an Input link.
	 */
	public AbstractObject processWorkingMemoryInput() {

		AbstractObject il = new AbstractObject("InputLink");

		AbstractObject currentPerceptionWO = (AbstractObject) workingMemory.getCurrentPerceptionMemory().getI() != null
				? convertToAbstractObject((AbstractObject) workingMemory.getCurrentPerceptionMemory().getI(),
						"CURRENT_PERCEPTION")
				: null;

		AbstractObject imaginationsWO = (List<AbstractObject>) workingMemory.getImaginationsMemory().getI() != null
				? convertToAbstractObject((List<AbstractObject>) workingMemory.getImaginationsMemory().getI(),
						"IMAGINATION")
				: null;

		AbstractObject goalsWO = (List<Goal>) workingMemory.getGoalsMemory().getI() != null
				? goalToAbstractObject((List<Goal>) workingMemory.getGoalsMemory().getI()) : null;

		AbstractObject globalWO = (List<Memory>) workingMemory.getGlobalWorkspaceMemory().getI() != null
				? globalWorkspaceToAbstractObject((List<Memory>) workingMemory.getGlobalWorkspaceMemory().getI())
				: null;

		AbstractObject epRecallWO = (List<Memory>) workingMemory.getEpisodicRecallMemory().getI() != null
				? epRecallToAbstractObject((List<Memory>) workingMemory.getEpisodicRecallMemory().getI()) : null;

		if (currentPerceptionWO != null)
			il.addCompositePart(currentPerceptionWO);

		if (imaginationsWO != null)
			il.addCompositePart(imaginationsWO);

		if (goalsWO != null)
			il.addCompositePart(goalsWO);

		if (globalWO != null)
			il.addCompositePart(globalWO);

		if (epRecallWO != null)
			il.addCompositePart(epRecallWO);

		return il;
	}

	/**
	 * Converts to abstract object.
	 * 
	 * @param abstractObject
	 *            the input abstract object.
	 * @param nodeName
	 *            the node name.
	 * @return the abstract object.
	 */
	public AbstractObject convertToAbstractObject(AbstractObject abstractObject, String nodeName) {

		AbstractObject abs = new AbstractObject(nodeName);

		abs.addCompositePart(abstractObject);

		return abs;
	}

	/**
	 * Converts to abstract object.
	 * 
	 * @param abstractObjects
	 *            the list of input abstract objects.
	 * @param nodeNameTemplate
	 *            the node name template.
	 * @return the abstract object.
	 */
	public AbstractObject convertToAbstractObject(List<AbstractObject> abstractObjects, String nodeNameTemplate) {

		AbstractObject configs = new AbstractObject(abstractObjects.toString());

		for (AbstractObject abs : abstractObjects) {
			configs.addAggregatePart(convertToAbstractObject(abs, nodeNameTemplate));
		}
		return configs;
	}

	/**
	 * Creates the Abstract Object representing the Goals.
	 * 
	 * @param goals
	 *            the list of goals.
	 * @return the Abstract Object representing the Goals.
	 */
	public AbstractObject goalToAbstractObject(List<Goal> goals) {

		AbstractObject go = new AbstractObject("Goals");

		for (Goal goal : goals) {

			AbstractObject temp = convertToAbstractObject(goal.getGoalAbstractObjects(), "GOAL");
			temp.addProperty(new Property(goal.getId()));
			go.addAggregatePart(temp);
		}
		return go;
	}

	/**
	 * Creates an Abstract Object representing the global workspace.
	 * 
	 * @param global
	 *            the list of memories
	 * @return the abstract object representing global workspace
	 */
	public AbstractObject globalWorkspaceToAbstractObject(List<Memory> global) {

		List<AbstractObject> globalAbstractObjects = null;

		List<String> globalStrings = null;

		for (Memory mem : global) {

			if (isAbstractObject(mem.getI())) {

				globalAbstractObjects.add((AbstractObject) mem.getI());
			}
		}

		for (Memory mem : global) {

			if (isString(mem.getI())) {

				globalStrings.add((String) mem.getI());
			}
		}

		AbstractObject gAbs = convertToAbstractObject(globalAbstractObjects, "GLOBAL_WORKSPACE");

		for (String st : globalStrings) {

			gAbs.addAggregatePart(new AbstractObject(st));
		}

		return gAbs;
	}

	/**
	 * Creates an Abstract Object representing an episodic recall.
	 * 
	 * @param episodicRecall
	 *            the list of memories representing the episodic recall.
	 * @return the Abstract Object representing an episodic recall.
	 */
	public AbstractObject epRecallToAbstractObject(List<Memory> episodicRecall) {

		List<AbstractObject> epConfigurations = null;

		for (Memory mem : episodicRecall) {

			if (isAbstractObject(mem.getI())) {

				epConfigurations.add((AbstractObject) mem.getI());
			}
		}

		AbstractObject gConf = convertToAbstractObject(epConfigurations, "EPISODIC_RECALL_MEMORY");

		return gConf;
	}

	/**
	 * Tells if the object is an abstract object.
	 * 
	 * @param obj
	 *            the object to be tested
	 * @return true if the obj is an abstract object.
	 */
	public boolean isAbstractObject(Object obj) {

		if (obj.getClass() == AbstractObject.class)
			return true;
		else
			return false;
	}

	/**
	 * Tells if the object is a Java String.
	 * 
	 * @param obj
	 *            the object to be tested
	 * @return true if the obj is a Java String.
	 */
	public boolean isString(Object obj) {

		if (obj.getClass() == String.class)
			return true;
		else
			return false;
	}

	/**
	 * Gets the Soar Codelet id.
	 * 
	 * @return the Soar Codelet id.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the Soar Codelet id.
	 * 
	 * @param id
	 *            the Soar codelet id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Working memory
	 * 
	 * @return the working memoryy.
	 */
	public WorkingMemory getWorkingMemory() {
		return this.workingMemory;
	}

	/**
	 * Gets the path to commands.
	 * 
	 * @return the path to commands.
	 */
	public String getPathToCommands() {
		return this.pathToCommands;
	}

	/**
	 * Sets the path to commands.
	 * 
	 * @param path
	 *            the path to commands to set.
	 */
	public void setPathToCommands(String path) {
		this.pathToCommands = path;
	}
}
