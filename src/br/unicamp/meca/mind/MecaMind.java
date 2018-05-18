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
package br.unicamp.meca.mind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.unicamp.cst.bindings.soar.PlansSubsystemModule;
import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import br.unicamp.meca.memory.WorkingMemory;
import br.unicamp.meca.system1.codelets.AttentionCodelet;
import br.unicamp.meca.system1.codelets.EmotionalCodelet;
import br.unicamp.meca.system1.codelets.MoodCodelet;
import br.unicamp.meca.system1.codelets.MotivationalBehavioralCodelet;
import br.unicamp.meca.system1.codelets.MotivationalCodelet;
import br.unicamp.meca.system1.codelets.MotorCodelet;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;
import br.unicamp.meca.system1.codelets.RandomBehavioralCodelet;
import br.unicamp.meca.system1.codelets.ReactiveBehavioralCodelet;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import br.unicamp.meca.system2.codelets.AppraisalCodelet;
import br.unicamp.meca.system2.codelets.ConsciousnessCodelet;
import br.unicamp.meca.system2.codelets.EpisodicLearningCodelet;
import br.unicamp.meca.system2.codelets.EpisodicRetrievalCodelet;
import br.unicamp.meca.system2.codelets.ExpectationCodelet;
import br.unicamp.meca.system2.codelets.GoalCodelet;
import br.unicamp.meca.system2.codelets.SoarCodelet;

/**
 *         <p>
 *         The mind of the Meca Agent. This is the main class to be used
 *         by any MECA user.
 */
public class MecaMind extends Mind {

	/*
     * System 1
	 */

    private List<SensoryCodelet> sensoryCodelets;

    private List<PerceptualCodelet> perceptualCodelets;

    private List<MoodCodelet> moodCodelets;

    private List<MotivationalCodelet> motivationalCodelets;

    private AttentionCodelet attentionCodeletSystem1;

    private List<EmotionalCodelet> emotionalCodelets;

    private List<RandomBehavioralCodelet> randomBehavioralCodelets;

    private List<ReactiveBehavioralCodelet> reactiveBehavioralCodelets;

    private List<MotivationalBehavioralCodelet> motivationalBehavioralCodelets;

    private List<MotorCodelet> motorCodelets;

	/*
	 * System 2
	 */

    private List<br.unicamp.meca.system2.codelets.AttentionCodelet> attentionCodeletsSystem2;

    private EpisodicLearningCodelet episodicLearningCodelet;

    private EpisodicRetrievalCodelet episodicRetrievalCodelet;

    private ExpectationCodelet expectationCodelet;

    private ConsciousnessCodelet consciousnessCodelet;

    private SoarCodelet soarCodelet;

    private GoalCodelet goalCodelet;

    private AppraisalCodelet appraisalCodelet;


    /*
     * Working Memory
     */
    private WorkingMemory workingMemory;

    /*
     * Mind's Id
     */
    private String id;

    /**
     *
     */
    public MecaMind() {
        setId(UUID.randomUUID().toString());
        setWorkingMemory(new WorkingMemory(getId()));
    }

    public MecaMind(String id) {
        setId(id);
        setWorkingMemory(new WorkingMemory(getId()));
    }


    /**
     *
     */
    public void mountMecaMind() {


        mountSensoryCodelets();

        mountPerceptualCodelets();

        mountMotorCodelets();


        mountAttentionCodelets();

        mountWorkingMemory();

        mountSoarCodelet();


        mountRandomBehavioralCodelets();

        mountReactiveBehavioralCodelets();

        mountMotivationalCodelets();

        mountMotivationalBehavioralCodelets();

        mountModules();

    }

    private void mountModules(){


        if(getMotivationalCodelets() != null){
            if(getMotivationalCodelets().size() > 0){
                List<? extends br.unicamp.cst.motivational.MotivationalCodelet> mtcodelets = getMotivationalCodelets();
                getMotivationalSubsystemModule().setMotivationalCodelets((List<br.unicamp.cst.motivational.MotivationalCodelet>) mtcodelets);
            }

        }

        if(getSoarCodelet() != null){
            getPlansSubsystemModule().setjSoarCodelet(getSoarCodelet());
        }

    }

