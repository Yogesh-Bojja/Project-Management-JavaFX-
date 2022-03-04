package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayDeque;
import java.util.HashMap;

public class Student implements Serializable {
	private String student_id, personality, conflict1, conflict2;
	private int P_grade, N_grade, W_grade, A_grade;
	private HashMap <String, Integer> grades = new HashMap <String, Integer> ();
	private HashMap <String, ArrayDeque> project_preferences = new HashMap <String, ArrayDeque> ();
	private String strpref;
	private String assigned_team;
	
	public Student(String student_id, String p_grade, String n_grade, String a_grade, String w_grade) {
		super();
		this.student_id = student_id;
		P_grade = Integer.parseInt(p_grade);
		N_grade = Integer.parseInt(n_grade);
		W_grade = Integer.parseInt(w_grade);
		A_grade = Integer.parseInt(a_grade);
		grades.put("P",P_grade);
		grades.put("N",N_grade);
		grades.put("W",W_grade);
		grades.put("A",A_grade);
		project_preferences = null;
		personality = null;
		assigned_team = "";
	}
	
	public Student(Student s){
		this.student_id = s.student_id;
		this.P_grade = s.P_grade;
		this.N_grade = s.N_grade;
		this.W_grade = s.A_grade;
		this.A_grade = s.W_grade;
		this.grades.put("P",this.P_grade);
		this.grades.put("N",this.N_grade);
		this.grades.put("W",this.W_grade);
		this.grades.put("A",this.A_grade);
		this.project_preferences = s.project_preferences;
		this.personality = s.personality;
		this.assigned_team = s.assigned_team;
		this.conflict1 = s.conflict1;
		this.conflict2 = s.conflict2;
		
	}
	
	public String getStrpref() {
		String str = "";
		if(this.getProject_preferences() != null) {
			for(String key : this.project_preferences.keySet()) {
				ArrayDeque value = this.getProject_preferences().get(key);
				str = str + "\t" + key + "\t" + value;
			}
		}
		return str;
	}


	public void setStrpref(String str_pref) {
		this.strpref = str_pref;
	}


	public void setPersonalityAndConflict(String Personality, String conflict1, String conflict2) {
		this.personality = Personality;
		this.conflict1 = conflict1;
		this.conflict2 = conflict2;
	}
	
	public String getAssigned_team() {
		return assigned_team;
	}


	public void setAssigned_team(String assigned_team) {
		this.assigned_team = assigned_team;
	}


	public String getStudent_id() {
		return student_id;
	}


	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}


	public int getP_grade() {
		return P_grade;
	}


	public void setP_grade(int p_grade) {
		P_grade = p_grade;
	}


	public int getN_grade() {
		return N_grade;
	}


	public void setN_grade(int n_grade) {
		N_grade = n_grade;
	}


	public int getW_grade() {
		return W_grade;
	}


	public void setW_grade(int w_grade) {
		W_grade = w_grade;
	}


	public int getA_grade() {
		return A_grade;
	}


	public void setA_grade(int a_grade) {
		A_grade = a_grade;
	}


	public String getPersonality() {
		return personality;
	}


	public void setPersonality(String personality) {
		this.personality = personality;
	}


	public String getConflict1() {
		return conflict1;
	}


	public void setConflict1(String conflict1) {
		this.conflict1 = conflict1;
	}


	public String getConflict2() {
		return conflict2;
	}


	public void setConflict2(String conflict2) {
		this.conflict2 = conflict2;
	}


	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public HashMap<String, ArrayDeque> getProject_preferences() {
		return project_preferences;
	}


	public void setProject_preferences(HashMap<String, ArrayDeque> project_preferences) {
		this.project_preferences = project_preferences;
		
	}


	public void setGrades(HashMap<String, Integer> grades) {
		this.grades = grades;
	}


	
}
