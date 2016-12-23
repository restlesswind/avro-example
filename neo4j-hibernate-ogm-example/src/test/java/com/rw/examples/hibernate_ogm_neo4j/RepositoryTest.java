package com.rw.examples.hibernate_ogm_neo4j;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class RepositoryTest {
	
	
	@Autowired
	private EmployeeRepository er;
	
	@Test
	public void testRepository() {
		
		ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		
		EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);
		EntityManager em = emf.createEntityManager();
		
		
		System.out.println("testRepository");
		er.save(new Employee("Frank"));
		System.out.println("list employees using repository");
		Iterable<Employee> employees = er.findAll();
		employees.forEach(e->System.out.println(e.toString()));
	}
}
