package es.uv.prnr.p2;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.*;


public class ProjectService {
	EntityManagerFactory emf;
	EntityManager em;
	
	public ProjectService() {
		this.emf = Persistence.createEntityManagerFactory("acmeEmployees");
		this.em = emf.createEntityManager();
	}
	
	/**
	 * Busca un departamento
	 * @param id identificador del departamento	
	 * @return entidad con el deparamenteo encontrado
	 */
	public Department getDepartmentById (String id) {
		return em.find(Department.class, id);
	}
	
	/**
	 * Asciende a un empleado a manager. Utilizar una estrateg�a de herencia adecuada
	 * en employee. Tened en cuenta que NO puede haber dos entidades con el mismo id
	 * por lo que habr� que eliminar el empleado original en algun momento.
	 * @param employeeId
	 * @param bonus
	 * @return
	 */
	public Manager promoteToManager(int employeeId, long bonus) {
		em.getTransaction().begin();
		Employee employee = em.find(Employee.class, employeeId);
		Manager man = new Manager(employee, bonus);	
		em.remove(employee);
		em.persist(man);
		em.getTransaction().commit();
		
		return man;
	}
	
	/**
	 * Crea un nuevo proyecto en el area de Big Data que comienza en la fecha actual y que finaliza
	 * en 3 a�os.
	 * @param name 
	 * @param d departamento asignado al proyecto
	 * @param m manager que asignado al proyecto
	 * @param budget
	 * @return el proyecto creado
	 */
	public Project createBigDataProject(String name, Department d, Manager m, BigDecimal budget) {
		em.getTransaction().begin();
		Project project = new Project(name, d, m,budget,LocalDate.now(),LocalDate.now().plusYears(3),"BigData");
		em.persist(project);
		em.getTransaction().commit();
		return project;
	}
	
	/**
	 * Crea un equipo de proyecto. Se debera implementa el m�todo addEmployee de 
	 * Project para incluir los empleados
	 * @param p proyecto al cual asignar el equipo
	 * @param startId identificador a partir del cual se asignan empleado
	 * @param endId identificador final de empleados. Se asume que start id < endId
	 */
	public void assignTeam (Project p, int startId, int endId) {
		em.getTransaction().begin();
		for(int i = startId; i <= endId; i++) {
			if(em.find(Employee.class, i) != null) {
				p.addEmployee(em.find(Employee.class, i));
			}
		}
		em.merge(p);
		em.getTransaction().commit();
	}
	
	/** 
	 * Genera un conjunto de horas inicial para cada empleado. El m�todo asigna para cada
	 * mes de duraci�n del proyecto, un n�mero entre 10-165 de horas a cada empleado.
	 * @param projectId
	 * @return total de horas generadas para el proyecto
	 */
	public int assignInitialHours (int projectId) {
		em.getTransaction().begin();
		int totalHours = 0;
		Project p = em.find(Project.class, projectId);
		LocalDate start = p.getStartDate();
		while (start.isBefore(p.getEndDate())) {
			for (Employee e: p.getEmployees()) {
				int hours = new Random().nextInt(165) + 10; 
				totalHours += hours;
				p.addHours(e, start.getMonthValue(), start.getYear(), hours);			
			}
			start = start.plusMonths(1);
		}

		em.persist(p);
		em.getTransaction().commit();
		return totalHours;
	}
	
	/**
	 * Busca si un empleado se encuentra asignado en el proyecto utilizando la
	 * namedQuery Project.findEmployee
	 * @param projectId
	 * @param firstName
	 * @param lastName
	 * @return cierto si se encuentra asignado al proyecto
	 */
	public boolean employeeInProject (int projectId, String firstName, String lastName){
		Query consulta= em.createNamedQuery("Project.findEmployee", Integer.class);
		consulta.setParameter("idProyecto", projectId);
		consulta.setParameter("nombre", firstName);
		consulta.setParameter("apellido", lastName);
		@SuppressWarnings("unchecked")
		List<Integer> lista= consulta.getResultList();
		if (lista.size() == 0) {
			return false;
		} else {
			return true;			
		}
	}
	
	/**
	 * Devuelve los meses con mayor n�mero de horas de un a�o determinado
	 * utilizando la namedQuery Project.getTopMonths
	 * @param projectId
	 * @param year a�o a seleccionar
	 * @param rank n�mero de meses a mostrar, se asume que rank <= 12
	 * @return una lista de objetos mes,hora ordenados de mayor a menor
	 */
	public List getTopHourMonths(int projectId, int year, int rank) {
//		Query consulta= em.createNamedQuery("Project.getTopMonths");
//		consulta.setParameter("idProyecto", projectId);
//		consulta.setParameter("año", year);
//		consulta.setMaxResults(rank);

		return  em.createNamedQuery("Project.getTopMonths").setParameter("idProyecto", projectId).setParameter("año", year).setMaxResults(rank).getResultList();
	}
	
	/**
	 * Devuelve para cada par mes-a�o el presupuesto teniendo en cuenta el 
	 * coste/hora de los empleados asociado utilizando la namedQuery Project.getMonthlyBudget
	 * que realiza una consulta nativa
	 * @param projectId
	 * @return una colecci�n de objetos MonthlyBudget
	 */
	public List<MonthlyBudget> getMonthlyBudget (int projectId){
		return em.createNamedQuery("Project.getMonthlyBudget", MonthlyBudget.class).setParameter("idProyecto", projectId)
				.getResultList();
	}
	
}
