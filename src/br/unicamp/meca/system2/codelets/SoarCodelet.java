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


public abstract class SoarCodelet extends br.unicamp.cst.bindings.soar.JSoarCodelet {

    private String id;

    private String pathToCommands;
    //private String pathToRules;

    private WorkingMemory workingMemory;

    private List<Object> rawPlan = null;

    private Memory workingMemoryOutputMO;

    private Memory workingMemoryInputMO;

    private String _agentName;

    private File _productionPath;

    private boolean startSOARDebugger;

    public SoarCodelet(String id) {

        this.setId(id);
        setName(id);
        //this.episodicRecallMemory = workingMemory.get

        rawPlan = new ArrayList<>();
        setPathToCommands("");
        //setPathToRules("");
    }

    public SoarCodelet(String id, String path_to_commands, String _agentName, File _productionPath, Boolean startSOARDebugger) {

        this.setId(id);
        setName(id);
        this._agentName = _agentName;
        this._productionPath = _productionPath;
        this.startSOARDebugger = startSOARDebugger;
        //this.episodicRecallMemory = workingMemory.get

        rawPlan = new ArrayList<>();
        setPathToCommands(path_to_commands);
        initSoarPlugin(_agentName, _productionPath, startSOARDebugger);
        //setPathToRules(rules);
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

        setInputLinkAO(il);

        if (getDebugState() == 0)
            getJsoar().step();

        ArrayList<Object> commandList = getOutputInObject(getPathToCommands());

        if(commandList != null) {
            Collections.addAll(rawPlan, commandList);

            workingMemory.getPlansMemory().setI(rawPlan);

            /*plan.setI(rawPlan);
		    plansMemory.add(plan);
		    workingMemoryOut.setPlansMemory(plansMemory);*/

            workingMemoryOutputMO.setI(workingMemory);

            //n sei se eh a melhor escolha
            fromPlanToAction();
        }

    }

    public abstract void fromPlanToAction();


