package com.rw.examples.hibernate_ogm_neo4j;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	@OneToMany(mappedBy = "company")
    private Set<Employee> employees = new HashSet<Employee>();
	
	private Company() {}
	
	public Company(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void addEmployee(Employee e) {
		employees.add(e);
		e.worksFor(this);
	}
	

	@Override
	public String toString() {
		return "Company [name=" + name + "]";
	}
	
	
	
	
}
