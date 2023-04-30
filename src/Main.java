import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ExportCsvUtil exportCsvUtil = new ExportCsvUtil();
        SearchUtils searchUtils = new SearchUtils();
        System.out.println("Welcome to Employee Skills Management System!");
        String filePath = "EmployeeDataCsv.csv";
        ParseCSV parseCSV = new ParseCSV();
        try {
            parseCSV.parseCSVAndLoadData(filePath);
        } catch (InvalidObjectException | FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            System.out.println("Please select skills from below (type the ID, enter -1 to exit the program): ");
            System.out.println("Skill ID : Skill Name");
            Map<Integer, Skill> skillMap = searchUtils.getAllSkills();
            for (int skillId : skillMap.keySet()) {
                System.out.println(" " + skillId + " : " + skillMap.get(skillId).getName());
            }
            System.out.println("");
            int inputSkillId = sc.nextInt();
            sc.nextLine();
            if (inputSkillId == -1) {
                break;
            }
            System.out.println("Please select subskills from below (type the NAME): \n");
            System.out.println("SUBSKILL NAME\n");
            Skill selectedSkill = skillMap.get(inputSkillId);
            Set<SubSkill> subSkillSet = searchUtils.getAllSubSkillsForSkill(skillMap.get(inputSkillId));
            for (SubSkill subSkill : subSkillSet) {
                System.out.println(subSkill.getName());
            }
            System.out.println("ALL");
            System.out.print("Enter the option you want to get the skill data: ");

            System.out.println("");
            String inputSubSkillName = sc.nextLine();
            SubSkill selectedSubSkill = null;
            if (!inputSubSkillName.equals("ALL")) {
                for (SubSkill subSkill : subSkillSet) {
                    if (subSkill.getName().equals(inputSubSkillName)) {
                        selectedSubSkill = subSkill;
                        break;
                    }
                }
                if (selectedSubSkill == null) {
                    System.out.println("Invalid input!");
                    continue;
                }
            }

            System.out.println("Please select the valid skills levels based on your filters from below (type the SKILL LEVEL): ");
            System.out.println("SKILL LEVEL");
            Set<SkillLevel> skillLevelSet;
            if (selectedSubSkill == null) {
                skillLevelSet = searchUtils.getAllValidSkillLevels(selectedSkill);
            } else {
                skillLevelSet = searchUtils.getAllValidSkillLevels(selectedSkill, selectedSubSkill);
            }
            for (SkillLevel skillLevel : skillLevelSet) {
                System.out.println(skillLevel.toString());
            }
            System.out.println("ALL");
            System.out.println("Please select the option: ");
            String inputSkillLevel = sc.nextLine();
            SkillLevel selectedSkillLevel = null;
            if (!inputSkillLevel.equals("ALL")) {
                selectedSkillLevel = SkillLevel.valueOf(inputSkillLevel.toUpperCase());
            }

            Set<Employee> searchResults;
            if (selectedSubSkill == null) {
                searchResults = searchUtils.getEmployees(selectedSkill);
            } else if (selectedSkillLevel == null) {
                searchResults = searchUtils.getEmployees(selectedSkill, selectedSubSkill);
            } else {
                searchResults = searchUtils.getEmployees(selectedSkill, selectedSubSkill, selectedSkillLevel);
            }
            exportCsvUtil.exportEmployeesToCSV("result.csv", searchResults);
            System.out.println("\nCSV with no of employees " + searchResults.size() + " successfully exported.\n\n");
        }

        try (Socket socket = new Socket("localhost", 900)) {

            DataInputStream dataInputStream = new DataInputStream(
                    socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(
                    socket.getOutputStream());
            System.out.println(
                    "Sending the File to the Server");
            sendFile(
                    "result.csv");

            dataInputStream.close();
            dataInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendFile(String path)
            throws Exception
    {
        int bytes = 0;
        File file = new File(path);
        FileInputStream fileInputStream
                = new FileInputStream(file);

        DataOutputStream dataOutputStream = null;
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ((bytes = fileInputStream.read(buffer))
                != -1) {
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();
        }
        fileInputStream.close();
    }

}

