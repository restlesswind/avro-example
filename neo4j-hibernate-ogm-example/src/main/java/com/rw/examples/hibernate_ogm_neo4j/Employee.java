package com.rw.examples.hibernate_ogm_neo4j;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Employee extends AbstractEntity {

	private String name;
	
	@OneToOne
	public Employer employer;
	
	protected Employee() {
		
	}
	
	public Employee(String name) {
		this.name = name;
	}
	
	@OneToMany
	public Set<Employee> teammates = new HashSet<>();;
	
	public void worksWith(Employee person) {
		
		teammates.add(person);
	}
	
	public void worksFor(Employer e)
	{
		this.employer = e;
	}
	
	public String toString() {
		return this.name + " id " + getId() + " works for " + employer.getName() + " and has teammates => "
				+ teammates.stream().map(e->e.getName()).collect(Collectors.joining(","));
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
