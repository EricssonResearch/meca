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

/**
 * This class represents the Meca Mood Codelet. In MECA, we will be following
 * Canamero's approach to emotions, together with Sun's proposal to a
 * motivational system. Under this view, emotions work as temporary cognitive
 * distortions on system drives, resulting in a change in priorities, due to the
 * recognition of critical situations. These critical situations will be
 * recognized by Mood Codelets from direct sensor data, but also from situations
 * remembered from episodic memory and possible predicted situations (from
 * System 2) which might be classified as critical. The detection of a critical
 * situation will change the Moods in Perception Memory. These Moods are
 * responsible for, through Emotional Codelets, change the Drives intensity
 * landscape, resulting in a change of priorities in order to better attend the
 * critical situation.
 * <p>
 * The Mood class represents cognitive distortions caused by a combination of
 * drives, sensors, agent's current perception and the agent's current state
 * appraisal. In MECA's motivational system model there is no direct
 * representation of an emotion, as an entity or property. Instead, emotions are
 * represented by cognitive distortions caused in a specific drive. In other
 * words, emotions are the combination of drives and moods, changing the
 * activation value of a drive. This cognitive distortion promotes a sudden
 * change in the activation of a drive, attenuating or enhancing its value,
 * according to the situation. A mood is represented by a numeric attribute
 * which works like a multiplier to the affected Drive's activity.
 * 
 * @author E. Froes
 * @author A. L. O. Paraense
 *
 */
public abstract class MoodCodelet extends br.unicamp.cst.motivational.MoodCodelet {

	/**
	 * Creates the MECA Mood Codelet.
	 * 
	 * @param name
	 *            the name of the Mood Codelet.
	 */
	public MoodCodelet(String name) {
		super(name);
	}
}