    //mudar Nome
    //varre as memorias e cria os WO
    public AbstractObject processWorkingMemoryInput() {

        AbstractObject il = new AbstractObject("InputLink");

        AbstractObject currentPerceptionWO = (AbstractObject) workingMemory.getCurrentPerceptionMemory().getI() != null ? convertToAbstractObject((AbstractObject) workingMemory.getCurrentPerceptionMemory().getI(), "CURRENT_PERCEPTION") : null;
        //WorldObject predictedSituationWO = convertToAbstractObject(predictedSituation);

        AbstractObject imaginationsWO = (List<AbstractObject>) workingMemory.getImaginationsMemory().getI() != null ? convertToAbstractObject((List<AbstractObject>) workingMemory.getImaginationsMemory().getI(), "IMAGINATION") : null;

        AbstractObject goalsWO = (List<Goal>) workingMemory.getGoalsMemory().getI() != null ? goalToAbstractObject((List<Goal>) workingMemory.getGoalsMemory().getI()) : null;

        AbstractObject globalWO = (List<Memory>) workingMemory.getGlobalWorkspaceMemory().getI() != null ? globalWorkspaceToAbstractObject((List<Memory>) workingMemory.getGlobalWorkspaceMemory().getI()) : null;

        AbstractObject epRecallWO = (List<Memory>) workingMemory.getEpisodicRecallMemory().getI() != null ? epRecallToAbstractObject((List<Memory>) workingMemory.getEpisodicRecallMemory().getI()) : null;
        //if(){}

        if (currentPerceptionWO != null)
            il.addCompositePart(currentPerceptionWO);
        //il.addPart(predictedSituationWO);
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


    public AbstractObject convertToAbstractObject(AbstractObject abstractObject, String nodeName) {

        //List<AbstractObject> compositeParts = abstractObject.getCompositeParts();
        //List<AbstractObject> aggregateParts = abstractObject.getAggregateParts();

        //AbstractObject il = new AbstractObject("InputLink");
        AbstractObject abs = new AbstractObject(nodeName);
        //AbstractObject abs = new AbstractObject("ABSTRACT_OBJECT");
        //AbstractObject comp = new AbstractObject("COMPOSITE_PARTS");
        //AbstractObject aggr = new AbstractObject("AGGREGATE_PARTS");

        //il.addAggregatePart(abs);
        //abs.addAggregatePart(comp);
        //abs.addAggregatePart(aggr);

        //abs.setProperties(abstractObject.getProperties());
        //comp.setAggregatePart(compositeParts);
        //aggr.setAggregatePart(aggregateParts);
        //for(AbstractObject part : compositeParts){
        //    abs.addCompositePart(part);
        //}
        abs.addCompositePart(abstractObject);

        //		AbstractObject appName = new AbstractObject("Name");
        //		appName.addProperty(new Property(ap.getId()));
        //		app.addAggregatePart(appName);
        //
        //		AbstractObject appEval = new AbstractObject("Evaluation");
        //		appEval.addProperty(new Property("Evaluation", new QualityDimension("", ap.getEvaluation())));
        //		app.addAggregatePart(appEval);
        //
        //		AbstractObject appCState = new AbstractObject("CurrentState");
        //		appCState.addProperty(new Property(ap.getCurrentState()));
        //		app.addAggregatePart(appCState);

        //call this method till the whole configuration tree be ready. May be problematic

		/*WorldObject appCConf = new WorldObject("CurrentConfiguration");
        appCConf.addPart(convertToAbstractObject(ap.getCurrentConfiguration())); //TODO - fix this due to changes in Appraisal

        WorldObject appCConf = new WorldObject("CurrentConfiguration");
        appCConf.addPart(convertToAbstractObject(ap.getCurrentConfiguration()));
        app.addPart(appCConf);

        //same as above 
        WorldObject appPSit = new WorldObject("PredictedSituation");

        appPSit.addPart(convertToAbstractObject(ap.getPredictedSituation())); //TODO - fix this due to changes in Appraisal
        app.addPart(appPSit);*/

        //return il;
        return abs;
    }

    public AbstractObject convertToAbstractObject(List<AbstractObject> abstractObjects, String nodeNameTemplate) {

        AbstractObject configs = new AbstractObject(abstractObjects.toString());

        for (AbstractObject abs : abstractObjects) {
            configs.addAggregatePart(convertToAbstractObject(abs, nodeNameTemplate));
        }
        return configs;
    }

    public AbstractObject goalToAbstractObject(List<Goal> goals) {

        AbstractObject go = new AbstractObject("Goals");

        for (Goal goal : goals) {

            AbstractObject temp = convertToAbstractObject(goal.getGoalAbstractObjects(), "GOAL");
            temp.addProperty(new Property(goal.getId()));
            go.addAggregatePart(temp);
        }
        return go;
    }

    //ignores anything that is not a WO, Conf. or String
    public AbstractObject globalWorkspaceToAbstractObject(List<Memory> global) {

        //AbstractObject ws = new AbstractObject("global Workspace");

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
        //WorldObject gWO =

        //ws.addAggregatePart(gAbs);

        for (String st : globalStrings) {

            //ws.addAggregatePart(new AbstractObject(st));
            gAbs.addAggregatePart(new AbstractObject(st));
        }

        //return ws;
        return gAbs;
    }

    public AbstractObject epRecallToAbstractObject(List<Memory> episodicRecall) {

        //AbstractObject epR = new AbstractObject("episodicRecallMemory");

        List<AbstractObject> epConfigurations = null;

        for (Memory mem : episodicRecall) {

            if (isAbstractObject(mem.getI())) {

                epConfigurations.add((AbstractObject) mem.getI());
            }
        }

        AbstractObject gConf = convertToAbstractObject(epConfigurations, "EPISODIC_RECALL_MEMORY");
        //WorldObject gWO =

        //epR.addAggregatePart(gConf);

        //return epR;
        return gConf;
    }

    public boolean isAbstractObject(Object obj) {

        if (obj.getClass() == AbstractObject.class)
            return true;
        else
            return false;
    }

    public boolean isString(Object obj) {

        if (obj.getClass() == String.class)
            return true;
        else
            return false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public WorkingMemory getWorkingMemory() {
        return this.workingMemory;
    }

    public String getPathToCommands() {
        return this.pathToCommands;
    }

    public void setPathToCommands(String path) {
        this.pathToCommands = path;
    }

    //public void setPathToRules(String path){
    //    this.pathToRules = path;
    //}
}
