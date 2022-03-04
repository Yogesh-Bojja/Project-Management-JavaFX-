package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Team implements Serializable {

	private String teamID, assigned_projID;
	private HashMap<String, Double> average_competancy = new HashMap <String, Double>();
	private Double percent_of_pref;
	private double skill_gap, team_competancy;
	private ArrayList <String> studentIDs = new ArrayList <String>();
	
	public Team(Team t) {
		this.teamID = t.getTeamID();
		this.assigned_projID = t.getAssigned_projID();
		this.average_competancy = new HashMap(t.getAverage_competancy());
		this.percent_of_pref = t.getPercent_of_pref();
		this.skill_gap = t.getSkill_gap();
		this.team_competancy = t.getTeam_competancy();
		this.studentIDs = new ArrayList(t.getStudentIDs());
	}
	public Team(String team_id_count, ArrayList<String> studentIDs, String assigned_projID) {
		this.studentIDs = studentIDs;
		this.teamID = team_id_count;
		this.assigned_projID = assigned_projID;
		percent_of_pref = 0.0;
		skill_gap = 0.0;
		team_competancy = 0.0;
	}
	
	public double getTeam_competancy() {
		return team_competancy;
	}

	public void setTeam_competancy(double team_competancy) {
		this.team_competancy = team_competancy;
	}

	public String getAssigned_projID() {
		return assigned_projID;
	}

	public void setAssigned_projID(String assigned_projID) {
		this.assigned_projID = assigned_projID;
	}

	public String getTeamID() {
		return teamID;
	}

	public void setTeamID(String teamID) {
		this.teamID = teamID;
	}

	public HashMap<String, Double> getAverage_competancy() {
		return average_competancy;
	}

	public void setAverage_competancy(HashMap<String, Double> average_competancy) {
		this.average_competancy = average_competancy;
	}

	public Double getPercent_of_pref() {
		return percent_of_pref;
	}

	public void setPercent_of_pref(Double percent_of_pref) {
		this.percent_of_pref = percent_of_pref;
	}

	public double getSkill_gap() {
		return skill_gap;
	}

	public void setSkill_gap(double skill_gap) {
		this.skill_gap = skill_gap;
	}

	public ArrayList<String> getStudentIDs() {
		return studentIDs;
	}

	public boolean checkIfArrayNull() {
		if(studentIDs == null) {
			return true;
		}
		return false;
	}
	public void setStudentIDs(ArrayList<String> studentIDs) {
		this.studentIDs = studentIDs;
	}
	public void clear_records() {
		studentIDs = null;
		percent_of_pref = 0.0;
		skill_gap = 0.0;
		team_competancy = 0.0;
	}
	
	
	
}
