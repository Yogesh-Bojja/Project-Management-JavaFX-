package model;

import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;



public class ProjectManager implements Serializable {

	public HashMap <String, Company> companies = new LinkedHashMap <String, Company> ();
	public int company_id_count = 1;
	
	public HashMap <String, Owner> owners = new LinkedHashMap <String, Owner> ();
	public int owner_id_count = 1;
	
	public HashMap <String, Project> projects = new LinkedHashMap <String, Project> ();
	public int project_id_count = 1;
	
	public HashMap <String, Student> students = new LinkedHashMap <String, Student> ();
	public int students_imported_flag = 0;
	public int A_count = 0;
	public int B_count = 0;
	public int C_count = 0;
	public int D_count = 0;

	//public List <Project> selectedProjectsList = new LinkedList<Project>();
	
	public int team_id_count = 1;
	public String team1_id, team2_id, team3_id, team4_id, team5_id;
	public ArrayList<String> team1 = new ArrayList<String>();
	public ArrayList<String> team2 = new ArrayList<String>();
	public ArrayList<String> team3 = new ArrayList<String>();
	public ArrayList<String> team4 = new ArrayList<String>();
	public ArrayList<String> team5 = new ArrayList<String>();
	public HashMap<String, Team> teams = new HashMap<String, Team>();

	public ArrayDeque<HashMap<String, Team>> stack = new ArrayDeque<HashMap<String, Team>>();
	public ArrayDeque<HashMap<TextField, String>> GUIstack = new ArrayDeque<>();
	
	public ProjectManager(){
		
	}
	
	public ProjectManager(ProjectManager p){
		this.team1_id = p.team1_id;
		this.team2_id = p.team2_id;
		this.team3_id = p.team3_id;
		this.team4_id = p.team4_id;
		this.team5_id = p.team5_id;

		this.team1 = new ArrayList<>(p.team1);
		this.team2 = new ArrayList<>(p.team2);
		this.team3 = new ArrayList<>(p.team3);
		this.team4 = new ArrayList<>(p.team4);
		this.team5 = new ArrayList<>(p.team5);

		this.teams = new HashMap<String, Team>(p.teams);
		this.students = new HashMap<>(p.students);
		this.projects = new HashMap<>(p.projects);
	}
	
	public void display_barchart1(BarChart<String, Double> bc12, CategoryAxis bc1_ca, NumberAxis bc1_na, Label sd12) {
		bc12.getData().clear();
		if(teams.size()!=5) {
			return;
		}
		for(Team tobj : teams.values()) {
			calculatePercentOfPref(tobj);
		}
		
		XYChart.Series<String, Double> series1 = new XYChart.Series<>();
	    series1.getData().add(new XYChart.Data<>("Team1", teams.get(team1_id).getPercent_of_pref()));
	    series1.getData().add(new XYChart.Data<>("Team2", teams.get(team2_id).getPercent_of_pref()));
	    series1.getData().add(new XYChart.Data<>("Team3", teams.get(team3_id).getPercent_of_pref()));
	    series1.getData().add(new XYChart.Data<>("Team4", teams.get(team4_id).getPercent_of_pref()));
	    series1.getData().add(new XYChart.Data<>("Team5", teams.get(team5_id).getPercent_of_pref()));
	    bc1_ca.setAnimated(false);
	    bc12.getData().addAll(series1);
	    sd12.setText("Std Dev = "+calculatePercOfPrefSD());
	}
	
	public void display_barchart2(BarChart<String, Double> bcac, CategoryAxis bc2_ca, NumberAxis bc2_na, Label sdac) {
		bcac.getData().clear();
		if(teams.size()!=5) {
			return;
		}
		for(Team tobj : teams.values()) {
			calculateAverageCompetency(tobj);
		}
		
		XYChart.Series<String, Double> series1 = new XYChart.Series<>();
	    series1.getData().add(new XYChart.Data<>("Team1", teams.get(team1_id).getTeam_competancy()));
	    series1.getData().add(new XYChart.Data<>("Team2", teams.get(team2_id).getTeam_competancy()));
	    series1.getData().add(new XYChart.Data<>("Team3", teams.get(team3_id).getTeam_competancy()));
	    series1.getData().add(new XYChart.Data<>("Team4", teams.get(team4_id).getTeam_competancy()));
	    series1.getData().add(new XYChart.Data<>("Team5", teams.get(team5_id).getTeam_competancy()));
	    bc2_ca.setAnimated(false);
	    bcac.getData().addAll(series1);
		sdac.setText("Std Dev = "+calculateAverageTeamCompetanceSD());
		
	}

