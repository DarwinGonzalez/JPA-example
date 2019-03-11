package es.uv.prnr.p2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 * Clase principal para probar la ejecuci�n de ProjectService
 * Se recomienda descomentar el c�digo de los ejercicios conforme se vayan realizando
 * @author Paco
 *
 */
public class P2Main {

	public static void main(String[] args) {
		
		ProjectService service = new ProjectService();
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("acmeEmployees");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		
		/* Comprobar funcionamiento */
		Employee e = em.find(Employee.class, 222222);
		e.print();
		
		Employee newEmployee = new Employee(1, "Edgar", "Cood",
				LocalDate.of(1923,8,19), LocalDate.now(), Employee.Gender.M);	
		em.persist(newEmployee);
		e = em.find(Employee.class,1);
		e.print();
		em.remove(e);
		
		// Ejercicio 2.1
		Department proyDepartment = service.getDepartmentById("d005"); // a)
		Employee aux = em.find(Employee.class,20001);
		Manager projectManager = service.promoteToManager(20001, 1000L); // b)
		projectManager.print();
		em.remove(aux);
		em.persist(projectManager);
		
		
		//Ejercicio 2.2
		Project acmeProject = 
				service.createBigDataProject("Persistence Layer",proyDepartment,projectManager,new BigDecimal(1500000.99));
		em.persist(acmeProject);
		
		
		//Ejercicio 2.3
		service.assignTeam(acmeProject,10001,10005);
		em.getTransaction().commit();
		
		/*
		int totalHours = service.assignInitialHours(acmeProject.getId());
		System.out.println("Total project hours: " + totalHours);*/
		
		/*
		 * Ejercicio 3. Prueba de consultas
		 *
		
		if(service.employeeInProject(acmeProject.getId(), "Parto", "Bamford"))
			System.out.println("Parto Bamford assigned to project");
		if(!service.employeeInProject(acmeProject.getId(), "Luke", "Johnson"))
			System.out.println("Luke Johnson is not assigned to project");
		
		List<Object[]>results = service.getTopHourMonths(acmeProject.getId(), 2019, 3);
		for(Object[] result : results) {
			System.out.println("Month " + result[0] + " Hours " + result[1]);
		}
		
		
		List<MonthlyBudget> monthBudgets = service.getMonthlyBudget(acmeProject.getId());
		for(MonthlyBudget budget : monthBudgets) {
			System.out.println (budget.getMonth()+"-"+budget.getYear()+" : "+budget.getAmount()+"�");
		}
		
		/* Eliminamos la informaci�n creada *
		em.getTransaction().begin();
		Manager m = em.merge(projectManager);
		Project p = em.merge(acmeProject);
		em.remove(p);
		em.createNativeQuery("Delete from manager where emp_no = " + m.getId()).executeUpdate();
		em.getTransaction().commit();*/
		
		return;
	}
	

}
