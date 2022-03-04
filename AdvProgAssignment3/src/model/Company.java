package model;

import java.io.Serializable;

public class Company implements Serializable {
	private String ID, company_name, abn, company_url, company_address;
	
	public Company(String company_name, String abn, String company_url, String company_address, int company_id_count) {
		this.company_name = company_name;
		this.abn = abn;
		this.company_url = company_url;
		this.company_address = company_address;
		this.ID = "C"+company_id_count;
	}

	public String getID() {
		
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getAbn() {
		return abn;
	}

	public void setAbn(String abn) {
		this.abn = abn;
	}

	public String getCompany_url() {
		return company_url;
	}

	public void setCompany_url(String company_url) {
		this.company_url = company_url;
	}

	public String getCompany_address() {
		return company_address;
	}

	public void setCompany_address(String company_address) {
		this.company_address = company_address;
	}
	
	
}
