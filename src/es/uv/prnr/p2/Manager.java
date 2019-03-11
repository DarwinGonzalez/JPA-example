package es.uv.prnr.p2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name="manager")
public class Manager extends Employee {

	private Long bonus;

	public Manager() {
		
	}
	public Manager(Employee e, Long bonus) {
		super(e.getId(), e.getFirstName(), e.getLastName(), e.getBirthDate(), e.getHireDate(), e.getGender());
		this.bonus = bonus;
	}
	
	public Long getBonus() {
		return this.bonus;
	}

	public void setBonus(Long bonus) {
		this.bonus = bonus;
	}

}
