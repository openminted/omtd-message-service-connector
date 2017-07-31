package eu.openminted.messageservice.messages;

public class WorkflowExecutionStatusMessage {

	private String workflowExecutionID;
	private String workflowID;
	private String corpusID;
	private String workflowStatus;
	
	public WorkflowExecutionStatusMessage(){
		
	}
	
	public WorkflowExecutionStatusMessage(String workflowExecutionID, String workflowID, String corpusID,
			String workflowStatus) {
		super();
		this.workflowExecutionID = workflowExecutionID;
		this.workflowID = workflowID;
		this.corpusID = corpusID;
		this.workflowStatus = workflowStatus;
	}
	
	public String getWorkflowExecutionID() {
		return workflowExecutionID;
	}
	public void setWorkflowExecutionID(String workflowExecutionID) {
		this.workflowExecutionID = workflowExecutionID;
	}
	public String getWorkflowID() {
		return workflowID;
	}
	public void setWorkflowID(String workflowID) {
		this.workflowID = workflowID;
	}
	public String getCorpusID() {
		return corpusID;
	}
	public void setCorpusID(String corpusID) {
		this.corpusID = corpusID;
	}
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	
}
