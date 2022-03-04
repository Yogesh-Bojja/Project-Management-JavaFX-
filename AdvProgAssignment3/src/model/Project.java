package model;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

public class Project implements Serializable {
	private String title, project_id, description, owner_id;
	private int W_rank, P_rank, N_rank, A_rank, project_score;
	HashMap <String, Integer> skill_rank = new HashMap <String, Integer >();
	private String assigned_team;
	
	public Project(String title, String description, String owner_id, int p_rank, int n_rank, int a_rank,
			int w_rank, int project_id_count) {
		this.project_id = "Pr"+project_id_count;
		this.title = title;
		this.description = description;
		this.owner_id = owner_id;
		W_rank = w_rank;
		P_rank = p_rank;
		N_rank = n_rank;
		A_rank = a_rank;
		skill_rank.put("P",P_rank);
		skill_rank.put("W",W_rank);
		skill_rank.put("N",N_rank);
		skill_rank.put("A",A_rank);
		project_score = 0;
		assigned_team = "";
	}

	public String getassigned_team() {
		return assigned_team;
	}

	public void setassigned_team(String is_assigned) {
		this.assigned_team = is_assigned;
	}

	public int getProject_score() {
		return project_score;
	}

	public void setProject_score(int project_score) {
		this.project_score = project_score;
	}

	public HashMap<String, Integer> getSkill_rank() {
		return skill_rank;
	}

	public void setSkill_rank(HashMap<String, Integer> skill_rank) {
		this.skill_rank = skill_rank;
	}

	public HashMap getSkillRank() {
		return skill_rank;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProject_id() {
		return project_id;
	}

	public void setProject_id(String project_id) {
		this.project_id = project_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public int getW_rank() {
		return W_rank;
	}

	public void setW_rank(int w_rank) {
		W_rank = w_rank;
	}

	public int getP_rank() {
		return P_rank;
	}

	public void setP_rank(int p_rank) {
		P_rank = p_rank;
	}

	public int getN_rank() {
		return N_rank;
	}

	public void setN_rank(int n_rank) {
		N_rank = n_rank;
	}

	public int getA_rank() {
		return A_rank;
	}

	public void setA_rank(int a_rank) {
		A_rank = a_rank;
	}

	
	
}
