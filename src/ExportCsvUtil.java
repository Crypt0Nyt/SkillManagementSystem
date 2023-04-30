import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Set;

public class ExportCsvUtil {

	public void exportEmployeesToCSV(String filePath, Set<Employee> employeeSet) {
		try (PrintWriter writer = new PrintWriter(filePath)) {
			writer.println("Organization Id,Orgnization Name,Employee Name,Employee ID,Role,Skill ID,Skill,SubSkill,Rating");
			for(Employee employee : employeeSet) {
				for(CSVRow csvRow : CSVRow.buildCSVRowsForEmployee(employee)) {
					writer.println(csvRow.getCSVString());
				}
			}
			writer.flush();
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
	}
}
