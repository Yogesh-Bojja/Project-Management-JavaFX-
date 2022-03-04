package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.AlertClass;
import model.ProjectManager;
import model.Student;
import model.Team;
import view.Suggestions;
import model.Company;
import model.HashMapComparator;
import model.Owner;
import model.Project;

public class GUIController implements Serializable{

	public static ProjectManager projObj;
	Connection con;
	
	//Company variables
    @FXML private TextField cmp_txt_cname;
    @FXML private TextField cmp_txt_abn;
    @FXML private TextField cmp_txt_url;
    @FXML private TextField cmp_txt_addr;
    @FXML private Button cmp_btn_add_cmp;
    @FXML private TableView<Company> cmp_tblview;
    @FXML private TableColumn<Company, String> cmp_tblr_cid;
    @FXML private TableColumn<Company, String> cmp_tblr_cname;
    @FXML private TableColumn<Company, String> cmp_tblr_abn;
    @FXML private TableColumn<Company, String> cmp_tblr_url;
    @FXML private TableColumn<Company, String> cmp_tblr_addr;

    //Owner variables
    @FXML private Button own_btn_add_own;
    @FXML private TextField own_txt_fname;
    @FXML private TextField own_txt_surname;
    @FXML private TextField own_txt_role;
    @FXML private TextField own_txt_mail;
    @FXML private TextField own_txt_cid;
    @FXML private TableView<Owner> own_tblview;
    @FXML private TableColumn<Owner, String> own_tblr_oid;
    @FXML private TableColumn<Owner, String> own_tblr_fname;
    @FXML private TableColumn<Owner, String> own_tblr_sname;
    @FXML private TableColumn<Owner, String> own_tblr_role;
    @FXML private TableColumn<Owner, String> own_tblr_mail;
    @FXML private TableColumn<Owner, String> own_tblr_cid;

    //Project variables
    @FXML private TextField prj_txt_title;
    @FXML private TextField prj_txt_desc;
    @FXML private TextField prj_txt_oid;
    @FXML private TextField prj_txt_p;
    @FXML private TextField prj_txt_n;
    @FXML private TextField prj_txt_a;
    @FXML private TextField prj_txt_w;
    @FXML private Button prj_btn_add_prj;
    @FXML private TableView<Project> prj_tblView;
    @FXML private TableColumn<Project, String> prj_tblr_pid;
    @FXML private TableColumn<Project, String> prj_tblr_title;
    @FXML private TableColumn<Project, String> prj_tblr_desc;
    @FXML private TableColumn<Project, String> prj_tblr_oid;
    @FXML private TableColumn<Project, Integer> prj_tblr_p;
    @FXML private TableColumn<Project, Integer> prj_tblr_n;
    @FXML private TableColumn<Project, Integer> prj_tblr_a;
    @FXML private TableColumn<Project, Integer> prj_tblr_w;
    
    //Students variables
    @FXML private TextField stu_txt_import;
    @FXML private Button stu_btn_import;
    @FXML private TextField stu_txt_sid;
    @FXML private TextField stu_txt_c1;
    @FXML private TextField stu_txt_c2;
    @FXML private TextField stu_txt_pers;
    @FXML private Button stu_btn_add_data;
    @FXML private TableView<Student> stu_tableview;
    @FXML private TableColumn<Student, String> stu_tblr_sid;
    @FXML private TableColumn<Student, Integer> stu_tblr_pg;
    @FXML private TableColumn<Student, Integer> stu_tblr_ng;
    @FXML private TableColumn<Student, Integer> stu_tblr_ag;
    @FXML private TableColumn<Student, Integer> stu_tblr_wg;
    @FXML private TableColumn<Student, String> stu_tblr_c1;
    @FXML private TableColumn<Student, String> stu_tblr_c2;
    @FXML private TableColumn<Student, String> stu_tblr_pers;
    
    //project preferences
    @FXML private TextField pre_txt_sid;
    @FXML private TextField pre_txt_pid;
    @FXML private TextField pre_txt_pref;
    @FXML private Button pre_btn_add_pref;
    @FXML private TableView<Student> pre_tblview;
    @FXML private TableColumn<Student, String> pre_tblr_sid;
    @FXML private TableColumn<Student, String> pre_tblr_pref;

    //Top projects
    @FXML private Button tp_btn_pop;
    @FXML private TextArea tp_txt;
    
    //Team metrics
    @FXML private TextField txt_t1_m1;
    @FXML private TextField txt_t1_m2;
    @FXML private TextField txt_t1_m3;
    @FXML private TextField txt_t1_m4;
    @FXML private CheckBox cb_t1_m1;
    @FXML private CheckBox cb_t1_m2;
    @FXML private CheckBox cb_t1_m3;
    @FXML private CheckBox cb_t1_m4;
    @FXML private TextField txt_t2_m1;
    @FXML private TextField txt_t2_m2;
    @FXML private TextField txt_t2_m3;
    @FXML private TextField txt_t2_m4;
    @FXML private CheckBox cb_t2_m1;
    @FXML private CheckBox cb_t2_m2;
    @FXML private CheckBox cb_t2_m3;
    @FXML private CheckBox cb_t2_m4;
    @FXML private TextField txt_t3_m1;
    @FXML private TextField txt_t3_m2;
    @FXML private TextField txt_t3_m3;
    @FXML private TextField txt_t3_m4;
    @FXML private CheckBox cb_t3_m1;
    @FXML private CheckBox cb_t3_m2;
    @FXML private CheckBox cb_t3_m3;
    @FXML private CheckBox cb_t3_m4;
    @FXML private TextField txt_t4_m1;
    @FXML private TextField txt_t4_m2;
    @FXML private TextField txt_t4_m3;
    @FXML private TextField txt_t4_m4;
    @FXML private CheckBox cb_t4_m1;
    @FXML private CheckBox cb_t4_m2;
    @FXML private CheckBox cb_t4_m3;
    @FXML private CheckBox cb_t4_m4;
    @FXML private TextField txt_t5_m1;
    @FXML private TextField txt_t5_m2;
    @FXML private TextField txt_t5_m3;
    @FXML private TextField txt_t5_m4;
    @FXML private CheckBox cb_t5_m1;
    @FXML private CheckBox cb_t5_m2;
    @FXML private CheckBox cb_t5_m3;
    @FXML private CheckBox cb_t5_m4;
    @FXML private TextField team_sid;
    @FXML private Button team_btn_add;
    @FXML private Button team_btn_swap;
    @FXML private BarChart<String, Double> bc12;
    @FXML private Label sd12;
    @FXML private BarChart<String, Double> bcac;
    @FXML private Label sdac;
    @FXML private BarChart<String, Double> bcsg;
    @FXML private Label sdsg;
    @FXML private Button team_btn_refresh;
    @FXML private Button team_btn_undo;
    @FXML private Button team_btn_commit;
    
