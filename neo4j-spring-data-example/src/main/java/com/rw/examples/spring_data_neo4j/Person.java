package com.rw.examples.spring_data_neo4j;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class Person {

	@GraphId private Long id;
	
	private String name;
	
	@Relationship (type = "WORKFOR", direction = Relationship.OUTGOING)
	public Company company;
	
	private Person() {
		
	}
	
	public Person(String name) {
		this.name = name;
	}
	
	@Relationship (type = "TEAMMATE", direction = Relationship.UNDIRECTED)
	public Set<Person> teammates;
	
	public void worksWith(Person person) {
		if(teammates == null) {
			teammates = new HashSet<>();
		}
		
		teammates.add(person);
	}
	
	public void worksFor(Company company)
	{
		this.company = company;
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
