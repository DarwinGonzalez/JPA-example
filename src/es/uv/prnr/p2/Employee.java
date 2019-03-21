package es.uv.prnr.p2;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@Table(name = "employees")
public class Employee {
	
	@Id @Column(name="emp_no")
	private int id;
	@Column(name = "first_name", unique = false,
			nullable = false, length = 14)
	private String firstName;
	@Column
	private String last_name;
	
	@Column(name = "birth_date")
	private LocalDate birthDate;
	
	@Column(name = "hire_date")
	private LocalDate hireDate;
	
	@Column
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	public enum Gender{
		M,F
	};
	
	/*Necesario referencedColumnName puesto que la fk y pk se llaman igual */
	@OneToMany
	@JoinColumn(name="emp_no", updatable=false, referencedColumnName="emp_no")
	private List<Salary> salaries = new ArrayList();
	
	@ManyToMany(mappedBy="team", fetch=FetchType.LAZY)
	private List<Project> assignedTo = new ArrayList();

	public Employee(){
		
	}

	public Employee(int id, String firstName, String lastName, LocalDate birthDate, LocalDate hireDate, Gender gender) {
		this.id = id;
		this.firstName = firstName;
		this.last_name = lastName;
		this.birthDate = birthDate;
		this.hireDate = hireDate;
		this.gender = gender;
	}
	
	public int getCurrentSalary() {
		for(Salary s: this.salaries) {
			if (s.getToDate().isEqual(LocalDate.of(9999, 01, 01))) return s.getSalary();
		}
		return 0;
	}
	
	//TODO a�adir un nuevo proyecto al empleado
	public void addProject (Project p) {
	}
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return last_name;
	}

	public void setLastName(String lastName) {
		this.last_name = lastName;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void print() {
		System.out.println("****************************************************");
		System.out.println(this.id + " " + this.firstName + " " + this.last_name);
	}
	
}


