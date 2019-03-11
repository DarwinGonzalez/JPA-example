package es.uv.prnr.p2;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class SalaryId implements java.io.Serializable {

	private static final long serialVersionUID = 1193476270832180532L;
	@Column(name = "emp_no",nullable = false)
	private int empNo;
	@Column(name = "from_date", nullable = false, length = 10)
	private LocalDate fromDate;

	public SalaryId() {
	}

	public SalaryId(int empNo, LocalDate fromDate) {
		this.empNo = empNo;
		this.fromDate = fromDate;
	}

	public int getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(int empNo) {
		this.empNo = empNo;
	}

	public LocalDate getFromDate() {
		return this.fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	
	public int hashCode() {
	        return (int) fromDate.hashCode() + empNo;
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof SalaryId))
			return false;
		SalaryId s = (SalaryId) obj;
		return s.getEmpNo() == empNo && s.getFromDate().equals(fromDate);
	}

}