    //Assign projects
    @FXML private TextField assign_txt_tid;
    @FXML private TextField assign_txt_pid;
    @FXML private Button assign_btn_assign_project;
    @FXML private Button assign_btn_populate_team;
    @FXML private TableView<Team> assign_tblview;
    @FXML private TableColumn<Team, String> assign_tblr_tid;
    @FXML private TableColumn<Team, String> assign_tblr_pid;
    @FXML private TableColumn<Team, String> assign_tblr_memb;
    @FXML private CategoryAxis bc1_ca;
    @FXML private NumberAxis bc1_na;
    @FXML private CategoryAxis bc2_ca;
    @FXML private NumberAxis bc2_na;
    @FXML private CategoryAxis bc3_ca;
    @FXML private NumberAxis bc3_na;

    
    // Initialize methods - performs deserialization and retrieves the state of object
    @FXML
    public void initialize() throws ClassNotFoundException, SQLException {
    	Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:ProjectCRS.db");
		System.out.println("Connected");
    	projObj = new ProjectManager();
		try {
			projObj = loadDeserialization("data.ser");
			System.out.println("successfully deserialized !!!");
		} catch (Exception e1) {
			System.out.println("No file for deserialization !!!");
		} 
		try {
			loadGUIDeserialization("GUI.ser");
		} catch (Exception e1) {
			System.out.println("No GUI file for deserialization !!!");
		} 

		display_company_table();
		display_owner_table();
		display_project_table();
		show_student_table();
		display_preferences_table();
    	projObj.display_barchart1(bc12, bc1_ca, bc1_na, sd12);
    	projObj.display_barchart2(bcac, bc2_ca, bc2_na, sdac);
    	projObj.display_barchart3(bcsg, bc3_ca, bc1_na, sdsg);
    	if(projObj.team1.size() == 4 && projObj.team2.size() == 4 && projObj.team3.size() == 4 && projObj.team4.size() == 4 && projObj.team5.size() == 4) {
    		team_btn_swap.setDisable(false);
    	}
    	else {
    		team_btn_swap.setDisable(true);
    	}    	
    }
    
    // Adds company information to SQLITE database and companies HashMap
    @FXML
    void add_company(ActionEvent event) {
    	String cName = cmp_txt_cname.getText();
    	String abn = cmp_txt_abn.getText();
    	if(abn.length() != 10) {
    		AlertClass.giveALert("ABN Error", "ABN should be of length 10");
    		return;
    	}
        String url = cmp_txt_url.getText();
        String addr = cmp_txt_addr.getText();
        
        Company cobj = new Company(cName, abn, url, addr, projObj.company_id_count++);

		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "insert into companies (cid, cname, abn, url, address) values ('"+cobj.getID()+"', '"+cName+"', '"+abn+"', '"+url+"', '"+addr+"')";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			AlertClass.giveALert("JDBC Exception", "Exception while inserting !!!");
			projObj.company_id_count--;
			return;
		}

