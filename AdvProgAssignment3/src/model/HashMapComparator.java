package model;

import java.util.Comparator;

public class HashMapComparator implements Comparator<Project>{
	
	@Override
	public int compare(Project p1, Project p2) {
		if(p1.getProject_score() > p2.getProject_score()) {
			return -1;
		}
		else if(p1.getProject_score() < p2.getProject_score()) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
