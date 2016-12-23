package com.rw.examples.spring_data_neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;

public interface PersonRepository extends GraphRepository<Person> {

	Person findByName(String name);
}
