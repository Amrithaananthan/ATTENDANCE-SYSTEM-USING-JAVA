import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Main {
    // Inner class: Student
    static class Student implements Serializable {
        private int id;
        private String name;
        private HashMap<String, String> attendance;
        public Student(int id, String name) {
            this.id = id;
            this.name = name;
            this.attendance = new HashMap<>();
        }
        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void markAttendance(String date, String status) {
            attendance.put(date, status);
        }
        public HashMap<String, String> getAttendance() {
            return attendance;
        }
        public double getAttendancePercentage() {
            int totalClasses = attendance.size();
            int presentCount = 0;
            for (String status : attendance.values()) {
                if ("Present".equals(status)) {
                    presentCount++;
                }
            }
            return (double) presentCount / totalClasses * 100;
        }
    }
    // Inner class: AttendanceSystem
    static class AttendanceSystem {
        private List<Student> students = new ArrayList<>();
        private final String FILE_NAME = "attendance_data.ser";

        public AttendanceSystem() {
            students = loadData(FILE_NAME);  // Load student data from file
        }
        // Add or update student details
        public void addOrUpdateStudent(int id, String name) {
            // Check if student already exists
            for (Student student : students) {
                if (student.getId() == id) {
                    student.setName(name);
                    System.out.println("Student updated: " + name);
                    saveData(FILE_NAME);
                    return;
                }
            }
            // Add new student if not exists
            students.add(new Student(id, name));
            System.out.println("Student added: " + name);
            saveData(FILE_NAME);
        }
        // Mark attendance for all students on a particular day
        public void markDailyAttendance() {
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            Scanner scanner = new Scanner(System.in);
            List<String> presentStudents = new ArrayList<>();
            List<String> absentStudents = new ArrayList<>();
            System.out.println("Mark attendance for all students today (" + date + "). Enter 'p' for Present or 'a' for Absent.");
            for (Student student : students) {
                System.out.print(student.getName() + ": ");
                char attendanceChar = scanner.next().charAt(0);
                String status = (attendanceChar == 'p' || attendanceChar == 'P') ? "Present" : "Absent";
                student.markAttendance(date, status);

                if ("Present".equals(status)) {
                    presentStudents.add(student.getName());
                } else {
                    absentStudents.add(student.getName());
                }
            }
            System.out.println("Attendance marked for " + date);
            saveData(FILE_NAME);
            // Display who are present and absent
            System.out.println("\nStudents Present Today: ");
            if (presentStudents.isEmpty()) {
                System.out.println("No students were present.");
            } else {
                for (String student : presentStudents) {
                    System.out.println(student);
                }
            }
            System.out.println("\nStudents Absent Today: ");
            if (absentStudents.isEmpty()) {
                System.out.println("No students were absent.");
            } else {
                for (String student : absentStudents) {
                    System.out.println(student);
                }
            }
        }
        // View attendance for a particular student
        public void viewAttendance(int id) {
            for (Student student : students) {
                if (student.getId() == id) {
                    System.out.println("Attendance for " + student.getName() + ":");
                    student.getAttendance().forEach((date, status) ->
                            System.out.println(date + " - " + status));
                    return;
                }
            }
            System.out.println("Student not found.");
        }
        // Display attendance summary for all students
        public void displaySummary() {
            System.out.println("Total students: " + students.size());
            System.out.println("Attendance summary: ");
            for (Student student : students) {
                System.out.println(student.getName() + ": " + student.getAttendancePercentage() + "% attendance");
            }
        }
        // Save student data to file
        private void saveData(String filename) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
                out.writeObject(students);
            } catch (IOException e) {
                System.out.println("Error saving data: " + e.getMessage());
            }
        }
        // Load student data from file
        private List<Student> loadData(String filename) {
            List<Student> loadedStudents = new ArrayList<>();
            try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                loadedStudents = (List<Student>) in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("No previous data found. Starting fresh.");
            }
            return loadedStudents;
        }
        // Clear all student 
        public void clearAllData() {
            students.clear();
            new File(FILE_NAME).delete();
            System.out.println("All data has been cleared.");
        }
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AttendanceSystem system = new AttendanceSystem();

        // If no student data exists, ask the user for the number of students and details
        if (system.students.isEmpty()) {
            System.out.print("Enter the number of students: ");
            int numStudents = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            // Get details for each student
            for (int i = 0; i < numStudents; i++) {
                System.out.print("Enter student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();  // Consume the newline
                System.out.print("Enter student name: ");
                String name = scanner.nextLine();
                system.addOrUpdateStudent(id, name);
            }
        }
        while (true) {
            System.out.println("============================================================");
            System.out.println("\nPlease choose an option from the menu below:");
            System.out.println("1. Add or Update a student");
            System.out.println("2. Mark today's attendance");
            System.out.println("3. View attendance for a student");
            System.out.println("4. View all students' attendance summary");
            System.out.println("5. Clear all data");
            System.out.println("6. Exit the system");
            System.out.println("============================================================");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter student ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    System.out.print("Enter student name: ");
                    String name = scanner.nextLine();
                    system.addOrUpdateStudent(id, name);
                    break;
                case 2:
                    system.markDailyAttendance();
                    break;
                case 3:
                    System.out.print("Enter student ID: ");
                    id = scanner.nextInt();
                    system.viewAttendance(id);
                    break;
                case 4:
                    system.displaySummary();
                    break;
                case 5:
                    system.clearAllData();
                    break;
                case 6:
                    System.out.print("Are you sure you want to exit? (yes/no): ");
                    String exit = scanner.next();
                    if ("yes".equalsIgnoreCase(exit)) {
                        System.out.println("Exiting system.");
                        return;
                    }
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } 
    } 
}