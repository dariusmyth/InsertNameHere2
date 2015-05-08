package com.insertNameHere.rallyUtils.objects;

public class RallyDefectObject {

	private String defectName;
	private String defectDescription;
	private String state;
	private String scheduleState;

	public RallyDefectObject(String defectName, String defectDescription, String state, String scheduleState) {
		this.defectName = defectName;
		this.defectDescription = defectDescription;
		this.state = state;
		this.scheduleState = scheduleState;
	}

	public String getDefectName() {
		return defectName;
	}

	public void setDefectName(String defectName) {
		this.defectName = defectName;
	}

	public String getDefectDescription() {
		return defectDescription;
	}

	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getScheduleState() {
		return scheduleState;
	}

	public void setScheduleState(String scheduleState) {
		this.scheduleState = scheduleState;
	}
}
