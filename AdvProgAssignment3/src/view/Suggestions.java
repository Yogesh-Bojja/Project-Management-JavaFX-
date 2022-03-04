package view;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import controller.GUIController;
import model.Project;
import model.ProjectManager;
import model.Student;
import model.Team;

class StuObj {
	Student sobj;
	double avg;
	
	StuObj(Student s){
		this.sobj = s;
		this.avg = Suggestions.getAverageOfStudent(s);
	}	
}

class NewTeam {
	List<StuObj> student_mem = new LinkedList<StuObj>();
	
	public NewTeam(LinkedList<StuObj> students) {
		this.student_mem = students;
	}
	
	//returns student object with highes marks in a team
	public StuObj getStudentWithHighestMarks(int stu1_idx) {
		student_mem.sort(SuggestionsComparator1);
		return student_mem.get(student_mem.size()-stu1_idx-1);
	}
	
	//return student object with lowest marks in a team
	public StuObj getStudentWithLowestMarks(int stu2_idx) {		
		student_mem.sort(SuggestionsComparator1);
		return student_mem.get(stu2_idx);

	}
	
	//returns Avergae of all student averages in team
	public double getAverageOfATeam() {
		double total = 0.0;
		for(StuObj s : student_mem) {
			total = total + s.avg;
		}
		return total/student_mem.size();
	}
	
	//comparator for sorting NewTeam Objects 
	public static Comparator<NewTeam> skillComparator = new Comparator<NewTeam>() {

		@Override
		public int compare(NewTeam o1, NewTeam o2) {
			double score1 = o1.getAverageOfATeam();
			double score2 = o2.getAverageOfATeam();
			return Double.compare(score1, score2);
		}
	};
	
	public static Comparator<StuObj> SuggestionsComparator1 = new Comparator<StuObj>() {

		@Override
		public int compare(StuObj o1, StuObj o2) {
			double score1 = o1.avg;
			double score2 = o2.avg;
			return Double.compare(score1, score2);
		}
	};
	
	public static Comparator<String> StringComparator = new Comparator<String>() {

		@Override
		public int compare(String o1, String o2) {
			double num1 = Double.parseDouble(o1.split(" ")[2]);
			double num2 = Double.parseDouble(o2.split(" ")[2]);
			return Double.compare(num2, num1);
		}
	};

}

//Suggestions thread
public class Suggestions extends Thread {

	ProjectManager projObj;

	//Checking swapping if valid
	public static boolean CheckToSwap(Student toCheck, Student exist, NewTeam team) {
		int has_A = 0;
		boolean has_conflict = false;
		Set c = new TreeSet();
		for(StuObj so : team.student_mem) {
			Student sobj = so.sobj;
			if(!exist.equals(sobj)) {
				if(sobj.getPersonality().equals("A")) {
					has_A++;
				}
				if(sobj.getConflict1().equals(toCheck.getStudent_id()) || sobj.getConflict2().equals(toCheck.getStudent_id())) {
					has_conflict = true;
				}
				c.add(sobj.getPersonality());
			}
		}
		c.add(toCheck.getPersonality());
		if(toCheck.getPersonality().equals("A")) {
			has_A++;
		}
		if(has_A==1 && has_conflict == false && c.size() >= 3) {
			return true;
		}
		else {
			return false;
		}
	}

	//getting average of students grades i.e. average of grades scored in Programming, Web Tech, Networking, and Analytics
	public static double getAverageOfStudent(Student s) {
		return (s.getP_grade()+s.getN_grade()+s.getA_grade()+s.getW_grade())/4.0;
	}
	
	
	@Override
	public void run() {
		//fetching state of projectManager Object
		projObj =new ProjectManager(GUIController.projObj);

		//List of suggestions
		List<String> suggestion = new LinkedList<String>();
		
		//List of teams 
		List<NewTeam> teams = new LinkedList<>();
		int success = 0;
		double sd = 0.0;
		int counter = 10;
		
		//Add Teams from ProjectManager object in teams List
		for(Team t : projObj.teams.values()) {
			//Team tobj = new Team(t);
			ArrayList<String> sids = t.getStudentIDs();
			LinkedList<StuObj> students1 = new LinkedList<>();
			students1.clear();
			for(String s : sids) {
				Student snew = projObj.students.get(s);
				StuObj temp = new StuObj(snew);
				students1.add(temp);
			}
			teams.add(new NewTeam(students1));
		}
		
		
		//Sorting teams list
		teams.sort(NewTeam.skillComparator);
		suggestion.clear();
		
		//Setting indexes
		int stu1_idx = 0;
		int stu2_idx = 0;
		int team_idx = 0;
		boolean exit = false;
		NewTeam team_low, team_high;
		
		while(team_idx <= 1){
			stu1_idx = 0; stu2_idx = 0;
			team_low = teams.get(team_idx);
			team_high = teams.get(teams.size()-team_idx-1);
			
			StuObj stu1 = team_high.getStudentWithHighestMarks(stu1_idx);
			StuObj stu2 = team_low.getStudentWithLowestMarks(stu2_idx);
			while(true) {
				if(CheckToSwap(stu1.sobj, stu2.sobj, team_low) && CheckToSwap(stu2.sobj, stu1.sobj, team_high)) {
					String str = stu1.sobj.getStudent_id()+" "+stu2.sobj.getStudent_id()+" "+(stu1.avg-stu2.avg);
					suggestion.add(str);
					success = success + 1;
				}
				stu2_idx++;
				if(stu2_idx == 4 ) {
					stu2_idx = 0;
					stu1_idx++;
				}
				if(stu1_idx == 4) {
					break;
				}
				
				stu1 = team_high.getStudentWithHighestMarks(stu1_idx);
				stu2 = team_low.getStudentWithLowestMarks(stu2_idx);
				
				if(stu1.avg < stu2.avg) {
					break;
				}
			}
			team_idx = team_idx + 1;
		}
		
		suggestion.sort(NewTeam.StringComparator);
		
		for(String s : suggestion) {
			String token[] = s.split(" ");
			if(Double.parseDouble(token[2]) > 0.0)
				System.out.println("SWAP "+token[0]+" & "+token[1]+"; Average Gap = "+token[2]);
		}
		
		
	}		
}
	

