package com.rw.examples.hibernate_ogm_neo4j;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;


public class AppTest {

    public static void main(final String[] args) throws Exception {
        final EntityManagerFactory emf = Persistence.createEntityManagerFactory("ogm-neo4j");
        EntityManager em = emf.createEntityManager();
        
        
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
        
        
        em.getTransaction().begin();
        Employee mike = new Employee("Mike");
        Employee jian = new Employee("Jian"); 
        Employee john = new Employee("John"); 

        
		Company nokia = new Company("Nokia");
		Company ibm = new Company("ibm");
		
		em.persist(nokia);
		em.persist(ibm);
		
		nokia.addEmployee(jian);
		nokia.addEmployee(mike);

		ibm.addEmployee(john);
		
        mike.worksWith(jian);
        jian.worksWith(mike);
        
		
        em.persist(mike);
        em.persist(jian);
        em.persist(john);
		
        
        em.flush();
        em.getTransaction().commit();
        em.clear();
        
        
        //printDbContents();
        em.getTransaction().begin();
        
        
        query = em.createQuery("from Employee e");
        employeeList = query.getResultList();
        System.out.println("Employee:" + employeeList.size());
        employeeList.stream().forEach(e->System.out.println(e.toString()));
        
        query = em.createQuery("from Company c");
        companyList = query.getResultList();
        System.out.println("Company:" + companyList.size());
        companyList.stream().forEach(e->System.out.println(e.toString()));
        
        em.getTransaction().commit();
        em.close();
        emf.close();
        //printDbContents();
    }

    private static void printDbContents() {

    }
}
