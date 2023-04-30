import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SearchUtils {

    public Map<Integer, Skill> getAllSkills() {
        InMemDatabase inMemDatabase = InMemDatabase.getInstance();
        Map<Integer, Skill> results = new HashMap<>();
        for (int empId : inMemDatabase.employeeMap.keySet()) {
            results.putAll(inMemDatabase.employeeMap.get(empId).getSkillMap());
        }
        return results;
    }

    public Set<SubSkill> getAllSubSkillsForSkill(Skill skill) {
        InMemDatabase inMemDatabase = InMemDatabase.getInstance();
        Set<SubSkill> results = new HashSet<>();
        for (int empId : inMemDatabase.employeeMap.keySet()) {
            if (inMemDatabase.employeeMap.get(empId).getSkillMap().containsKey(skill.getSkillId())) {
                results.addAll(inMemDatabase.employeeMap.get(empId).getSkillMap().get(skill.getSkillId()).getSubSkills());
            }
        }
        return results;
    }

    public Set<SkillLevel> getAllValidSkillLevels(Skill skill, SubSkill subSkillInput) {
        Set<SubSkill> subSkillSet = getAllSubSkillsForSkill(skill);
        Set<SkillLevel> result = new HashSet<>();
        for (SubSkill subSkill : subSkillSet) {
            if (subSkillInput.equals(subSkill)) {
                result.add(subSkill.getSkillLevel());
            }
        }
        return result;
    }

    public Set<SkillLevel> getAllValidSkillLevels(Skill skill) {
        Set<SubSkill> subSkillSet = getAllSubSkillsForSkill(skill);
        Set<SkillLevel> result = new HashSet<>();
        for (SubSkill subSkill : subSkillSet) {
            result.add(subSkill.getSkillLevel());
        }
        return result;
    }

    public Set<Employee> getEmployees(Skill skill) {
        InMemDatabase inMemDatabase = InMemDatabase.getInstance();
        Set<Employee> results = new HashSet<>();
        for (int empId : inMemDatabase.employeeMap.keySet()) {
            if (inMemDatabase.employeeMap.get(empId).getSkillMap().containsKey(skill.getSkillId())) {
                results.add(inMemDatabase.employeeMap.get(empId));
            }
        }
        return results;
    }

    public Set<Employee> getEmployees(Skill skill, SubSkill subSkill) {
        Set<Employee> employeesWithSkill = this.getEmployees(skill);
        Set<Employee> results = new HashSet<>();
        for (Employee employee : employeesWithSkill) {
            if (employee.getSkillMap().get(skill.getSkillId()).getSubSkills().contains(subSkill)) {
                results.add(employee);
            }
        }
        return results;
    }

    public Set<Employee> getEmployees(Skill skill, SubSkill subSkill, SkillLevel skillLevel) {
        Set<Employee> employeesWithSkillAndSubSkill = this.getEmployees(skill, subSkill);
        Set<Employee> results = new HashSet<>();
        for (Employee employee : employeesWithSkillAndSubSkill) {
            for (SubSkill empSubSkill : employee.getSkillMap().get(skill.getSkillId()).getSubSkills()) {
                if (empSubSkill.getSkillLevel().equals(skillLevel)) {
                    results.add(employee);
                }
            }
        }
        return results;
    }


}


