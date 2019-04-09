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
package br.unicamp.meca.mind;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import br.unicamp.cst.core.entities.Memory;
import br.unicamp.cst.core.entities.MemoryObject;
import br.unicamp.cst.core.entities.Mind;
import br.unicamp.meca.memory.WorkingMemory;
import br.unicamp.meca.system1.codelets.ActionCodelet;
import br.unicamp.meca.system1.codelets.AttentionCodelet;
import br.unicamp.meca.system1.codelets.BehaviorCodelet;
import br.unicamp.meca.system1.codelets.EmotionalCodelet;
import br.unicamp.meca.system1.codelets.MoodCodelet;
import br.unicamp.meca.system1.codelets.MotivationalCodelet;
import br.unicamp.meca.system1.codelets.MotorCodelet;
import br.unicamp.meca.system1.codelets.PerceptualCodelet;
import br.unicamp.meca.system1.codelets.SensoryCodelet;
import br.unicamp.meca.system2.codelets.AppraisalCodelet;
import br.unicamp.meca.system2.codelets.ConsciousnessCodelet;
import br.unicamp.meca.system2.codelets.EpisodicLearningCodelet;
import br.unicamp.meca.system2.codelets.EpisodicRetrievalCodelet;
import br.unicamp.meca.system2.codelets.ExpectationCodelet;
import br.unicamp.meca.system2.codelets.GoalCodelet;
import br.unicamp.meca.system2.codelets.SoarCodelet;

/**
 * This class represents the MECA's agent mind.This is the main class to be used
 * by any MECA user.
 * 
 * @author A. L. O. Paraense
 * @author E. Froes
 * @see Mind
 */
public class MecaMind extends Mind {
	
	public static final String ACTION_SEQUENCE_PLAN_ID = "ACTION_SEQUENCE_PLAN_ID";

	public static final String ACTION_SEQUENCE_PLAN_REQUEST_ID = "ACTION_SEQUENCE_PLAN_REQUEST_ID";

	/*
	 * System 1
	 */

	private List<SensoryCodelet> sensoryCodelets;

	private List<PerceptualCodelet> perceptualCodelets;

	private List<MoodCodelet> moodCodelets;

	private List<MotivationalCodelet> motivationalCodelets;

	private AttentionCodelet attentionCodeletSystem1;

	private List<EmotionalCodelet> emotionalCodelets;

	private List<ActionCodelet> actionCodelets;

	private List<BehaviorCodelet> behaviorCodelets;

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

	private WorkingMemory workingMemory;

	private String id;

	/**
	 * Creates the MECA Mind.
	 */
	public MecaMind() {
		setId(UUID.randomUUID().toString());
		setWorkingMemory(new WorkingMemory(getId()));
	}

	/**
	 * Creates the MECA Mind.
	 * 
	 * @param id
	 *            the id of the MECA mind. Must be unique per MECA mind.
	 */
	public MecaMind(String id) {
		setId(id);
		setWorkingMemory(new WorkingMemory(getId()));
	}

	/**
	 * Mounts the MECA Mind. After creating the MECA Mind's instance and setting
	 * all the codelets inside it, this method is responsible for binding
	 * together all codelets inside the mind, creating memories (objects and
	 * containers) and setting them either as inputs or outputs of each codelet,
	 * according to MECA's reference architecture, as shown in the picture in
	 * https://github.com/CST-Group/meca/blob/master/README.md.
	 * <p>
	 * This method must be called before running the MECA Agent.
	 */
	public void mountMecaMind() {

		mountSensoryCodelets();

		mountPerceptualCodelets();

		mountMotorCodelets();

		mountAttentionCodelets();

		mountWorkingMemory();

		mountSoarCodelet();		

		mountMotivationalCodelets();

		mountBehaviorCodelets();
		
		mountActionCodelets();

		mountModules();

	}

