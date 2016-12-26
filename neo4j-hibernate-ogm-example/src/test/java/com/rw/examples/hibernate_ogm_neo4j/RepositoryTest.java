package com.rw.examples.hibernate_ogm_neo4j;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class, loader = AnnotationConfigContextLoader.class)
public class RepositoryTest {
	
	
	@Autowired
	private EmployeeRepository er;
	
	@Test
	public void testRepository() {

		System.out.println("testRepository");
		//er.deleteAll();
		er.save(new Employee("Frank"));
		
		System.out.println("count: " + er.count());
		
		System.out.println("list employees using repository");
		List<Employee> employees = er.findAll();
		
		employees.stream().forEach(e->System.out.println(e.toString()));
		
		System.out.println("count: " + er.count());
	}
}
