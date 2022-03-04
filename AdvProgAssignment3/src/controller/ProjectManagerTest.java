package controller;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.sql.SQLException;

import model.ProjectManager;
import model.Team;

public class ProjectManagerTest {

	ProjectManager projObj;
	Connection con;
	@Before
	public void setUp() throws Exception {
		projObj = GUIController.loadDeserialization("Data.ser");
    	Class.forName("org.sqlite.JDBC");
		con = DriverManager.getConnection("jdbc:sqlite:ProjectCRS.db");
	}

	@After
	public void tearDown() throws Exception {
	}


	
	//Negative test case
	@Test (expected = SQLException.class)
	public void testRepeatedCompanyID() throws Exception {
		Statement stmt = con.createStatement();
		String sql = "insert into companies (cid, cname, abn, url, address) values ('C1', 'abc', 'ASDW67YHGF', 'www.abc.com', 'Melbourne')";
		stmt.executeUpdate(sql);
		fail("Test case Failed !!!!");
	}

	//Negative test case
	@Test (expected = SQLException.class)
	public void testRepeatedOwnerID() throws Exception {
		Statement stmt = con.createStatement();
		String sql = "insert into owners (oid, fname, surname, role, mail, cid) values ('Own1', 'Yogesh', 'Bojja', 'Developer', 'yb@gmail.com', 'C1')";
		stmt.executeUpdate(sql);
		fail("Test case Failed !!!!");
	}

	//Negative test case
	@Test (expected = SQLException.class)
	public void testRepeatedProjectID() throws Exception {
		Statement stmt = con.createStatement();
		String sql = "insert into projects (pid, title, desc, oid, p, n, a, w) values ('Pr1', 'CRS', 'Networking', 'Own1', 4, 3, 1, 2)";
		stmt.executeUpdate(sql);
		fail("Test case Failed !!!!");
	}

	//Negative test case
	@Test (expected = SQLException.class)
	public void testRepeatedStudentID() throws Exception {
		Statement stmt = con.createStatement();
		String sql = "insert into students (sid, pgrade, ngrade, agrade, wgrade) values ('S1', 4, 2, 1, 3)";
		stmt.executeUpdate(sql);
		fail("Test case Failed !!!!");
	}

	//Positive test case
	@Test
	public void testPersentOfPref() throws Exception {
		Team t = projObj.teams.get("T1");
		assertEquals("test Percent of 1st and 2nd Preference !!!",75.0, t.getPercent_of_pref(), 0.0);
	}

	//Positive test case
	@Test
	public void testSkillGap() throws Exception {
		Team t = projObj.teams.get("T5");
		assertEquals("test",0.5, t.getSkill_gap(),0.001);
	}

	//Positive test case
	@Test
	public void testAverageComp() throws Exception {
		Team t = projObj.teams.get("T5");
		assertEquals("test", 3.0, t.getTeam_competancy(),0.0);
	}
	
	//Positive test case
	@Test
	public void testSkillGapSD() throws Exception {
		assertEquals("test", 1.698 , projObj.calculateSkillGapSD(),0.001);
	}
	
	//Positive test case
	@Test
	public void testAverageCompSD() throws Exception {
		assertEquals("test", 0.6869 , projObj.calculateAverageTeamCompetanceSD(),0.001);
	}
	
	//Positive test case
	@Test
	public void testPercOfPrefSD() throws Exception {
		assertEquals("test", 25.4951 , projObj.calculatePercOfPrefSD(),0.001);
	}
	
	
}


