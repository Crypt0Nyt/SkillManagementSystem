import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Organization {

	private Integer id;
	private String name;
	private Set<Employee> employees;

	public Organization(int id, String name){
		this.id = id;
		this.name = name;
		this.employees = new HashSet<>();
	}

	//Getters
	public String getName(){
		return name;
	}

	public Integer getId() {
		return id;
	}

	public Set<Employee> getEmployees() {
		return employees;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Organization that = (Organization) o;
		return id.equals(that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
