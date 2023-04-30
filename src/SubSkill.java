import java.util.Objects;

public class SubSkill {
    private String name;
    private SkillLevel skillLevel;

    public SubSkill(String name, SkillLevel skillLevel) {
        this.name = name;
        this.skillLevel = skillLevel;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubSkill subSkill = (SubSkill) o;
        return name.equals(subSkill.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}