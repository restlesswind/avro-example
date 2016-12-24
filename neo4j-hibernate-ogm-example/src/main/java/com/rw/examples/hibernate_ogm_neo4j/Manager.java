package com.rw.examples.hibernate_ogm_neo4j;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Manager extends Employee{

	@OneToMany
	Set<Employee> managing = new HashSet<Employee>();
	
	private Manager() {super();}
	
	public Manager(String name) {
		super(name);
	}
	
	public void manages(Employee e) {
		managing.add(e);
	}

	public Set<Employee> getManaging() {
		return managing;
	}

	@Override
	public String toString() {
		return super.toString() + " Manager [managing=" + 
				managing.stream().map(e->e.getName()).collect(Collectors.joining(",")) + "]";
	}

	
}
