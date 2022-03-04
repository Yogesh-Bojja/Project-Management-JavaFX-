package model;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;

public class Owner implements Serializable {
	private String first_name, surname, owner_id, role, email, company_id;

	public Owner(String first_name, String surname, String role, String email, String company_id, int owner_id_count) {
		this.first_name = first_name;
		this.surname = surname;
		this.owner_id = "Own"+owner_id_count;
		this.role = role;
		this.email = email;
		this.company_id = company_id;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getOwner_id() {
		return owner_id;
	}

	public void setOwner_id(String owner_id) {
		this.owner_id = owner_id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany_id() {
		return company_id;
	}

	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}

	
}
