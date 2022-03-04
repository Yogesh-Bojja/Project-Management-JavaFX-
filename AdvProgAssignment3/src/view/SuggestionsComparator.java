package view;

import java.util.Comparator;

public class SuggestionsComparator implements Comparator<StuObj>{

	@Override
	public int compare(StuObj o1, StuObj o2) {
		return Double.compare(o1.avg, o2.avg);
	}

}
