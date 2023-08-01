package net.etfbl.api;

import java.util.HashSet;
import java.util.Set;

public class WarrantService {
	
	public static Set<Integer> evidences = new HashSet<>();
	
	static {
		evidences.add(5);
		evidences.add(50);
	}
	
	public WarrantService() {
		super();
	}
	
	public boolean add(int id) {
		return evidences.add(id);
	}
	
	public Set<Integer> getAll() {
		return evidences;
	}
	
}
