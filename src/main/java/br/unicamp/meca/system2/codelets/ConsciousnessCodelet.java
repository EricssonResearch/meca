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

import br.unicamp.cst.consciousness.SpotlightBroadcastController;
import br.unicamp.cst.core.entities.CodeRack;

/**
 * This class represents MECA's Consciousness Codelet.
 * <p>
 * Consciousness has emerged in animals apparently around 500 million years ago
 * (Feinberg and Mallatt, 2013). One of the theories to explain the evolutionary
 * advantage brought by consciousness is that consciousness works like a filter
 * in the manifold of perceptions gathered by perception processes. This is
 * basically the Global Workspace Theory (GWT) from Baars, (1988). This filtered
 * information is supposed to be the most relevant and important information at
 * the present time for the animal in question. In Global Workspace Theory, this
 * filtered information is then broadcast to all other subsystems, allowing an
 * interesting dynamics to emerge. So, the Global Workspace is a privileged
 * space within Working Memory, where very important information is supposed to
 * be present.
 * <p>
 * The Global Workspace is a virtual kind of memory. Instead of storing its own
 * set of Memory Objects, the Global Workspace is just a collection of
 * references to other Memory Objects stored in the different memories described
 * before, tagged as currently important. It is constantly being changed, by the
 * consciousness mechanism implemented by the Consciousness codelets.
 * <p>
 * Even though the topic of machine consciousness is still very controversial in
 * the community (Gamez, 2009), one of the most popular approaches involves the
 * implementation of Global Workspace Theory, which was implemented in the LIDA
 * cognitive architecture (Franklin, Strain, et al., 2012), and also by others
 * (Dubois et al., 2008; Shanahan, 2006). In CST, the consciousness mechanism is
 * not a built-in mechanism, but a mechanism which is implemented by means of
 * consciousness codelets. It is true that these codelets make use of features
 * provided by CST core, like the global input in codelets, which allow the
 * broadcast required in GWT. The current implementation of CST provides a set
 * of codelets which implements GWT in a way very similar to LIDA, but with some
 * differences. In LIDA, the codelets assumed to be in a coalition are those
 * which trigger at the same time. This is not the same in CST. In CST, codelets
 * are assumed to be in a coalition just if they are coupled together by means
 * of a common memory object. CST implementation of GWT also allows for subtle
 * variations or interpretations of GTW, something which is not available in
 * LIDA. An example on the use of consciousness codelets to implement GWT
 * machine consciousness in a cognitive architecture using CST is given in
 * Paraense, Raizer, and Gudwin, 2016.
 * <p>
 * In the case of MECA, we are using small variation of this mechanism, by means
 * of the Consciousness Subsystem. Instead of promoting a competition among all
 * unconscious sources of information, the consciousness mechanism in MECA
 * selects information from only three different sub-memories: the Current
 * Perception, the Episodic Recall Memory and the Imagination. At each time
 * step, the Consciousness Codelet evaluates the Memory Objects within these
 * three locations and choose something to send to the Global Workspace. The
 * information in the Global Workspace can then be used in the Planning
 * Subsystem.
 * <p>
 * References:
 * <p>
 * (Feinberg et al. 2013) Feinberg, T. E. and Mallatt, J. The Evolutionary and
 * Genetic Origins of Consciousness in the Cambrian Period over 500 Million
 * Years Ago. In: Frontiers in Psychology 4 (2013).
 * <p>
 * (Baars 1988) Baars, B. J. A Cognitive Theory of Consciousness. Cambridge
 * University Press, 1988.
 * <p>
 * (Gamez 2009) Gamez, D. The potential for consciousness of artificial systems.
 * In: International Journal of Machine Consciousness 1.02 (2009), pp. 213-223.
 * <p>
 * (Franklin et al. 2012) Franklin, S., Strain, S., Snaider, J., McCall, R., and
 * Faghihi, U. Global workspace theory, its LIDA model and the underlying
 * neuroscience. In: Biologically Inspired Cognitive Architectures 1 (2012), pp.
 * 32-43.
 * <p>
 * ( Dubois et al. 2008) Dubois, D., Gaha, M., Nkambou, R., and Poirier, P.
 * Cognitive Tutoring System with Consciousness. In: Intelligent Tutoring
 * Systems. Springer. 2008, pp. 803-806.
 * <p>
 * (Shanahan 2006) Shanahan, M. A cognitive architecture that combines internal
 * simulation with a global workspace. In: Consciousness and cognition 15.2
 * (2006), pp. 433-449.
 * <p>
 * (Paraense et al. 2016) Paraense, A. L. O., Raizer, K., and Gudwin, R. R. A
 * machine consciousness approach to urban traffic control. In: Biologically
 * Inspired Cognitive Architectures 15 (2016), pp. 61-73. URL :
 * http://www.sciencedirect.com/science/ article/pii/S2212683X15000614.
 * 
 * 
 * @author A. L. O. Paraense
 * @see SpotlightBroadcastController
 *
 */
public abstract class ConsciousnessCodelet extends SpotlightBroadcastController {

	/**
	 * Creates a MECA Consciousness Codelet.
	 * 
	 * @param codeRack
	 *            the Code Rack, containing all the codelets in the whole
	 *            system.
	 */
	public ConsciousnessCodelet(CodeRack codeRack) {
		super(codeRack);
	}
}