        projObj.companies.put(cobj.getID(), cobj);
        display_company_table();
		cmp_txt_cname.setText("");
		cmp_txt_abn.setText("");
		cmp_txt_url.setText("");
		cmp_txt_addr.setText("");
		
		
    }

    // Adds owner information to SQLITE database and owners HashMap
    @FXML
    void add_owner(ActionEvent event) {
    	String fName = own_txt_fname.getText();
    	String surname = own_txt_surname.getText();
        String role = own_txt_role.getText();
        String mail = own_txt_mail.getText();
        String cid = own_txt_cid.getText();
        
        if((!projObj.companies.containsKey(cid)) || (projObj.companies == null)) {
        	AlertClass.giveALert("Inserting Error", "Specified company ID not present !!!");
        	return;
        }
        
        Owner oobj = new Owner(fName, surname, role, mail, cid, projObj.owner_id_count++);

		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "insert into owners (oid, fname, surname, role, mail, cid) values ('"+oobj.getOwner_id()+"', '"+fName+"', '"+surname+"', '"+role+"', '"+mail+"', '"+cid+"')";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			AlertClass.giveALert("JDBC Exception", "Exception while inserting !!!");
			projObj.owner_id_count--;
			return;
		}

        projObj.owners.put(oobj.getOwner_id(), oobj);

        display_owner_table();
		own_txt_fname.setText("");
		own_txt_surname.setText("");
		own_txt_role.setText("");
		own_txt_mail.setText("");
		own_txt_cid.setText("");
		
    }

    // Adds Project information to SQLITE database and projects HashMap
    @FXML
    void add_project(ActionEvent event) {
    	String title = prj_txt_title.getText();
    	String desc = prj_txt_desc.getText();
        String oid = prj_txt_oid.getText();
        int p = Integer.parseInt(prj_txt_p.getText());
        int n = Integer.parseInt(prj_txt_n.getText());
        int a = Integer.parseInt(prj_txt_a.getText());
        int w = Integer.parseInt(prj_txt_w.getText());
        
        if((!projObj.owners.containsKey(oid)) || (projObj.owners == null)) {
        	AlertClass.giveALert("Inserting Error", "Specified owner ID not present !!!");
        	return;
        }
        if(p > 4 || p < 1) {
        	AlertClass.giveALert("Score Exception", "Skill of Programming should be in range 1-4 !!!");
        	return;
        }
        if(n == p) {
        	AlertClass.giveALert("Score Exception", "Ranks cannot be same !!!");
        	return;        	
        }
        if(n > 4 || n < 1) {
        	AlertClass.giveALert("Score Exception", "Skill of Networking should be in range 1-4 !!!");
        	return;
        }
        if(a == p || a == n) {
        	AlertClass.giveALert("Score Exception", "Ranks cannot be same !!!");
        	return;
        }
        if(a > 4 || a < 1) {
        	AlertClass.giveALert("Score Exception", "Skill of Analytics should be in range 1-4 !!!");
        	return;
        }
        if(w == p || w == n || w == a) {
        	AlertClass.giveALert("Score Exception", "Ranks cannot be same !!!");
        	return;
        }
        if(w > 4 || w < 1) {
        	AlertClass.giveALert("Score Exception", "Skill of Web should be in range 1-4 !!!");
        	return;
        }
        
        Project pobj = new Project(title, desc, oid, p, n, a, w, projObj.project_id_count++);

		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "insert into projects (pid, title, desc, oid, p, n, a, w) values ('"+pobj.getProject_id()+"', '"+title+"', '"+desc+"', '"+oid+"', "+p+", "+n+", "+a+", "+w+")";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			AlertClass.giveALert("JDBC Exception", "Exception while inserting !!!");
			projObj.project_id_count--;
			return;
		}

        projObj.projects.put(pobj.getProject_id(), pobj);
        display_project_table();
		prj_txt_title.setText("");
		prj_txt_desc.setText("");
		prj_txt_oid.setText("");
		prj_txt_p.setText("");
		prj_txt_n.setText("");
		prj_txt_a.setText("");
		prj_txt_w.setText("");		

    }
    
    // Imports data from students.txt file(from milestone 1) and stores in SQLITE DB and students HashMap
    @FXML
    void import_students(ActionEvent event) {
    	if(projObj.students_imported_flag == 1) {
    		stu_txt_import.setText("Students already imported !!!");
    		return;
    	}
    	
    	Statement stmt;
    	String filename = stu_txt_import.getText();
    	File student_obj = new File(filename);
    	Scanner fileReader;
		try {
			fileReader = new Scanner(student_obj);
			while(fileReader.hasNext()) {
				String student[] = fileReader.nextLine().split("\t");
				Student sobj = new Student(student[0], student[2], student[4], student[6], student[8]);
				stmt = con.createStatement();
				String sql = "insert into students (sid, pgrade, ngrade, agrade, wgrade) values ('"+sobj.getStudent_id()+"', "+student[2]+", "+student[4]+", "+student[6]+", "+student[8]+")";
				stmt.executeUpdate(sql);
				projObj.students.put(student[0], sobj);
			}
			fileReader.close();
			show_student_table();
			projObj.students_imported_flag = 1;
			
		} catch (FileNotFoundException e) {
			AlertClass.giveALert("Import Exception ", "Students.txt not imported !!!");
			return;
		} catch (SQLException e) {
			AlertClass.giveALert("Import Exception ", "Exception in create statement ");
			return;
		}
		
    }

    // Updating students info conflicts and student personality
    @FXML
    void add_student_data(ActionEvent event) {
    	String sid = stu_txt_sid.getText();
    	if(!projObj.students.containsKey(sid)) {
    		AlertClass.giveALert("Student Not Found Exception ", "Student ID not present");
    		return;
    	}
    	String Conflict1 = stu_txt_c1.getText();
    	String Conflict2 = stu_txt_c2.getText();
    	if(!projObj.students.containsKey(Conflict1) || !projObj.students.containsKey(Conflict2)) {
			AlertClass.giveALert("ID not present Exception", "Student ID not present!!!");
			return;
    	}
		if(Conflict1.equals(sid) || Conflict2.equals(sid)) {
			AlertClass.giveALert("ID Exception", "Conflict ID cannot be SID!!!");
			return;
    	}		
		if(Conflict1.equals(Conflict2)){
			AlertClass.giveALert("ID Exception", "Conlfict cannot be same!!!");
			return;
    	}
    	String Pers = stu_txt_pers.getText();
    	Student sobj = projObj.students.get(sid);
    	if(sobj.getPersonality() != null){
    		String p = sobj.getPersonality();
    		if(p.equals("A")) {
    			projObj.A_count--;
    		}
    		else if(p.equals("B")) {
    			projObj.B_count--;
    		}
    		else  if (p.equals("C")) {
    			projObj.C_count--;
    		}
    		else if( p.equals("D")) {
    			projObj.D_count--;
    		}
    	}
     	if(Pers.equals("A")) {
			if(projObj.A_count >= 5) {
				AlertClass.giveALert("StudentInfoException", "Count of A can't be more than 4 !!!");
				return;
			}
			projObj.A_count++;
		}
		else if(Pers.equals("B")) {
			if(projObj.B_count >= 5) {
				AlertClass.giveALert("StudentInfoException", "Count of B can't be more than 4 !!!");
				return;
			}
			projObj.B_count++;
		}
		else if(Pers.equals("C")) {
			if(projObj.C_count >= 5) {
				AlertClass.giveALert("StudentInfoException", "Count of C can't be more than 4 !!!");
				return;
			}			projObj.C_count++;
		}
		else if(Pers.equals("D")) {
			if(projObj.D_count >= 5) {
				AlertClass.giveALert("StudentInfoException", "Count of D can't be more than 4 !!!");
				return;
			}			projObj.D_count++;
		}
		else {
			AlertClass.giveALert("StudentInfoException", "Personality has to be A/B/C/D !!!");
			return;
		}
 		Statement stmt;
		try {
			stmt = con.createStatement();
			String sql = "UPDATE students SET conflict1 = '"+Conflict1+"', conflict2 = '"+Conflict2+"', personality = '"+Pers+"'  WHERE sid = '"+sid+"'";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			AlertClass.giveALert("Insert Exception", "Exception while inserting !!!!");
			return;
		}
		projObj.students.get(sid).setPersonalityAndConflict(Pers, Conflict1,Conflict2);
		show_student_table();  	
    }

    // Adding project preferences to the student object and storing it in SQLITE DB 
    @FXML
    void add_preferences(ActionEvent event) {
    	String sid = pre_txt_sid.getText();
    	String pid = pre_txt_pid.getText();
    	Integer pref = Integer.parseInt(pre_txt_pref.getText());
    	
    	if(!projObj.students.containsKey(sid)) {
    		AlertClass.giveALert("Exception", "Student not found !!!");
    		return;
    	}
    	if(!projObj.projects.containsKey(pid)) {
    		AlertClass.giveALert("Exception", "Project not found !!!");
    		return;
    	}
		if(pref<0 || pref>4) {
    		AlertClass.giveALert("Exception", "Enter preference between 1 to 4 !!!");
    		return;
    	}
		HashMap<String, ArrayDeque> preferences = projObj.students.get(sid).getProject_preferences();
		if(preferences == null) {
			ArrayDeque stack = new ArrayDeque();
			stack.push(pref);
			preferences = new LinkedHashMap<String, ArrayDeque>();
			preferences.put(pid, stack);
		}
		else {
			if(preferences.containsKey(pid)) {
				preferences.get(pid).push(pref);
			}
			else {
				ArrayDeque stack = new ArrayDeque();
				stack.push(pref);
				preferences.put(pid,stack);
			}
			
		}
		String str = "";
		for(String key : preferences.keySet()) {
			ArrayDeque value = preferences.get(key);
			str = str + "\t" + key + "\t" + value;
		}
		projObj.students.get(sid).setStrpref(str);
		Statement stmt = null;
		try {
			stmt = con.createStatement();
			String sql = "insert into preferences (sid, preference) values ('"+sid+"', '"+str+"')";
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			String sql = "UPDATE preferences SET preference = '"+str+"' WHERE sid = '"+sid+"'";
			try {
				stmt.executeUpdate(sql);
			} catch (SQLException e1) {
				AlertClass.giveALert("Updatation error", "Cannot update");
				return;
			}
		}
		projObj.students.get(sid).setProject_preferences(preferences);

		display_preferences_table();
    }

    // Finding scores of projects then sorting them from highest to lowest and displaying it in Top projects tab.
    @FXML
    void populate(ActionEvent event) {
    	for(Project pobj : projObj.projects.values()) {
			String pid = pobj.getProject_id();
			int score = 0;
			for(Student sobj : projObj.students.values()) {
				if(sobj.getProject_preferences() != null) {
					if(sobj.getProject_preferences().containsKey(pid)) {
						score = score + (Integer)sobj.getProject_preferences().get(pid).peek();
					}
				}
			}
			pobj.setProject_score(score);
		}
		
    	List <Project> selectedProjectsList = new LinkedList<Project>();
		int i = 0;
		for(Project p : projObj.projects.values()) {
			selectedProjectsList.add(p);
		}
		Collections.sort(selectedProjectsList, new HashMapComparator());
		Iterator<Project> itr = selectedProjectsList.iterator();
		String str = "Ranking of projects are :\n";
		while(itr.hasNext()) {
			Project p = itr.next();
			str = str+ p.getProject_id()+"\t"+p.getProject_score()+"\n";
			
		}

		tp_txt.setText(str);
    }


    // Adding student to a team
    @FXML
    void add_member(ActionEvent event) {

    	//get_checked_boxes() returns arraylist of all selected checkboxes.
    	ArrayList<CheckBox> cb = get_checked_boxes(); 
    	
    	//get_text_fields() returns arraylist of all textfields linked with selected checkboxes.
    	ArrayList<TextField> tf = get_text_fields();
    	
    	if(cb.size()!=1) {
    		AlertClass.giveALert("Exception", "Check only one box !!!");
    		return;
    	}
    	String sid = team_sid.getText();
    	if(!projObj.students.containsKey(sid)) {
    		AlertClass.giveALert("Exception", "Student ID not present !!!");
    		return;
    	}
    	
    	//isValidateMember() checks if the Student ID is valid to be added to a team. for eg. two members with 'A' personality cannot exist.
    	boolean check = isValidateMember(sid, cb.get(0));
    	if(!check) {
    		return;
    	}
    	
    	CheckBox cbobj = cb.get(0);
    	TextField tfobj = tf.get(0);
    	if(cbobj.getId().contains("t1")) {
    		projObj.team1.add(sid);
    		projObj.teams.get(projObj.team1_id).setStudentIDs(projObj.team1);
    	}
    	else if(cbobj.getId().contains("t2")) {
    		projObj.team2.add(sid);
    		projObj.teams.get(projObj.team2_id).setStudentIDs(projObj.team2);    		
    	}
    	else if(cbobj.getId().contains("t3")) {
    		projObj.team3.add(sid);
    		projObj.teams.get(projObj.team3_id).setStudentIDs(projObj.team3);
    	}
    	else if(cbobj.getId().contains("t4")) {
    		projObj.team4.add(sid);
    		projObj.teams.get(projObj.team4_id).setStudentIDs(projObj.team4);
    	}
    	else if(cbobj.getId().contains("t5")) {
    		projObj.team5.add(sid);
    		projObj.teams.get(projObj.team5_id).setStudentIDs(projObj.team5);
    	}
    	
    	tfobj.setText(sid);
    	cbobj.setSelected(false);
    	team_sid.setText("");
    	projObj.display_barchart1(bc12, bc1_ca, bc1_na, sd12);
    	projObj.display_barchart2(bcac, bc2_ca, bc2_na, sdac);
    	projObj.display_barchart3(bcsg, bc3_ca, bc1_na, sdsg);
    	
    	if(projObj.team1.size() == 4 && projObj.team2.size() == 4 && projObj.team3.size() == 4 && projObj.team4.size() == 4 && projObj.team5.size() == 4) {
    		team_btn_swap.setDisable(false);
    	}
    }
    
    //Swapping members from one team to another.
    @FXML
    void swap_member(ActionEvent event) {
    	
    	ArrayList<CheckBox> cb = get_checked_boxes();
    	ArrayList<TextField> tf = get_text_fields();

    	if(cb.size()!=2) {
    		AlertClass.giveALert("Exception", "Check only 2 IDs !!!");
    		return;
    	}
    	
    	String t1_id = cb.get(0).getId();
    	String t2_id = cb.get(1).getId();
    	if((t1_id.contains("t1") && t2_id.contains("t1")) ||
    			(t1_id.contains("t2") && t2_id.contains("t2")) || (t1_id.contains("t3") && t2_id.contains("t3")) ||
    			(t1_id.contains("t4") && t2_id.contains("t4")) || (t1_id.contains("t5") && t2_id.contains("t5"))){
    		AlertClass.giveALert("Warning", "Cannot swap within a team !!!");
    		cb.get(0).setSelected(false);
    		cb.get(1).setSelected(false);
    		return;
    	}
    	
    	String sid1 = tf.get(0).getText();
    	String sid2 = tf.get(1).getText();
    	
    	Team t1 = null, t2 = null;
    	if(cb.get(0).getId().contains("t1")) {
    		t1 = projObj.teams.get(projObj.team1_id);
    		t1_id = projObj.team1_id; 
    	}
    	else if(cb.get(0).getId().contains("t2")) {
    		t1 = projObj.teams.get(projObj.team2_id);    		
    		t1_id = projObj.team2_id; 
    	}
    	else if(cb.get(0).getId().contains("t3")) {
    		t1 = projObj.teams.get(projObj.team3_id);    		
    		t1_id = projObj.team3_id; 
    	}
    	else if(cb.get(0).getId().contains("t4")) {
    		t1 = projObj.teams.get(projObj.team4_id);
    		t1_id = projObj.team4_id;     		
    	}
    	else if(cb.get(0).getId().contains("t5")) {
    		t1 = projObj.teams.get(projObj.team5_id);    		
    		t1_id = projObj.team5_id;     
    	}

    	if(cb.get(1).getId().contains("t1")) {
    		t2 = projObj.teams.get(projObj.team1_id);
    		t2_id = projObj.team1_id; 
    	}
    	else if(cb.get(1).getId().contains("t2")) {
    		t2 = projObj.teams.get(projObj.team2_id);    		
    		t2_id = projObj.team2_id; 
    	}
    	else if(cb.get(1).getId().contains("t3")) {
    		t2 = projObj.teams.get(projObj.team3_id);    		
    		t2_id = projObj.team3_id;   
    	}
    	else if(cb.get(1).getId().contains("t4")) {
    		t2 = projObj.teams.get(projObj.team4_id);    		
    		t2_id = projObj.team4_id; 
    	}
    	else if(cb.get(1).getId().contains("t5")) {
    		t2 = projObj.teams.get(projObj.team5_id);    		
    		t2_id = projObj.team5_id; 
    	}
    	
    	if(t1 != null && t2 != null) {
    		
    		boolean chk1 = isValidSwap(projObj.students.get(sid2), projObj.students.get(sid1), t1);
    		boolean chk2 = isValidSwap(projObj.students.get(sid1), projObj.students.get(sid2), t2);
    		
    		if(chk1 == true && chk2 == true) {  
    		   	Thread thread = new Suggestions();
    	    	if(!thread.isAlive()) {
    	    		System.out.println("Suggestions !!!");
    	    		thread.start();
    	    	}
    	 
    			HashMap<String, Team> tempObj = new HashMap<>();
	    		tempObj.put(projObj.team1_id, new Team(projObj.teams.get(projObj.team1_id)));
	    		tempObj.put(projObj.team2_id, new Team(projObj.teams.get(projObj.team2_id)));
	    		tempObj.put(projObj.team3_id, new Team(projObj.teams.get(projObj.team3_id)));
	    		tempObj.put(projObj.team4_id, new Team(projObj.teams.get(projObj.team4_id)));
	    		tempObj.put(projObj.team5_id, new Team(projObj.teams.get(projObj.team5_id)));
	  
	    		projObj.stack.push(tempObj);
	
	    		HashMap<TextField, String> tempgui = new HashMap<TextField, String>();
	    		tempgui.put(tf.get(0), sid1);
	    		tempgui.put(tf.get(1), sid2);
	    		projObj.GUIstack.push(tempgui);
	    		
	    		t1.getStudentIDs().remove(sid1);
		    	t1.getStudentIDs().add(sid2);
		    	t2.getStudentIDs().remove(sid2);
		    	t2.getStudentIDs().add(sid1);
    		}
    		else {
    			AlertClass.giveALert("Cannot Swap", "Conflicts or Personality imbalance occured !!!");
    	    	cb.get(0).setSelected(false);
    	    	cb.get(1).setSelected(false);
    			return;
    		}
    	}
    	projObj.display_barchart1(bc12, bc1_ca, bc1_na, sd12);
    	projObj.display_barchart2(bcac, bc2_ca, bc2_na, sdac);
    	projObj.display_barchart3(bcsg, bc3_ca, bc3_na, sdsg);
    	
    	tf.get(1).setText(sid1);
    	tf.get(0).setText(sid2);
    	cb.get(0).setSelected(false);
    	cb.get(1).setSelected(false);
    }
    
    //checks if the swap is valid
    private boolean isValidSwap(Student toCheck, Student exist, Team team) {
		int has_A = 0;
		boolean has_conflict = false;
		Set c = new TreeSet();
		
		for(String sid : team.getStudentIDs()) {
			Student temp = projObj.students.get(sid);
			if(!temp.equals(exist)) {
				if(temp.getPersonality().equals("A")) {
					has_A++;
				}
				if(temp.getConflict1().equals(toCheck.getStudent_id()) || temp.getConflict2().equals(toCheck.getStudent_id())) {
					has_conflict = true;
				}			
				c.add(temp.getPersonality());
			}
		}
		c.add(toCheck.getPersonality());
		if(toCheck.getPersonality().equals("A")) {
			has_A++;
		}
		if(has_A==1 && has_conflict == false && c.size() >=3) {
			return true;
		}
		else {
			return false;
		}
	}

    // undo and store previous state as newest
    @FXML void undo(ActionEvent event) {
    	if(projObj.stack != null && !projObj.stack.isEmpty()) {
    		projObj.teams.clear();
    		
    		HashMap<String, Team> state = projObj.stack.pop();
    		projObj.teams.put(projObj.team1_id, state.get(projObj.team1_id));
    		projObj.teams.get(projObj.team1_id).setStudentIDs(state.get(projObj.team1_id).getStudentIDs());
    		projObj.teams.put(projObj.team2_id, state.get(projObj.team2_id));
    		projObj.teams.get(projObj.team2_id).setStudentIDs(state.get(projObj.team2_id).getStudentIDs());
    		projObj.teams.put(projObj.team3_id, state.get(projObj.team3_id));
    		projObj.teams.get(projObj.team3_id).setStudentIDs(state.get(projObj.team3_id).getStudentIDs());
    		projObj.teams.put(projObj.team4_id, state.get(projObj.team4_id));
    		projObj.teams.get(projObj.team4_id).setStudentIDs(state.get(projObj.team4_id).getStudentIDs());
    		projObj.teams.put(projObj.team5_id, state.get(projObj.team5_id));
    		projObj.teams.get(projObj.team5_id).setStudentIDs(state.get(projObj.team5_id).getStudentIDs());

    		HashMap<TextField, String> obj = projObj.GUIstack.pop();
    		for(TextField t : obj.keySet()) {
    			t.setText(obj.get(t));
    		}
    		
    	}
    	else {
    		AlertClass.giveALert("Info", "Stack Empty !!!");
    		return;
    	}
    	projObj.display_barchart1(bc12, bc1_ca, bc1_na, sd12);
    	projObj.display_barchart2(bcac, bc2_ca, bc2_na, sdac);
    	projObj.display_barchart3(bcsg, bc3_ca, bc1_na, sdsg);	
    }
    
    @FXML void commit(ActionEvent event) {
    	projObj.stack.clear();
    	projObj.GUIstack.clear();
    	AlertClass.giveALert("Info", "Commit successfull !!!");
    }
    
    //returns true of the student ID is legat to be added to a team.
    //Checks for students conflicts
    //checks for Leader conflict
    //checks if team is full
    //checks for no leader
    private boolean isValidateMember(String sid, CheckBox cb) {
    	ArrayList <String> al = null;
    	if(!projObj.teams.get(projObj.team1_id).checkIfArrayNull()) {
    		if(projObj.teams.get(projObj.team1_id).getStudentIDs().contains(sid)) {
    			AlertClass.giveALert("Warning", "Member already Present !!!");
    			return false;
    		}
    	}
    	if(!projObj.teams.get(projObj.team2_id).checkIfArrayNull()) {
    		if(projObj.teams.get(projObj.team2_id).getStudentIDs().contains(sid)) {
    			AlertClass.giveALert("Warning", "Member already Present !!!");
    			return false;
    		}
    	}
    	if(!projObj.teams.get(projObj.team3_id).checkIfArrayNull()) {
    		if(projObj.teams.get(projObj.team3_id).getStudentIDs().contains(sid)) {
    			AlertClass.giveALert("Warning", "Member already Present !!!");
    			return false;
    		}
    	}
    	if(!projObj.teams.get(projObj.team4_id).checkIfArrayNull()) {
    		if(projObj.teams.get(projObj.team4_id).getStudentIDs().contains(sid)) {
    			AlertClass.giveALert("Warning", "Member already Present !!!");
    			return false;
    		}
    	}
    	if(!projObj.teams.get(projObj.team5_id).checkIfArrayNull()) {
    		if(projObj.teams.get(projObj.team5_id).getStudentIDs().contains(sid)) {
    			AlertClass.giveALert("Warning", "Member already Present !!!");
    			return false;
    		}
    	}
    	
    	if(cb.getId().contains("t1")) {
    		al = projObj.teams.get(projObj.team1_id).getStudentIDs();

    	}
    	else if(cb.getId().contains("t2")) {
    		al = projObj.teams.get(projObj.team2_id).getStudentIDs();
    	} 
    	else if(cb.getId().contains("t3")) {
    		al = projObj.teams.get(projObj.team3_id).getStudentIDs();
    	} 
    	else if(cb.getId().contains("t4")) {
    		al = projObj.teams.get(projObj.team4_id).getStudentIDs();
    	} 
    	else if(cb.getId().contains("t5")) {
    		al = projObj.teams.get(projObj.team5_id).getStudentIDs();
    	} 
    	if(al == null) {
    		return true;
    	}
    	if(al.size()>=4) {
    		AlertClass.giveALert("Warning", "Cannot add member. Team full !!!");
    		return false;
    	}
    	boolean can_add = true;
    	boolean has_A = false;
    	Set pCount = new TreeSet();
    	pCount.add(projObj.students.get(sid).getPersonality());
    	for(String s : al) {
    		if(projObj.students.get(sid).getPersonality().equals("A")) {
    			if(projObj.students.get(s).getPersonality().equals("A")) {
    		    	AlertClass.giveALert("Warning", "Cannot add member to team. Already a team leader is present !!!");
    				can_add = false;
    				return false;
    			}
   			}
    		if(projObj.students.get(s).getConflict1().equals(sid) || projObj.students.get(s).getConflict2().equals(sid)) {
    			AlertClass.giveALert("Warning", "Conflict occured, cannot add member !!!");
    			can_add = false;
    			return false;
    		}
    		if(projObj.students.get(s).getPersonality().equals("A"))
    			has_A = true;
    		pCount.add(projObj.students.get(s).getPersonality());
    	}
    	if(has_A == false && !projObj.students.get(sid).getPersonality().equals("A") && al.size()>=3) {
    		AlertClass.giveALert("Warning", "Cannot Add. No leader present if team formed !!!");
    		return false;
    	}
    	if(al.size()>=3 && pCount.size() < 3) {
    		AlertClass.giveALert("Warning", "Personality imbalance !!!");
    		return false;
    	}
    	return can_add;
	}


    //returs arraylist of selected textfiled linked to checkboxes
	private ArrayList<TextField> get_text_fields(){
    	ArrayList<TextField> l = new ArrayList<TextField>();
    	if(cb_t1_m1.isSelected()) {
    		l.add(txt_t1_m1);
    	}
    	if(cb_t1_m2.isSelected()) {
    		l.add(txt_t1_m2);
    	}
    	if(cb_t1_m3.isSelected()) {
    		l.add(txt_t1_m3);
    	}
    	if(cb_t1_m4.isSelected()) {
    		l.add(txt_t1_m4);
    	}
    	if(cb_t2_m1.isSelected()) {
    		l.add(txt_t2_m1);
    	}
    	if(cb_t2_m2.isSelected()) {
    		l.add(txt_t2_m2);
    	}
    	if(cb_t2_m3.isSelected()) {
    		l.add(txt_t2_m3);
    	}
    	if(cb_t2_m4.isSelected()) {
    		l.add(txt_t2_m4);
    	}
    	if(cb_t3_m1.isSelected()) {
    		l.add(txt_t3_m1);
    	}
    	if(cb_t3_m2.isSelected()) {
    		l.add(txt_t3_m2);
    	}
    	if(cb_t3_m3.isSelected()) {
    		l.add(txt_t3_m3);
    	}
    	if(cb_t3_m4.isSelected()) {
    		l.add(txt_t3_m4);
    	}
    	if(cb_t4_m1.isSelected()) {
    		l.add(txt_t4_m1);
    	}
    	if(cb_t4_m2.isSelected()) {
    		l.add(txt_t4_m2);
    	}
    	if(cb_t4_m3.isSelected()) {
    		l.add(txt_t4_m3);
    	}
    	if(cb_t4_m4.isSelected()) {
    		l.add(txt_t4_m4);
    	}
    	if(cb_t5_m1.isSelected()) {
    		l.add(txt_t5_m1);
    	}
    	if(cb_t5_m2.isSelected()) {
    		l.add(txt_t5_m2);
    	}
    	if(cb_t5_m3.isSelected()) {
    		l.add(txt_t5_m3);
    	}
    	if(cb_t5_m4.isSelected()) {
    		l.add(txt_t5_m4);
    	}
    	return l;
    }
	
	//returns arraylist of selected checkboxes
    private ArrayList<CheckBox> get_checked_boxes() {
    	ArrayList<CheckBox> l = new ArrayList<CheckBox>();
    	if(cb_t1_m1.isSelected()) {
    		l.add(cb_t1_m1);
    	}
    	if(cb_t1_m2.isSelected()) {
    		l.add(cb_t1_m2);
    	}
    	if(cb_t1_m3.isSelected()) {
    		l.add(cb_t1_m3);
    	}
    	if(cb_t1_m4.isSelected()) {
    		l.add(cb_t1_m4);
    	}
    	if(cb_t2_m1.isSelected()) {
    		l.add(cb_t2_m1);
    	}
    	if(cb_t2_m2.isSelected()) {
    		l.add(cb_t2_m2);
    	}
    	if(cb_t2_m3.isSelected()) {
    		l.add(cb_t2_m3);
    	}
    	if(cb_t2_m4.isSelected()) {
    		l.add(cb_t2_m4);
    	}
    	if(cb_t3_m1.isSelected()) {
    		l.add(cb_t3_m1);
    	}
    	if(cb_t3_m2.isSelected()) {
    		l.add(cb_t3_m2);
    	}
    	if(cb_t3_m3.isSelected()) {
    		l.add(cb_t3_m3);
    	}
    	if(cb_t3_m4.isSelected()) {
    		l.add(cb_t3_m4);
    	}
    	if(cb_t4_m1.isSelected()) {
    		l.add(cb_t4_m1);
    	}
    	if(cb_t4_m2.isSelected()) {
    		l.add(cb_t4_m2);
    	}
    	if(cb_t4_m3.isSelected()) {
    		l.add(cb_t4_m3);
    	}
    	if(cb_t4_m4.isSelected()) {
    		l.add(cb_t4_m4);
    	}
    	if(cb_t5_m1.isSelected()) {
    		l.add(cb_t5_m1);
    	}
    	if(cb_t5_m2.isSelected()) {
    		l.add(cb_t5_m2);
    	}
    	if(cb_t5_m3.isSelected()) {
    		l.add(cb_t5_m3);
    	}
    	if(cb_t5_m4.isSelected()) {
    		l.add(cb_t5_m4);
    	}
    	return l;
    }
    
    //Assigns project to a team
    @FXML
    void assign_project(ActionEvent event) {
    	if(projObj.team_id_count >= 6) {
    		AlertClass.giveALert("Exception", "5 teams have been formed already !!!");
    		return;
    	}
    	String tid = assign_txt_tid.getText();
    	String pid = assign_txt_pid.getText();
    	
    	if(projObj.teams.containsKey(tid)) {
    		AlertClass.giveALert("Exception", "Team ID already taken !!!");
    		return;
    	}

  	
    	Team tobj = new Team(tid, null, pid);
    	if(projObj.team_id_count == 1) {
    		projObj.team1_id = tid;
    	}
    	else if(projObj.team_id_count == 2) {
    		projObj.team2_id = tid;
    	}
    	else if(projObj.team_id_count == 3) {
    		projObj.team3_id = tid;
    	}
    	else if(projObj.team_id_count == 4) {
    		projObj.team4_id = tid;
    	}
    	else if(projObj.team_id_count == 5) {
    		projObj.team5_id = tid;
    	}

    	projObj.team_id_count++;
    	projObj.teams.put(tid, tobj);
    	AlertClass.giveALert("Message", "Successfull creation of team !!!");
    }
    
    //displays team info in assign project tab
    @FXML
    void populate_team_table(ActionEvent event) {
    	assign_tblview.refresh();
		List<Team> tl = new ArrayList<Team>();
		for(Team o : projObj.teams.values())
			tl.add(o);
		ObservableList<Team> tlist = FXCollections.observableArrayList(tl);
		assign_tblr_tid.setCellValueFactory(new PropertyValueFactory<Team, String>("teamID"));
		assign_tblr_pid.setCellValueFactory(new PropertyValueFactory<Team, String>("assigned_projID"));
		assign_tblr_memb.setCellValueFactory(new PropertyValueFactory<Team, String>("studentIDs"));
    	
		assign_tblview.setItems(tlist);

    }
    
    //displays students information in Students tab
    private void show_student_table() {
    	stu_tableview.refresh();
    	List<Student> sl = new ArrayList<Student>();
		for(Student s : projObj.students.values())
			sl.add(s);
		ObservableList<Student> slist = FXCollections.observableArrayList(sl);
		stu_tblr_sid.setCellValueFactory(new PropertyValueFactory<Student, String>("student_id"));
		stu_tblr_pg.setCellValueFactory(new PropertyValueFactory<Student, Integer>("P_grade"));
		stu_tblr_ng.setCellValueFactory(new PropertyValueFactory<Student, Integer>("N_grade"));
		stu_tblr_ag.setCellValueFactory(new PropertyValueFactory<Student, Integer>("A_grade"));
		stu_tblr_wg.setCellValueFactory(new PropertyValueFactory<Student, Integer>("W_grade"));
		stu_tblr_c1.setCellValueFactory(new PropertyValueFactory<Student, String>("conflict1"));
		stu_tblr_c2.setCellValueFactory(new PropertyValueFactory<Student, String>("conflict2"));
		stu_tblr_pers.setCellValueFactory(new PropertyValueFactory<Student, String>("personality"));
		
		stu_tableview.setItems(slist);
	}
    
    //displays company information on companies tab
    private void display_company_table() {
		List<Company> cl = new ArrayList<Company>();
		for(Company o : projObj.companies.values())
			cl.add(o);
		ObservableList<Company> clist = FXCollections.observableArrayList(cl);
		cmp_tblr_cid.setCellValueFactory(new PropertyValueFactory<Company, String>("ID"));
		cmp_tblr_cname.setCellValueFactory(new PropertyValueFactory<Company, String>("company_name"));
		cmp_tblr_abn.setCellValueFactory(new PropertyValueFactory<Company, String>("abn"));
		cmp_tblr_url.setCellValueFactory(new PropertyValueFactory<Company, String>("company_url"));
		cmp_tblr_addr.setCellValueFactory(new PropertyValueFactory<Company, String>("company_address"));
    	
		cmp_tblview.setItems(clist);

    }
    
    //displays owners information in owners tab
    private void display_owner_table() {
		List<Owner> ol = new ArrayList<Owner>();
		for(Owner o : projObj.owners.values())
			ol.add(o);
		ObservableList<Owner> olist = FXCollections.observableArrayList(ol);
		own_tblr_oid.setCellValueFactory(new PropertyValueFactory<Owner, String>("owner_id"));
		own_tblr_fname.setCellValueFactory(new PropertyValueFactory<Owner, String>("first_name"));
		own_tblr_sname.setCellValueFactory(new PropertyValueFactory<Owner, String>("surname"));
		own_tblr_role.setCellValueFactory(new PropertyValueFactory<Owner, String>("role"));
		own_tblr_mail.setCellValueFactory(new PropertyValueFactory<Owner, String>("email"));
    	own_tblr_cid.setCellValueFactory(new PropertyValueFactory<Owner, String>("company_id"));
		
		own_tblview.setItems(olist);

    }
    
    //displays project inforrmation in projects tab
    private void display_project_table() {
		List<Project> pl = new ArrayList<Project>();
		for(Project o : projObj.projects.values())
			pl.add(o);
		ObservableList<Project> plist = FXCollections.observableArrayList(pl);
		prj_tblr_pid.setCellValueFactory(new PropertyValueFactory<Project, String>("project_id"));
		prj_tblr_title.setCellValueFactory(new PropertyValueFactory<Project, String>("title"));
		prj_tblr_desc.setCellValueFactory(new PropertyValueFactory<Project, String>("description"));
		prj_tblr_oid.setCellValueFactory(new PropertyValueFactory<Project, String>("owner_id"));
		prj_tblr_p.setCellValueFactory(new PropertyValueFactory<Project, Integer>("P_rank"));
    	prj_tblr_n.setCellValueFactory(new PropertyValueFactory<Project, Integer>("N_rank"));
    	prj_tblr_a.setCellValueFactory(new PropertyValueFactory<Project, Integer>("A_rank"));
    	prj_tblr_w.setCellValueFactory(new PropertyValueFactory<Project, Integer>("W_rank"));
		
		prj_tblView.setItems(plist);

    }
    
    //displays preferences in preferences tab
    private void display_preferences_table() {
		pre_tblview.refresh();
		List<Student> prl = new ArrayList<Student>();
		for(Student o : projObj.students.values())
			prl.add(o);
		ObservableList<Student> plist = FXCollections.observableArrayList(prl);
		pre_tblr_sid.setCellValueFactory(new PropertyValueFactory<Student, String>("student_id"));
		pre_tblr_pref.setCellValueFactory(new PropertyValueFactory<Student, String>("strpref"));
		
		pre_tblview.setItems(plist);
		
		pre_tblview.refresh();
    }
    
    //performs deserialization of ProjectManager object from data.ser file
	public static ProjectManager loadDeserialization(String filename) throws IOException, ClassNotFoundException {
		 
		FileInputStream	fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		ProjectManager projObj = (ProjectManager)ois.readObject();
		ois.close();
		fis.close();
		return projObj;
    }

	//performs serialization of ProjectManager object in data.ser file
    public static void loadSerialization(ProjectManager projObj) throws IOException {
		FileOutputStream fos = new FileOutputStream("data.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(projObj);
		oos.flush();
		oos.close();
    }
    
    public void loadGUISerialization() throws IOException {
    	FileOutputStream fos = new FileOutputStream("GUI.ser");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject((String) txt_t1_m1.getText());
		oos.writeObject((String) txt_t1_m2.getText());
		oos.writeObject((String) txt_t1_m3.getText());
		oos.writeObject((String) txt_t1_m4.getText());
		oos.writeObject((String) txt_t2_m1.getText());
		oos.writeObject((String) txt_t2_m2.getText());
		oos.writeObject((String) txt_t2_m3.getText());
		oos.writeObject((String) txt_t2_m4.getText());
		oos.writeObject((String) txt_t3_m1.getText());
		oos.writeObject((String) txt_t3_m2.getText());
		oos.writeObject((String) txt_t3_m3.getText());
		oos.writeObject((String) txt_t3_m4.getText());
		oos.writeObject((String) txt_t4_m1.getText());
		oos.writeObject((String) txt_t4_m2.getText());
		oos.writeObject((String) txt_t4_m3.getText());
		oos.writeObject((String) txt_t4_m4.getText());
		oos.writeObject((String) txt_t5_m1.getText());
		oos.writeObject((String) txt_t5_m2.getText());
		oos.writeObject((String) txt_t5_m3.getText());
		oos.writeObject((String) txt_t5_m4.getText());
		oos.flush();
		oos.close();
		
		System.out.println("GUI serialized !!!");
    }
    
    public void loadGUIDeserialization(String filename) throws ClassNotFoundException, IOException {
    	FileInputStream	fis = new FileInputStream(filename);
		ObjectInputStream ois = new ObjectInputStream(fis);
		txt_t1_m1.setText((String)ois.readObject());
		txt_t1_m2.setText((String)ois.readObject());
		txt_t1_m3.setText((String)ois.readObject());
		txt_t1_m4.setText((String)ois.readObject());
		txt_t2_m1.setText((String)ois.readObject());
		txt_t2_m2.setText((String)ois.readObject());
		txt_t2_m3.setText((String)ois.readObject());
		txt_t2_m4.setText((String)ois.readObject());
		txt_t3_m1.setText((String)ois.readObject());
		txt_t3_m2.setText((String)ois.readObject());
		txt_t3_m3.setText((String)ois.readObject());
		txt_t3_m4.setText((String)ois.readObject());
		txt_t4_m1.setText((String)ois.readObject());
		txt_t4_m2.setText((String)ois.readObject());
		txt_t4_m3.setText((String)ois.readObject());
		txt_t4_m4.setText((String)ois.readObject());
		txt_t5_m1.setText((String)ois.readObject());
		txt_t5_m2.setText((String)ois.readObject());
		txt_t5_m3.setText((String)ois.readObject());
		txt_t5_m4.setText((String)ois.readObject());

		ois.close();
		fis.close();
		
		System.out.println("GUI deserialized !!!");
    }
    
    public void setStage(Stage stage){
        stage.setOnCloseRequest((EventHandler<WindowEvent>) new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
            	try {
            		projObj.stack.clear();
                	projObj.GUIstack.clear();
        			loadSerialization(GUIController.projObj);
        			System.out.println("Successfully serialized !!!");
        			loadGUISerialization();
        		} catch (IOException e) {
        			e.printStackTrace();
        			System.out.println("Problem in serialization !!!");
        		}
            }
        });
    }

}