	private void mountModules() {

		if (getMotivationalCodelets() != null) {
			if (getMotivationalCodelets().size() > 0) {
				List<? extends br.unicamp.cst.motivational.MotivationalCodelet> mtcodelets = getMotivationalCodelets();
				getMotivationalSubsystemModule()
						.setMotivationalCodelets((List<br.unicamp.cst.motivational.MotivationalCodelet>) mtcodelets);
			}

		}

		if (getSoarCodelet() != null) {
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
										if (sensoryCodeletId != null
												&& sensoryCodeletId.equalsIgnoreCase(sensoryCodelet.getId())) {
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
					 * Output
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
								driveRelevance.put(motivationalCodeletInput.getOutputDriveMO(),
										motivationalCodeletId.getValue());

								motivationalCodelet.addInput(this.createMemoryObject(
										motivationalCodeletInput.getOutputDriveMO().getName(), driveRelevance));
							}
						}
					}
				}

				/*
				 * Output Drives
				 */
				MemoryObject outputDrive = this.createMemoryObject(motivationalCodelet.getId() + "_DRIVE_MO");
				motivationalCodelet.addOutput(outputDrive);

				insertCodelet(motivationalCodelet);
			}
		}
	}

	private void mountBehaviorCodelets() {
		if (behaviorCodelets != null) {
			for (BehaviorCodelet behaviorCodelet : behaviorCodelets) {
				if (behaviorCodelet != null && behaviorCodelet.getId() != null
						&& behaviorCodelet.getMotivationalCodeletsIds() != null
						&& behaviorCodelet.getMotorCodeletId() != null) {

					/*
					 * Outputs
					 */
					if (motorCodelets != null) {
						for (MotorCodelet motorCodelet : motorCodelets) {
							if (motorCodelet != null && motorCodelet.getId() != null) {
								if (motorCodelet.getId()
										.equalsIgnoreCase(behaviorCodelet.getMotorCodeletId())) {
									behaviorCodelet.addOutputs(motorCodelet.getInputs());
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
								ArrayList<String> motivationalCodeletsIds = behaviorCodelet
										.getMotivationalCodeletsIds();
								if (motivationalCodeletsIds != null) {
									for (String motivationalCodeletId : motivationalCodeletsIds) {
										if (motivationalCodeletId != null && motivationalCodelet.getId()
												.equalsIgnoreCase(motivationalCodeletId)) {
											behaviorCodelet.addInputs(motivationalCodelet.getOutputs());
										}
									}
								}
							}
						}
					}

					if (soarCodelet != null && soarCodelet.getId() != null && behaviorCodelet.getSoarCodeletId() != null) {
						if (soarCodelet.getId().equalsIgnoreCase(behaviorCodelet.getSoarCodeletId())) {
							behaviorCodelet.addBroadcasts(soarCodelet.getOutputs());
						}

					}

					insertCodelet(behaviorCodelet);

				}
			}
		}
	}

	private void mountActionCodelets() {
		if (actionCodelets != null) {
			for (ActionCodelet actionCodelet : actionCodelets) {
				if (actionCodelet != null && actionCodelet.getId() != null
						&& actionCodelet.getPerceptualCodeletsIds() != null
						&& actionCodelet.getMotorCodeletId() != null) {

					insertCodelet(actionCodelet);
					/*
					 * Outputs
					 */
					if (motorCodelets != null) {
						for (MotorCodelet motorCodelet : motorCodelets) {
							if (motorCodelet != null && motorCodelet.getId() != null) {
								if (motorCodelet.getId()
										.equalsIgnoreCase(actionCodelet.getMotorCodeletId())) {
									actionCodelet.addOutputs(motorCodelet.getInputs());
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
								ArrayList<String> perceptualCodeletsIds = actionCodelet
										.getPerceptualCodeletsIds();
								if (perceptualCodeletsIds != null) {
									for (String perceptualCodeletId : perceptualCodeletsIds) {
										if (perceptualCodeletId != null
												&& perceptualCodelet.getId().equalsIgnoreCase(perceptualCodeletId)) {
											actionCodelet.addInputs(perceptualCodelet.getOutputs());
										}
									}
								}
							}
						}
					}

					if (soarCodelet != null && soarCodelet.getId() != null && actionCodelet.getSoarCodeletId() != null) {
						if (soarCodelet.getId().equalsIgnoreCase(actionCodelet.getSoarCodeletId())) {
							actionCodelet.addBroadcasts(soarCodelet.getOutputs());
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

			/*
			 * Inputs
			 */
			if (perceptualCodelets != null) {
				for (String inputPerceptualId : attentionCodeletSystem1.getPerceptualCodeletsIds()) {
					for (PerceptualCodelet perceptualCodelet : perceptualCodelets) {

						if (inputPerceptualId.equals(perceptualCodelet.getId())) {
							attentionCodeletSystem1.addInputs(perceptualCodelet.getOutputs());
						}
					}
				}
			}
			/*
			 * Outputs
			 */
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
	 * Sets the Sensory Codelets.
	 * 
	 * @param sensoryCodelets
	 *            the sensoryCodelets to set
	 */
	public void setSensoryCodelets(List<SensoryCodelet> sensoryCodelets) {
		this.sensoryCodelets = sensoryCodelets;
	}

	/**
	 * Sets the Perceptual Codelets.
	 * 
	 * @param perceptualCodelets
	 *            the perceptualCodelets to set
	 */
	public void setPerceptualCodelets(List<PerceptualCodelet> perceptualCodelets) {
		this.perceptualCodelets = perceptualCodelets;
	}

	/**
	 * Sets the Mood Codelets.
	 * 
	 * @param moodCodelets
	 *            the moodCodelets to set
	 */
	public void setMoodCodelets(List<MoodCodelet> moodCodelets) {
		this.moodCodelets = moodCodelets;
	}

	/**
	 * Sets the Motivational Codelets.
	 * 
	 * @param motivationalCodelets
	 *            the motivationalCodelets to set
	 */
	public void setMotivationalCodelets(List<MotivationalCodelet> motivationalCodelets) {
		this.motivationalCodelets = motivationalCodelets;
	}

	/**
	 * Sets the System 1 Attention Codelet.
	 * 
	 * @param attentionCodeletSystem1
	 *            the attentionCodeletSystem1 to set
	 */
	public void setAttentionCodeletSystem1(AttentionCodelet attentionCodeletSystem1) {
		this.attentionCodeletSystem1 = attentionCodeletSystem1;
	}

	/**
	 * Sets the Emotional Codelets.
	 * 
	 * @param emotionalCodelets
	 *            the emotionalCodelets to set
	 */
	public void setEmotionalCodelets(List<EmotionalCodelet> emotionalCodelets) {
		this.emotionalCodelets = emotionalCodelets;
	}

	/**
	 * Sets the Reactive Behavioral Codelets.
	 * 
	 * @param reactiveBehavioralCodelets
	 *            the reactiveBehavioralCodelets to set
	 */
	public void setReactiveBehavioralCodelets(List<ActionCodelet> reactiveBehavioralCodelets) {
		this.actionCodelets = reactiveBehavioralCodelets;
	}

	/**
	 * Sets the Motor Codelets.
	 * 
	 * @param motorCodelets
	 *            the motorCodelets to set
	 */
	public void setMotorCodelets(List<MotorCodelet> motorCodelets) {
		this.motorCodelets = motorCodelets;
	}

	/**
	 * Sets the System 2 Attention Codelets
	 * 
	 * @param attentionCodeletsSystem2
	 *            the attentionCodeletsSystem2 to set
	 */
	public void setAttentionCodeletsSystem2(
			List<br.unicamp.meca.system2.codelets.AttentionCodelet> attentionCodeletsSystem2) {
		this.attentionCodeletsSystem2 = attentionCodeletsSystem2;
	}

	/**
	 * Sets the Episodic Learning Codelet.
	 * 
	 * @param episodicLearningCodelet
	 *            the episodicLearningCodelet to set
	 */
	public void setEpisodicLearningCodelet(EpisodicLearningCodelet episodicLearningCodelet) {
		this.episodicLearningCodelet = episodicLearningCodelet;
	}

	/**
	 * Sets the Episodic Retrieval Codelet.
	 * 
	 * @param episodicRetrievalCodelet
	 *            the episodicRetrievalCodelet to set
	 */
	public void setEpisodicRetrievalCodelet(EpisodicRetrievalCodelet episodicRetrievalCodelet) {
		this.episodicRetrievalCodelet = episodicRetrievalCodelet;
	}

	/**
	 * Sets the Expectation Codelet.
	 * 
	 * @param expectationCodelet
	 *            the expectationCodelet to set
	 */
	public void setExpectationCodelet(ExpectationCodelet expectationCodelet) {
		this.expectationCodelet = expectationCodelet;
	}

	/**
	 * Sets the Consciousness Codelet.
	 * 
	 * @param consciousnessCodelet
	 *            the consciousnessCodelet to set
	 */
	public void setConsciousnessCodelet(ConsciousnessCodelet consciousnessCodelet) {
		this.consciousnessCodelet = consciousnessCodelet;
	}

	/**
	 * Sets the Soar Codelet.
	 * 
	 * @param soarCodelet
	 *            the soarCodelet to set
	 */
	public void setSoarCodelet(SoarCodelet soarCodelet) {
		this.soarCodelet = soarCodelet;
	}

	/**
	 * Sets the Goal Codelet.
	 * 
	 * @param goalCodelet
	 *            the goalCodelet to set
	 */
	public void setGoalCodelet(GoalCodelet goalCodelet) {
		this.goalCodelet = goalCodelet;
	}

	/**
	 * Sets the Appraisal Codelet.
	 * 
	 * @param appraisalCodelet
	 *            the appraisalCodelet to set
	 */
	public void setAppraisalCodelet(AppraisalCodelet appraisalCodelet) {
		this.appraisalCodelet = appraisalCodelet;
	}

	/**
	 * Gets the MECA Mind id
	 * 
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Sets the MECA Mind id
	 * 
	 * @param id
	 *            the id to set.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Gets the Mood Codelets.
	 * 
	 * @return the mood codelets.
	 */
	public List<MoodCodelet> getMoodCodelets() {
		return moodCodelets;
	}

	/**
	 * Gets the Motivational Codelets.
	 * 
	 * @return the Motivational Codelets.
	 */
	public List<MotivationalCodelet> getMotivationalCodelets() {
		return motivationalCodelets;
	}

	/**
	 * Gets the Emotional Codelets.
	 * 
	 * @return the Emotional Codelets.
	 */
	public List<EmotionalCodelet> getEmotionalCodelets() {
		return emotionalCodelets;
	}

	/**
	 * Gets the Goal Codelets.
	 * 
	 * @return the Goal Codelet.
	 */
	public GoalCodelet getGoalCodelet() {
		return goalCodelet;
	}

	/**
	 * Gets the Appraisal Codelet.
	 * 
	 * @return the Appraisal Codelet.
	 */
	public AppraisalCodelet getAppraisalCodelet() {
		return appraisalCodelet;
	}

	/**
	 * Gets the Working Memory.
	 * 
	 * @return the Working Memory.
	 */
	public WorkingMemory getWorkingMemory() {
		return workingMemory;
	}

	/**
	 * Sets the Working Memory.
	 * 
	 * @param workingMemory
	 *            the working memory to set.
	 */
	public void setWorkingMemory(WorkingMemory workingMemory) {
		this.workingMemory = workingMemory;
	}

	/**
	 * Gets the Sensory Codelets.
	 * 
	 * @return the sensoryCodelets.
	 */
	public List<SensoryCodelet> getSensoryCodelets() {
		return sensoryCodelets;
	}

	/**
	 * Gets the Perceptual Codelets.
	 * 
	 * @return the perceptualCodelets.
	 */
	public List<PerceptualCodelet> getPerceptualCodelets() {
		return perceptualCodelets;
	}

	/**
	 * Gets the Attention Codelet from System 1.
	 * 
	 * @return the attentionCodeletSystem1.
	 */
	public AttentionCodelet getAttentionCodeletSystem1() {
		return attentionCodeletSystem1;
	}

	/**
	 * Gets the Motor Codelets.
	 * 
	 * @return the motorCodelets.
	 */
	public List<MotorCodelet> getMotorCodelets() {
		return motorCodelets;
	}

	/**
	 * Gets the Attention Codelets from System 2.
	 * 
	 * @return the attentionCodeletsSystem2.
	 */
	public List<br.unicamp.meca.system2.codelets.AttentionCodelet> getAttentionCodeletsSystem2() {
		return attentionCodeletsSystem2;
	}

	/**
	 * Gets the Episodic Learning Codelet.
	 * 
	 * @return the episodicLearningCodelet.
	 */
	public EpisodicLearningCodelet getEpisodicLearningCodelet() {
		return episodicLearningCodelet;
	}

	/**
	 * Gets the Episodic Retrieval Codelet.
	 * 
	 * @return the episodicRetrievalCodelet.
	 */
	public EpisodicRetrievalCodelet getEpisodicRetrievalCodelet() {
		return episodicRetrievalCodelet;
	}

	/**
	 * Gets the Expectation Codelet.
	 * 
	 * @return the expectationCodelet.
	 */
	public ExpectationCodelet getExpectationCodelet() {
		return expectationCodelet;
	}

	/**
	 * Gets the Consciousness Codelet.
	 * 
	 * @return the consciousnessCodelet.
	 */
	public ConsciousnessCodelet getConsciousnessCodelet() {
		return consciousnessCodelet;
	}

	/**
	 * Gets the Soar Codelet.
	 * 
	 * @return the soarCodelet.
	 */
	public SoarCodelet getSoarCodelet() {
		return soarCodelet;
	}

}
