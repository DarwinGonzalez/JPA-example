package es.uv.prnr.p2;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "salaries")
public class Salary  {
	@EmbeddedId
	private SalaryId id;
	
	@Column(name = "salary", nullable = false)
	private int quantity;
	
	@Column(name = "to_date", nullable = false, length = 10)
	private LocalDate toDate;
	

	public Salary() {
	}

	public Salary(int employeeId, int salary, LocalDate toDate) {
		this.id = new SalaryId(employeeId, LocalDate.now());
		this.quantity = salary;
		this.toDate = toDate;
	}

	public SalaryId getId() {
		return this.id;
	}

	public void setId(SalaryId id) {
		this.id = id;
	}
	
	public int getSalary() {
		return this.quantity;
	}

	public void setSalary(int salary) {
		this.quantity = salary;
	}

	public LocalDate getToDate() {
		return this.toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	
}
