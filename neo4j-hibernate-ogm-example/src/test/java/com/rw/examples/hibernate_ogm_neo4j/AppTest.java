package com.rw.examples.hibernate_ogm_neo4j;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class AppTest {

	EntityManagerFactory emf;
	EntityManager em;
	
	@Before
	public void clearDb() {
        emf = Persistence.createEntityManagerFactory("ogm-neo4j");
        em = emf.createEntityManager();
        
        
/*        em.getTransaction().begin();
        
        System.out.println("clean db");
        String query1 = "MATCH (n) DETACH DELETE n";
        int n = em.createNativeQuery( query1).executeUpdate();
        System.out.println("# of entities deleted: " + n);
        em.getTransaction().commit();
        em.clear();*/
        
        List<Company> companyList;
        List<Employee> employeeList;
        Query query;
        
        em.getTransaction().begin();
        query = em.createQuery("from Employee e");
        employeeList = query.getResultList();
        System.out.println("Delete Employee:" + employeeList.size());
        employeeList.stream().forEach(e->em.remove(e));
        
        query = em.createQuery("from Company c");
        companyList = query.getResultList();
        System.out.println("Delete Company:" + companyList.size());
        companyList.stream().forEach(e->em.remove(e));

        em.getTransaction().commit();
	}
	
	@Test
	public void testPersist() {
        em.getTransaction().begin();
        Employee mike = new Employee("Mike");
        Employee jian = new Employee("Jian"); 
        Employee john = new Employee("John"); 
        Employee ted = new Employee("Ted"); 

        
		Company nokia = new Company("Nokia");
		Company ibm = new Company("ibm");
		
		em.persist(nokia);
		em.persist(ibm);
		
		nokia.addEmployee(jian);
		nokia.addEmployee(mike);

		ibm.addEmployee(john);
		ibm.addEmployee(ted);
		
        mike.worksWith(jian);
        jian.worksWith(mike);
        
		
        em.persist(mike);
        em.persist(jian);
        em.persist(john);
        em.persist(ted);
		
        
        em.flush();
        em.getTransaction().commit();
        em.clear();
        
        em.getTransaction().begin();
        
        
        List<Company> companyList;
        List<Employee> employeeList;
        Query query;
        
        query = em.createQuery("from Employee e");
        employeeList = query.getResultList();
        System.out.println("Employee:" + employeeList.size());
        employeeList.stream().forEach(e->System.out.println(e.toString()));
        
        query = em.createQuery("from Company c");
        companyList = query.getResultList();
        System.out.println("Company:" + companyList.size());
        companyList.stream().forEach(e->System.out.println(e.toString()));
        
        em.getTransaction().commit();
        
	}
	
	@After
	public void cleanUp()
	{
        em.close();
        emf.close();
	}

}
