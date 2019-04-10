/**
 * 
 */
package br.unicamp.meca.models;

/**
 * @author andre
 *
 */
public class ActionSequencePlan {
	
	private String[] actionIdSequence;
	
	private int currentActionIdIndex = 0;

	public ActionSequencePlan(String[] actionIdSequence) {
		this.actionIdSequence = actionIdSequence;
	}
	
	/**
	 * Returns the current action to be undertaken in the plan
	 * @return
	 */
	public String getCurrentActionId() {
		return actionIdSequence[currentActionIdIndex];
	}

	/**
	 * @return the actionIdSequence
	 */
	public String[] getActionIdSequence() {
		return actionIdSequence;
	}

	/**
	 * @param actionIdSequence the actionIdSequence to set
	 */
	public void setActionIdSequence(String[] actionIdSequence) {
		this.actionIdSequence = actionIdSequence;
	}

	/**
	 * @return the currentActionIdIndex
	 */
	public int getCurrentActionIdIndex() {
		return currentActionIdIndex;
	}

	/**
	 * @param currentActionIdIndex the currentActionIdIndex to set
	 */
	public void setCurrentActionIdIndex(int currentActionIdIndex) {
		this.currentActionIdIndex = currentActionIdIndex;
	}
}
