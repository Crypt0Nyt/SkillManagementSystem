import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Employee {

	private Organization organization;
	private String name;
	private Integer id;
	private Role role;
	private Map<Integer, Skill> skillMap;

	public Employee(Organization o, String name, int id) {
		this.name = name;
		this.organization = o;
		this.id= id;
		this.skillMap = new HashMap<>();

	}

	public String getName() {
		return name;
	}

	public Integer getEmployeeID() {
		return id;
	}


	public Role getRole() {
		return role;
	}

	public Map<Integer, Skill> getSkillMap() {
		return skillMap;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Employee employee = (Employee) o;
		return id.equals(employee.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
