import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.ArrayList;
import java.util.List;

public class ParseCSV {

    public static final int CSV_COLUMN_NOS = 9;

    private List<String[]> readCSV(String filePath) throws FileNotFoundException {
        if (filePath == null || filePath.isEmpty()) {
            throw new FileNotFoundException("Invalid filepath!");
        }
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] row = line.split(",");
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public void parseCSVAndLoadData(String filePath) throws FileNotFoundException, InvalidObjectException {
        List<String[]> rows = readCSV(filePath);
        for (String[] row : rows) {
            if(row[0].equalsIgnoreCase("Organization Id")) {
                continue;
            }
            CSVRow csvRow = CSVRow.buildCSVRow(row);
            if (csvRow == null) {
                throw new InvalidObjectException("Unable to read the CSV line!");
            }
            loadCSVRowInDatabase(csvRow);
        }
    }

    private void loadCSVRowInDatabase(CSVRow csvRow) {
        Organization organization;
        Employee employee;
        InMemDatabase inMemDatabase = InMemDatabase.getInstance();
        inMemDatabase.organizationMap.putIfAbsent(csvRow.getOrgId(), new Organization(csvRow.getOrgId(), csvRow.getOrgName()));
        inMemDatabase.employeeMap.putIfAbsent(csvRow.getEmpId(), new Employee(inMemDatabase.organizationMap.get(csvRow.getOrgId()), csvRow.getEmpName(), csvRow.getEmpId()));
		organization = inMemDatabase.organizationMap.get(csvRow.getOrgId());
		employee = inMemDatabase.employeeMap.get(csvRow.getEmpId());
		employee.setRole(csvRow.getRole());
		employee.getSkillMap().putIfAbsent(csvRow.getSkillId(), csvRow.getSkill());
		employee.getSkillMap().get(csvRow.getSkillId()).getSubSkills().add(csvRow.getSubSkill());
		organization.getEmployees().add(employee);
    }


}
