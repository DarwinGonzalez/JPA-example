package es.uv.prnr.p2;

import javax.persistence.*;

@Entity
@Table(name = "departments")
public class Department {

	@Id
	@Column(name = "dept_no", unique = true, nullable = false, length = 4)
	private String deptNo;
	@Column(name = "dept_name", unique = true, nullable = false, length = 40)
	private String deptName;

	public Department() {
	}

	public Department(String deptNo, String deptName) {
		this.deptNo = deptNo;
		this.deptName = deptName;
	}

	public String getDeptNo() {
		return this.deptNo;
	}

	public void setDeptNo(String deptNo) {
		this.deptNo = deptNo;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}


}
