import java.util.HashSet;
import java.util.Set;

public class Skill {

	private Integer skillId;
	private String name;
	private Set<SubSkill> subSkills;

	public Skill(Integer skillId, String name) {
		this.skillId  = skillId;
		this.name = name;
		this.subSkills = new HashSet<>();
	}

	public String getName() {
		return name;
	}

	public Set<SubSkill> getSubSkills() {
		return subSkills;
	}

	public Integer getSkillId() {
		return skillId;
	}
}