	public void display_barchart3(BarChart<String, Double> bcsg, CategoryAxis bc3_ca, NumberAxis bc3_na, Label sdsg) {
		bcsg.getData().clear();
		if(teams.size()!=5) {
			return;
		}
		for(Team tobj : teams.values()) {
			calculateSkillGap(tobj);
		}
		
		XYChart.Series<String, Double> series1 = new XYChart.Series<>();
	    series1.getData().add(new XYChart.Data<>("Team1", teams.get(team1_id).getSkill_gap()));
	    series1.getData().add(new XYChart.Data<>("Team2", teams.get(team2_id).getSkill_gap()));
	    series1.getData().add(new XYChart.Data<>("Team3", teams.get(team3_id).getSkill_gap()));
	    series1.getData().add(new XYChart.Data<>("Team4", teams.get(team4_id).getSkill_gap()));
	    series1.getData().add(new XYChart.Data<>("Team5", teams.get(team5_id).getSkill_gap()));
	    bc3_ca.setAnimated(false);
	    bcsg.getData().addAll(series1);
		sdsg.setText("Std Dev = "+calculateSkillGapSD());
	}

	public void calculatePercentOfPref(Team tobj) {
		double count = 0;
		if(tobj.getStudentIDs() == null) {
			tobj.setPercent_of_pref(0.0);
			return;
		}
		for(String sid : tobj.getStudentIDs()) {
			HashMap <String, ArrayDeque> a = students.get(sid).getProject_preferences();
			if(a != null) {
				if(a.containsKey(tobj.getAssigned_projID())) {
					int value = (int) a.get(tobj.getAssigned_projID()).peek();
					if(value == 4 || value == 3) {
						count++;
					}
				}
			}
		}
		Double percent = (count/4)*100;
		tobj.setPercent_of_pref(percent);
	}

	public void calculateAverageCompetency(Team tobj) {
		Double score = 0.0;
		HashMap <String, Double> h = new HashMap <String, Double>();
		h.put("P", 0.0);
		h.put("N", 0.0);
		h.put("W", 0.0);
		h.put("A", 0.0);
		if(tobj.getStudentIDs() == null || tobj.getStudentIDs().isEmpty()) {
			tobj.setTeam_competancy(0.0);
			return;
		}
		for(String sid : tobj.getStudentIDs()) {
			HashMap<String, Integer> b = students.get(sid).getGrades();
			h.put("P",h.get("P")+b.get("P"));
			h.put("N",h.get("N")+b.get("N"));
			h.put("W",h.get("W")+b.get("W"));
			h.put("A",h.get("A")+b.get("A"));
		}
		
		score = h.get("P")+h.get("N")+h.get("A")+h.get("W");
		score = score / 16.0;
		
		h.put("P",h.get("P")/4.0);
		h.put("N",h.get("N")/4.0);
		h.put("W",h.get("W")/4.0);
		h.put("A",h.get("A")/4.0);
		
		tobj.setTeam_competancy(score);
		tobj.setAverage_competancy(h);
	}
	
	public void calculateSkillGap(Team tobj) {
		double skillGap = 0.0;
		HashMap<String, Integer> p = projects.get(tobj.getAssigned_projID()).getSkill_rank();
		if(tobj.getStudentIDs() == null || tobj.getStudentIDs().isEmpty()) {
			tobj.setSkill_gap(skillGap);
			return;
		}
		calculateAverageCompetency(tobj);
			HashMap<String, Double> b = tobj.getAverage_competancy();
			
			if(b.isEmpty()) {
				tobj.setSkill_gap(0.0);
				return;
			}
			if(p.get("P") > b.get("P")) {
				skillGap = skillGap + (p.get("P")-b.get("P"));
			}
			if(p.get("N") > b.get("N")) {
				skillGap = skillGap + (p.get("N")-b.get("N"));
			}
			if(p.get("W") > b.get("W")) {
				skillGap = skillGap + (p.get("W")-b.get("W"));
			}
			if(p.get("A") > b.get("A")) {
				skillGap = skillGap + (p.get("A")-b.get("A"));
			}
		
		tobj.setSkill_gap(skillGap);
	}

	public double calculatePercOfPrefSD() {
		double total = 0;
		for(Team t : teams.values()) {
			total = total + t.getPercent_of_pref();
		}
		double mean = total/teams.size();
		total = 0;
		for(Team t : teams.values()) {
			total += Math.pow(t.getPercent_of_pref()-mean,2);
		}
		double sd = Math.sqrt(total/teams.size());
		return sd;
		
	}

	public double calculateSkillGapSD() {
		double total = 0;
		for(Team t : teams.values()) {
			total = total + t.getSkill_gap();
		}
		double mean = total/teams.size();
		total = 0;
		for(Team t : teams.values()) {
			total += Math.pow(t.getSkill_gap()-mean,2);
		}
		double sd = Math.sqrt(total/teams.size());
		return sd;
		
	}

	public double calculateAverageTeamCompetanceSD() {
		double total = 0;
		for(Team t : teams.values()) {
			total = total + t.getTeam_competancy();
		}
		double mean = total/teams.size();
		total = 0;
		for(Team t : teams.values()) {
			total += Math.pow(t.getTeam_competancy()-mean,2);
		}
		double sd = Math.sqrt(total/5);
		return sd;

	}

}
