package com.rw.examples.spring_data_neo4j;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

@Controller
public class AppController implements CommandLineRunner {

	private final static Logger log = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		personRepository.deleteAll();
		companyRepository.deleteAll();
		
		Company nokia = new Company("Nokia");
		Company ibm = new Company("ibm");
		
		companyRepository.save(nokia);
		companyRepository.save(ibm);
		
		Person greg = new Person("Greg");
		Person roy = new Person("Roy");
		Person craig = new Person("Craig");
		
		greg.worksFor(nokia);
		roy.worksFor(nokia);
		craig.worksFor(ibm);
		
		List<Person> team = Arrays.asList(greg, roy, craig);
		
		personRepository.save(greg);
		personRepository.save(roy);
		personRepository.save(craig);
		
		greg = personRepository.findByName(greg.getName());
		greg.worksWith(roy);
		greg.worksWith(craig);
		
		personRepository.save(greg);
		
		roy = personRepository.findByName(roy.getName());
		roy.worksWith(craig);
		personRepository.save(roy);
		
		log.info("Lookup each perosn by name...");
		
		team.stream().forEach(person -> log.info(
				"\t" + personRepository.findByName(person.getName()).toString()));
		
	}

}
