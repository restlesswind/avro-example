package com.rw.examples.hibernate_ogm_neo4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Employee {

	@Id 
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToOne
	public Employer employer;
	
	private Employee() {
		
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
		return this.name + "'s teammates => "
				+ Optional.ofNullable(this.teammates).orElse(Collections.emptySet())
				.stream().map(person->person.getClass()).collect(Collectors.toList());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
