import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class CSVRow {

    private int orgId;
    private String orgName;
    private String empName;
    private int empId;
    private Role role;
    private int skillId;
    private Skill skill;
    private SubSkill subSkill;
    private SkillLevel skillLevel;


    public static CSVRow buildCSVRow(String[] rowData) {
        CSVRow csvRow = new CSVRow();
        try {
            validateCSVRow(rowData);
            csvRow.orgId = Integer.parseInt(rowData[0]);
            csvRow.orgName = rowData[1];
            csvRow.empName = rowData[2];
            csvRow.empId = Integer.parseInt(rowData[3]);
            csvRow.role = new Role(rowData[4]);
            csvRow.skillId = Integer.parseInt(rowData[5]);
            csvRow.skill = new Skill(csvRow.skillId, rowData[6]);
            csvRow.skillLevel = SkillLevel.valueOf(rowData[8].toUpperCase());
            csvRow.subSkill = new SubSkill(rowData[7], csvRow.skillLevel);
            return csvRow;
        } catch (InvalidObjectException | RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<CSVRow> buildCSVRowsForEmployee(Employee employee) {
        List<CSVRow> csvRows = new ArrayList<>();
        for (int skillId : employee.getSkillMap().keySet()) {
            for (SubSkill subSkill : employee.getSkillMap().get(skillId).getSubSkills()) {
                CSVRow csvRow = new CSVRow();
                csvRow.orgId = employee.getOrganization().getId();
                csvRow.orgName = employee.getOrganization().getName();
                csvRow.empName = employee.getName();
                csvRow.empId = employee.getEmployeeID();
                csvRow.role = employee.getRole();
                csvRow.skillId = skillId;
                csvRow.skill = employee.getSkillMap().get(skillId);
                csvRow.skillLevel = subSkill.getSkillLevel();
                csvRow.subSkill = subSkill;
                csvRows.add(csvRow);
            }
        }
        return csvRows;
    }

    public String getCSVString() {
        String[] csvCols = new String[9];
        csvCols[0] = String.valueOf(orgId);
        csvCols[1] = orgName;
        csvCols[2] = empName;
        csvCols[4] = role.getName();
        csvCols[6] = skill.getName();
        csvCols[7] = subSkill.getName();
        csvCols[8] = skillLevel.toString();
        csvCols[3] = String.valueOf(empId);
        csvCols[5] = String.valueOf(skillId);
        return String.join(",", csvCols);
    }

    private static void validateCSVRow(String[] rowData) throws InvalidObjectException {
        if (rowData.length != ParseCSV.CSV_COLUMN_NOS) {
            throw new InvalidObjectException("The CSV line is invalid");
        } else {
            for (String col : rowData) {
                if (col.isEmpty()) {
                    throw new InvalidObjectException("The CSV line is invalid, there is an empty value in the CSV");
                }
            }
        }
    }

    public int getOrgId() {
        return orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public String getEmpName() {
        return empName;
    }

    public int getEmpId() {
        return empId;
    }

    public Role getRole() {
        return role;
    }

    public int getSkillId() {
        return skillId;
    }

    public Skill getSkill() {
        return skill;
    }

    public SubSkill getSubSkill() {
        return subSkill;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }
}