    private void mountPerceptualCodelets() {
        if (perceptualCodelets != null) {
            for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
                if (perceptualCodelet != null && perceptualCodelet.getId() != null) {

                    insertCodelet(perceptualCodelet);
					/*
					 * Inputs
					 */
                    if (sensoryCodelets != null) {
                        for (SensoryCodelet sensoryCodelet : sensoryCodelets) {
                            if (sensoryCodelet != null && sensoryCodelet.getId() != null) {
                                ArrayList<String> sensoryCodeletsIds = perceptualCodelet.getSensoryCodeletsIds();
                                if (sensoryCodeletsIds != null) {
                                    for (String sensoryCodeletId : sensoryCodeletsIds) {
                                        if (sensoryCodeletId != null && sensoryCodeletId.equalsIgnoreCase(sensoryCodelet.getId())) {
                                            perceptualCodelet.addInputs(sensoryCodelet.getOutputs());
                                        }
                                    }
                                }
                            }
                        }
                    }
					/*
					 * Output
					 */
                    MemoryObject perceptualMemory = createMemoryObject(perceptualCodelet.getId());
                    perceptualCodelet.addOutput(perceptualMemory);

                }
            }
        }
    }

    private void mountSensoryCodelets() {
        if (sensoryCodelets != null) {

            for (SensoryCodelet sensoryCodelet : sensoryCodelets) {
                if (sensoryCodelet != null && sensoryCodelet.getId() != null) {

                    insertCodelet(sensoryCodelet);
					/*
					 * Input
					 */
                    MemoryObject sensoryMemory = createMemoryObject(sensoryCodelet.getId());
                    sensoryCodelet.addOutput(sensoryMemory);

                }
            }
        }
    }

    private void mountMotivationalCodelets() {
        if (getMotivationalCodelets() != null) {
            for (MotivationalCodelet motivationalCodelet : getMotivationalCodelets()) {


				/*
				 * Input Sensors
				 */
                if (motivationalCodelet.getSensoryCodeletsIds() != null) {
                    List<String> sensoryIds = motivationalCodelet.getSensoryCodeletsIds();
                    for (String sensoryId : sensoryIds) {
                        if (sensoryCodelets != null) {
                            for (SensoryCodelet sensoryCodelet : sensoryCodelets) {
                                if (sensoryCodelet.getId().equals(sensoryId)) {
                                    motivationalCodelet.addInputs(sensoryCodelet.getOutputs());

                                }
                            }
                        }
                    }
                }

				/*
				 * Input Drives
				 */

                if (motivationalCodelet.getMotivationalCodeletsIds() != null) {
                    HashMap<String, Double> motivationalCodeletsIds = motivationalCodelet.getMotivationalCodeletsIds();
                    for (Map.Entry<String, Double> motivationalCodeletId : motivationalCodeletsIds.entrySet()) {

                        for (MotivationalCodelet motivationalCodeletInput : getMotivationalCodelets()) {
                            if (motivationalCodeletInput.getId().equals(motivationalCodeletId.getKey())) {

                                HashMap<Memory, Double> driveRelevance = new HashMap<>();
                                driveRelevance.put(motivationalCodeletInput.getOutputDriveMO(), motivationalCodeletId.getValue());

                                motivationalCodelet.addInput(this.createMemoryObject(motivationalCodeletInput.getOutputDriveMO().getName(), driveRelevance));
                                //((HashMap<Memory, Double>)(inputDrives.getI())).put(motivationalCodeletInput.getOutputDriveMO(), motivationalCodeletId.getValue());
                            }
                        }
                    }
                }

				/*
				 * Output Drives
				 */
                MemoryObject outputDrive = this.createMemoryObject(motivationalCodelet.getId() + "_DRIVE_MO");
                motivationalCodelet.addOutput(outputDrive);
                //MemoryObject outputDrive = this.createMemoryObject(MotivationalCodelet.OUTPUT_DRIVE_MEMORY);
                //motivationalCodelet.addOutput(outputDrive);

                insertCodelet(motivationalCodelet);
            }
        }
    }

    private void mountMotivationalBehavioralCodelets() {
        if (getMotivationalBehavioralCodelets() != null) {
            for (MotivationalBehavioralCodelet motivationalBehavioralCodelet : getMotivationalBehavioralCodelets()) {
                if (motivationalBehavioralCodelet != null && motivationalBehavioralCodelet.getId() != null && motivationalBehavioralCodelet.getMotivationalCodeletsIds() != null && motivationalBehavioralCodelet.getMotorCodeletId() != null && motivationalBehavioralCodelet.getSoarCodeletId() != null) {


					/*
					 * Outputs
					 */
                    if (motorCodelets != null) {
                        for (MotorCodelet motorCodelet : motorCodelets) {
                            if (motorCodelet != null && motorCodelet.getId() != null) {
                                if (motorCodelet.getId().equalsIgnoreCase(motivationalBehavioralCodelet.getMotorCodeletId())) {
                                    motivationalBehavioralCodelet.addOutputs(motorCodelet.getInputs());
                                }
                            }
                        }
                    }
					/*
					 * Inputs
					 */
                    if (getMotivationalCodelets() != null) {
                        for (MotivationalCodelet motivationalCodelet : getMotivationalCodelets()) {
                            if (motivationalCodelet != null && motivationalCodelet.getId() != null) {
                                ArrayList<String> motivationalCodeletsIds = motivationalBehavioralCodelet.getMotivationalCodeletsIds();
                                if (motivationalCodeletsIds != null) {
                                    for (String motivationalCodeletId : motivationalCodeletsIds) {
                                        if (motivationalCodeletId != null && motivationalCodelet.getId().equalsIgnoreCase(motivationalCodeletId)) {
                                            motivationalBehavioralCodelet.addInputs(motivationalCodelet.getOutputs());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (soarCodelet != null && soarCodelet.getId() != null) {
                        if (soarCodelet.getId().equalsIgnoreCase(motivationalBehavioralCodelet.getSoarCodeletId())) {
                            motivationalBehavioralCodelet.addBroadcasts(soarCodelet.getOutputs());
                        }

                    }

                    insertCodelet(motivationalBehavioralCodelet);

                }
            }
        }
    }

    private void mountReactiveBehavioralCodelets() {
        if (getReactiveBehavioralCodelets() != null) {
            for (ReactiveBehavioralCodelet reactiveBehavioralCodelet : getReactiveBehavioralCodelets()) {
                if (reactiveBehavioralCodelet != null && reactiveBehavioralCodelet.getId() != null && reactiveBehavioralCodelet.getPerceptualCodeletsIds() != null && reactiveBehavioralCodelet.getMotorCodeletId() != null && reactiveBehavioralCodelet.getSoarCodeletId() != null) {

                    insertCodelet(reactiveBehavioralCodelet);
					/*
					 * Outputs
					 */
                    if (motorCodelets != null) {
                        for (MotorCodelet motorCodelet : motorCodelets) {
                            if (motorCodelet != null && motorCodelet.getId() != null) {
                                if (motorCodelet.getId().equalsIgnoreCase(reactiveBehavioralCodelet.getMotorCodeletId())) {
                                    reactiveBehavioralCodelet.addOutputs(motorCodelet.getInputs());
                                }
                            }
                        }
                    }
					/*
					 * Inputs
					 */
                    if (perceptualCodelets != null) {
                        for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {
                            if (perceptualCodelet != null && perceptualCodelet.getId() != null) {
                                ArrayList<String> perceptualCodeletsIds = reactiveBehavioralCodelet.getPerceptualCodeletsIds();
                                if (perceptualCodeletsIds != null) {
                                    for (String perceptualCodeletId : perceptualCodeletsIds) {
                                        if (perceptualCodeletId != null && perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
                                            reactiveBehavioralCodelet.addInputs(perceptualCodelet.getOutputs());
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (soarCodelet != null && soarCodelet.getId() != null) {
                        if (soarCodelet.getId().equalsIgnoreCase(reactiveBehavioralCodelet.getSoarCodeletId())) {
                            reactiveBehavioralCodelet.addBroadcasts(soarCodelet.getOutputs());
                        }

                    }

                }
            }
        }
    }

    private void mountRandomBehavioralCodelets() {
        if (getRandomBehavioralCodelets() != null) {
            for (RandomBehavioralCodelet randomBehavioralCodelet : getRandomBehavioralCodelets()) {
                if (randomBehavioralCodelet != null && randomBehavioralCodelet.getId() != null && randomBehavioralCodelet.getMotorCodeletId() != null && randomBehavioralCodelet.getSoarCodeletId() != null) {
                    insertCodelet(randomBehavioralCodelet);
					/*
					 * Outputs
					 */
                    if (motorCodelets != null) {
                        for (MotorCodelet motorCodelet : motorCodelets) {
                            if (motorCodelet != null && motorCodelet.getId() != null) {
                                if ((motorCodelet.getId()).equalsIgnoreCase(randomBehavioralCodelet.getMotorCodeletId())) {
                                    randomBehavioralCodelet.addOutputs(motorCodelet.getInputs());
                                }
                            }
                        }
                    }
					/*
					 * Inputs
					 */
                    if (soarCodelet != null && soarCodelet.getId() != null) {
                        if (soarCodelet.getId().equalsIgnoreCase(randomBehavioralCodelet.getSoarCodeletId())) {
                            randomBehavioralCodelet.addBroadcasts(soarCodelet.getOutputs());
                        }

                    }
                }
            }
        }
    }

    private void mountMotorCodelets() {
        if (motorCodelets != null) {
            for (MotorCodelet motorCodelet : motorCodelets) {
                if (motorCodelet != null && motorCodelet.getId() != null) {
                    insertCodelet(motorCodelet);
					/*
					 * Input
					 */
                    Memory motorMemoryContainer = createMemoryContainer(motorCodelet.getId());
                    motorCodelet.addInput(motorMemoryContainer);
                }
            }
        }
    }

    private void mountAttentionCodelets() {
        if (attentionCodeletSystem1 != null) {

            if (perceptualCodelets != null) {
                for (String inputPerceptualId : attentionCodeletSystem1.getPerceptualCodeletsIds()) {
                    for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {

                        if (inputPerceptualId.equals(perceptualCodelet.getId())) {
                            attentionCodeletSystem1.addInputs(perceptualCodelet.getOutputs());
                        }
                    }
                }
            }
            Memory attentionMemoryOutput = createMemoryObject(attentionCodeletSystem1.getId());
            attentionCodeletSystem1.addOutput(attentionMemoryOutput);
            attentionCodeletSystem1.setOutputFilteredPerceptsMO(attentionMemoryOutput);
            insertCodelet(attentionCodeletSystem1);
        }
    }

    private void mountSoarCodelet() {
        if (soarCodelet != null) {
            soarCodelet.addInput(createMemoryObject(WorkingMemory.WORKING_MEMORY_INPUT, getWorkingMemory()));
            soarCodelet.addOutput(createMemoryObject(soarCodelet.getId()));
            insertCodelet(soarCodelet);
        }

    }

    private void mountWorkingMemory() {
        if (getWorkingMemory() != null) {

            if (attentionCodeletSystem1 != null) {
                getWorkingMemory().setCurrentPerceptionMemory(attentionCodeletSystem1.getOutputFilteredPerceptsMO());
            }

        }
    }

    /**
     * @param sensoryCodelets the sensoryCodelets to set
     */
    public void setSensoryCodelets(List<SensoryCodelet> sensoryCodelets) {
        this.sensoryCodelets = sensoryCodelets;
    }


    /**
     * @param perceptualCodelets the perceptualCodelets to set
     */
    public void setPerceptualCodelets(List<PerceptualCodelet> perceptualCodelets) {
        this.perceptualCodelets = perceptualCodelets;
    }


    /**
     * @param moodCodelets the moodCodelets to set
     */
    public void setMoodCodelets(List<MoodCodelet> moodCodelets) {
        this.moodCodelets = moodCodelets;
    }


    /**
     * @param motivationalCodelets the motivationalCodelets to set
     */
    public void setMotivationalCodelets(List<MotivationalCodelet> motivationalCodelets) {
        this.motivationalCodelets = motivationalCodelets;
    }


    /**
     * @param attentionCodeletSystem1 the attentionCodeletSystem1 to set
     */
    public void setAttentionCodeletSystem1(AttentionCodelet attentionCodeletSystem1) {
        this.attentionCodeletSystem1 = attentionCodeletSystem1;
    }


    /**
     * @param emotionalCodelets the emotionalCodelets to set
     */
    public void setEmotionalCodelets(List<EmotionalCodelet> emotionalCodelets) {
        this.emotionalCodelets = emotionalCodelets;
    }


    /**
     * @param randomBehavioralCodelets the randomBehavioralCodelets to set
     */
    public void setRandomBehavioralCodelets(List<RandomBehavioralCodelet> randomBehavioralCodelets) {
        this.randomBehavioralCodelets = randomBehavioralCodelets;
    }


    /**
     * @param reactiveBehavioralCodelets the reactiveBehavioralCodelets to set
     */
    public void setReactiveBehavioralCodelets(List<ReactiveBehavioralCodelet> reactiveBehavioralCodelets) {
        this.reactiveBehavioralCodelets = reactiveBehavioralCodelets;
    }


    /**
     * @param motivationalBehavioralCodelets the motivationalBehavioralCodelets to set
     */
    public void setMotivationalBehavioralCodelets(List<MotivationalBehavioralCodelet> motivationalBehavioralCodelets) {
        this.motivationalBehavioralCodelets = motivationalBehavioralCodelets;
    }


    /**
     * @param motorCodelets the motorCodelets to set
     */
    public void setMotorCodelets(List<MotorCodelet> motorCodelets) {
        this.motorCodelets = motorCodelets;
    }


    /**
     * @param attentionCodeletsSystem2 the attentionCodeletsSystem2 to set
     */
    public void setAttentionCodeletsSystem2(List<br.unicamp.meca.system2.codelets.AttentionCodelet> attentionCodeletsSystem2) {
        this.attentionCodeletsSystem2 = attentionCodeletsSystem2;
    }


    /**
     * @param episodicLearningCodelet the episodicLearningCodelet to set
     */
    public void setEpisodicLearningCodelet(EpisodicLearningCodelet episodicLearningCodelet) {
        this.episodicLearningCodelet = episodicLearningCodelet;
    }


    /**
     * @param episodicRetrievalCodelet the episodicRetrievalCodelet to set
     */
    public void setEpisodicRetrievalCodelet(EpisodicRetrievalCodelet episodicRetrievalCodelet) {
        this.episodicRetrievalCodelet = episodicRetrievalCodelet;
    }


    /**
     * @param expectationCodelet the expectationCodelet to set
     */
    public void setExpectationCodelet(ExpectationCodelet expectationCodelet) {
        this.expectationCodelet = expectationCodelet;
    }


    /**
     * @param consciousnessCodelet the consciousnessCodelet to set
     */
    public void setConsciousnessCodelet(ConsciousnessCodelet consciousnessCodelet) {
        this.consciousnessCodelet = consciousnessCodelet;
    }


    /**
     * @param soarCodelet the soarCodelet to set
     */
    public void setSoarCodelet(SoarCodelet soarCodelet) {
        this.soarCodelet = soarCodelet;
    }


    /**
     * @param goalCodelet the goalCodelet to set
     */
    public void setGoalCodelet(GoalCodelet goalCodelet) {
        this.goalCodelet = goalCodelet;
    }


    /**
     * @param appraisalCodelet the appraisalCodelet to set
     */
    public void setAppraisalCodelet(AppraisalCodelet appraisalCodelet) {
        this.appraisalCodelet = appraisalCodelet;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MoodCodelet> getMoodCodelets() {
        return moodCodelets;
    }

    public List<MotivationalCodelet> getMotivationalCodelets() {
        return motivationalCodelets;
    }

    public List<EmotionalCodelet> getEmotionalCodelets() {
        return emotionalCodelets;
    }

    public GoalCodelet getGoalCodelet() {
        return goalCodelet;
    }

    public AppraisalCodelet getAppraisalCodelet() {
        return appraisalCodelet;
    }

    public WorkingMemory getWorkingMemory() {
        return workingMemory;
    }

    public void setWorkingMemory(WorkingMemory workingMemory) {
        this.workingMemory = workingMemory;
    }

	/**
	 * @return the sensoryCodelets
	 */
	public List<SensoryCodelet> getSensoryCodelets() {
		return sensoryCodelets;
	}

	/**
	 * @return the perceptualCodelets
	 */
	public List<PerceptualCodelet> getPerceptualCodelets() {
		return perceptualCodelets;
	}

	/**
	 * @return the attentionCodeletSystem1
	 */
	public AttentionCodelet getAttentionCodeletSystem1() {
		return attentionCodeletSystem1;
	}

	/**
	 * @return the randomBehavioralCodelets
	 */
	public List<RandomBehavioralCodelet> getRandomBehavioralCodelets() {
		return randomBehavioralCodelets;
	}

	/**
	 * @return the reactiveBehavioralCodelets
	 */
	public List<ReactiveBehavioralCodelet> getReactiveBehavioralCodelets() {
		return reactiveBehavioralCodelets;
	}

	/**
	 * @return the motivationalBehavioralCodelets
	 */
	public List<MotivationalBehavioralCodelet> getMotivationalBehavioralCodelets() {
		return motivationalBehavioralCodelets;
	}

	/**
	 * @return the motorCodelets
	 */
	public List<MotorCodelet> getMotorCodelets() {
		return motorCodelets;
	}

	/**
	 * @return the attentionCodeletsSystem2
	 */
	public List<br.unicamp.meca.system2.codelets.AttentionCodelet> getAttentionCodeletsSystem2() {
		return attentionCodeletsSystem2;
	}

	/**
	 * @return the episodicLearningCodelet
	 */
	public EpisodicLearningCodelet getEpisodicLearningCodelet() {
		return episodicLearningCodelet;
	}

	/**
	 * @return the episodicRetrievalCodelet
	 */
	public EpisodicRetrievalCodelet getEpisodicRetrievalCodelet() {
		return episodicRetrievalCodelet;
	}

	/**
	 * @return the expectationCodelet
	 */
	public ExpectationCodelet getExpectationCodelet() {
		return expectationCodelet;
	}

	/**
	 * @return the consciousnessCodelet
	 */
	public ConsciousnessCodelet getConsciousnessCodelet() {
		return consciousnessCodelet;
	}

	/**
	 * @return the soarCodelet
	 */
	public SoarCodelet getSoarCodelet() {
		return soarCodelet;
	}
    
    
}
