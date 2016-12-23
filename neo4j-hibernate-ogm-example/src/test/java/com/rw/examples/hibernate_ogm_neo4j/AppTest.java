package com.rw.examples.hibernate_ogm_neo4j;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AppTest {

	ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
	
	EntityManagerFactory emf = ctx.getBean(EntityManagerFactory.class);
	EntityManager em = emf.createEntityManager();

	@Before
	public void clearDb() {
       
        
/*        em.getTransaction().begin();
        
        System.out.println("clean db");
        String query1 = "MATCH (n) DETACH DELETE n";
        int n = em.createNativeQuery( query1).executeUpdate();
        System.out.println("# of entities deleted: " + n);
        em.getTransaction().commit();
        em.clear();*/
        
        List<Employer> employerList;
        List<Employee> employeeList;
        Query query;
        
        em.getTransaction().begin();
        query = em.createQuery("from Employee e");
        employeeList = query.getResultList();
        System.out.println("Delete Employee:" + employeeList.size());
        employeeList.stream().forEach(e->em.remove(e));
        
        query = em.createQuery("from Employer e");
        employerList = query.getResultList();
        System.out.println("Delete Employer:" + employerList.size());
        employerList.stream().forEach(e->em.remove(e));

        em.getTransaction().commit();
	}
	
	@Test
	public void testPersist() {
        em.getTransaction().begin();
        Employee mike = new Employee("Mike");
        Employee jian = new Employee("Jian"); 
        Employee john = new Employee("John"); 
        Employee ted = new Employee("Ted"); 

        
		Employer nokia = new Employer("Nokia");
		Employer ibm = new Employer("ibm");
		
		
        em.persist(mike);
        em.persist(jian);
        em.persist(john);
        em.persist(ted);
		em.persist(nokia);
		em.persist(ibm);
		
		nokia.addEmployee(jian);
		nokia.addEmployee(mike);

		//ibm.addEmployee(john);
		ibm.addEmployee(ted);
		john.worksFor(ibm);
		
        mike.worksWith(jian);
        //jian.worksWith(mike);

        em.flush();
        em.getTransaction().commit();
        em.clear();
        
        em.getTransaction().begin();
        
        
        List<Employer> employerList;
        List<Employee> employeeList;
        Query query;
        
        query = em.createQuery("from Employee e");
        employeeList = query.getResultList();
        System.out.println("Employee:" + employeeList.size());
        employeeList.stream().forEach(e->System.out.println(e.toString()));
        
        query = em.createQuery("from Employer e");
        employerList = query.getResultList();
        System.out.println("Employer:" + employerList.size());
        employerList.stream().forEach(e->System.out.println(e.toString()));
        
        em.getTransaction().commit();
        
	}
	

	
	@After
	public void cleanUp()
	{

        em.close();
        emf.close();
	}

}
