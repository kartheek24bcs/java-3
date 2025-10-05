import java.io.*;
import java.util.Scanner;

// Student class implementing Serializable interface
class Student implements Serializable {
    // serialVersionUID for version control during deserialization
    private static final long serialVersionUID = 1L;
    
    // Student fields
    private int studentID;
    private String name;
    private String grade;
    
    // Constructor
    public Student(int studentID, String name, String grade) {
        this.studentID = studentID;
        this.name = name;
        this.grade = grade;
    }
    
    // Getter methods
    public int getStudentID() {
        return studentID;
    }
    
    public String getName() {
        return name;
    }
    
    public String getGrade() {
        return grade;
    }
    
    // toString method for displaying student information
    @Override
    public String toString() {
        return "Student Details:\n" +
               "================\n" +
               "Student ID: " + studentID + "\n" +
               "Name: " + name + "\n" +
               "Grade: " + grade;
    }
}

public class StudentSerializationDemo {
    // File name for storing serialized student object
    private static final String FILENAME = "student.ser";
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        
        System.out.println("╔════════════════════════════════════════════════╗");
        System.out.println("║   STUDENT SERIALIZATION & DESERIALIZATION     ║");
        System.out.println("╚════════════════════════════════════════════════╝\n");
        
        while (!exit) {
            displayMenu();
            System.out.print("Enter your choice: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        createAndSerializeStudent(scanner);
                        break;
                    case 2:
                        deserializeAndDisplayStudent();
                        break;
                    case 3:
                        serializeMultipleStudents(scanner);
                        break;
                    case 4:
                        deserializeMultipleStudents();
                        break;
                    case 5:
                        exit = true;
                        System.out.println("\n✓ Thank you for using Student Serialization System!");
                        System.out.println("Goodbye!\n");
                        break;
                    default:
                        System.out.println("\n✗ Invalid choice! Please enter 1-5.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("\n✗ Invalid input! Please enter a number.\n");
            }
        }
        
        scanner.close();
    }
    
    // Display menu
    private static void displayMenu() {
        System.out.println("┌────────────────────────────────────────────────┐");
        System.out.println("│              MAIN MENU                         │");
        System.out.println("├────────────────────────────────────────────────┤");
        System.out.println("│ 1. Create and Serialize Single Student         │");
        System.out.println("│ 2. Deserialize and Display Single Student      │");
        System.out.println("│ 3. Serialize Multiple Students                 │");
        System.out.println("│ 4. Deserialize and Display Multiple Students   │");
        System.out.println("│ 5. Exit                                        │");
        System.out.println("└────────────────────────────────────────────────┘");
    }
    
    // Method to create and serialize a single student
    private static void createAndSerializeStudent(Scanner scanner) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("        CREATE AND SERIALIZE STUDENT");
        System.out.println("═".repeat(50));
        
        try {
            // Get student details from user
            System.out.print("Enter Student ID: ");
            int studentID = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Enter Student Name: ");
            String name = scanner.nextLine();
            
            System.out.print("Enter Student Grade: ");
            String grade = scanner.nextLine();
            
            // Create Student object
            Student student = new Student(studentID, name, grade);
            
            // Serialize the student object
            serializeStudent(student, FILENAME);
            
            System.out.println("\n✓ SUCCESS!");
            System.out.println("Student object created and serialized successfully!");
            System.out.println("File: " + FILENAME);
            System.out.println("\nStudent Information:");
            System.out.println(student);
            System.out.println("═".repeat(50) + "\n");
            
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Error: Invalid Student ID! Please enter a number.\n");
        } catch (Exception e) {
            System.out.println("\n✗ Error: " + e.getMessage() + "\n");
        }
    }
    
    // Method to serialize a student object
    private static void serializeStudent(Student student, String filename) {
        try (FileOutputStream fileOut = new FileOutputStream(filename);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            
            // Write the student object to file
            objectOut.writeObject(student);
            objectOut.flush();
            
            System.out.println("\n[SERIALIZATION PROCESS]");
            System.out.println("→ Creating FileOutputStream...");
            System.out.println("→ Creating ObjectOutputStream...");
            System.out.println("→ Writing Student object to file...");
            System.out.println("→ Flushing output stream...");
            System.out.println("→ Closing streams...");
            
        } catch (IOException e) {
            System.out.println("\n✗ Serialization Error: " + e.getMessage());
        }
    }
    
    // Method to deserialize and display a single student
    private static void deserializeAndDisplayStudent() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("      DESERIALIZE AND DISPLAY STUDENT");
        System.out.println("═".repeat(50));
        
        File file = new File(FILENAME);
        if (!file.exists()) {
            System.out.println("\n✗ Error: File not found!");
            System.out.println("Please create and serialize a student first.\n");
            return;
        }
        
        try (FileInputStream fileIn = new FileInputStream(FILENAME);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            System.out.println("\n[DESERIALIZATION PROCESS]");
            System.out.println("→ Opening FileInputStream...");
            System.out.println("→ Creating ObjectInputStream...");
            System.out.println("→ Reading Student object from file...");
            
            // Read and deserialize the student object
            Student student = (Student) objectIn.readObject();
            
            System.out.println("→ Reconstructing Student object...");
            System.out.println("→ Closing streams...");
            
            System.out.println("\n✓ SUCCESS!");
            System.out.println("Student object deserialized successfully!\n");
            
            // Display student details
            System.out.println(student);
            
            // Additional detailed information
            System.out.println("\n[OBJECT DETAILS]");
            System.out.println("Object Class: " + student.getClass().getName());
            System.out.println("Student ID: " + student.getStudentID());
            System.out.println("Name: " + student.getName());
            System.out.println("Grade: " + student.getGrade());
            System.out.println("═".repeat(50) + "\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("\n✗ Error: File not found! " + e.getMessage() + "\n");
        } catch (IOException e) {
            System.out.println("\n✗ Deserialization Error: " + e.getMessage() + "\n");
        } catch (ClassNotFoundException e) {
            System.out.println("\n✗ Error: Student class not found! " + e.getMessage() + "\n");
        }
    }
    
    // Method to serialize multiple students
    private static void serializeMultipleStudents(Scanner scanner) {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("        SERIALIZE MULTIPLE STUDENTS");
        System.out.println("═".repeat(50));
        
        try {
            System.out.print("How many students do you want to add? ");
            int count = Integer.parseInt(scanner.nextLine());
            
            Student[] students = new Student[count];
            
            // Get details for each student
            for (int i = 0; i < count; i++) {
                System.out.println("\n--- Student " + (i + 1) + " ---");
                System.out.print("Enter Student ID: ");
                int studentID = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Enter Student Name: ");
                String name = scanner.nextLine();
                
                System.out.print("Enter Student Grade: ");
                String grade = scanner.nextLine();
                
                students[i] = new Student(studentID, name, grade);
            }
            
            // Serialize all students to a single file
            String multipleFile = "students_multiple.ser";
            try (FileOutputStream fileOut = new FileOutputStream(multipleFile);
                 ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
                
                objectOut.writeObject(students);
                objectOut.flush();
                
                System.out.println("\n✓ SUCCESS!");
                System.out.println(count + " students serialized successfully!");
                System.out.println("File: " + multipleFile);
                System.out.println("═".repeat(50) + "\n");
            }
            
        } catch (NumberFormatException e) {
            System.out.println("\n✗ Error: Invalid input! Please enter valid numbers.\n");
        } catch (IOException e) {
            System.out.println("\n✗ Serialization Error: " + e.getMessage() + "\n");
        }
    }
    
    // Method to deserialize multiple students
    private static void deserializeMultipleStudents() {
        System.out.println("\n" + "═".repeat(50));
        System.out.println("    DESERIALIZE MULTIPLE STUDENTS");
        System.out.println("═".repeat(50));
        
        String multipleFile = "students_multiple.ser";
        File file = new File(multipleFile);
        
        if (!file.exists()) {
            System.out.println("\n✗ Error: File not found!");
            System.out.println("Please serialize multiple students first.\n");
            return;
        }
        
        try (FileInputStream fileIn = new FileInputStream(multipleFile);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            
            // Read and deserialize the student array
            Student[] students = (Student[]) objectIn.readObject();
            
            System.out.println("\n✓ SUCCESS!");
            System.out.println(students.length + " students deserialized successfully!\n");
            
            // Display all students
            for (int i = 0; i < students.length; i++) {
                System.out.println("─".repeat(50));
                System.out.println("STUDENT " + (i + 1));
                System.out.println("─".repeat(50));
                System.out.println(students[i]);
                System.out.println();
            }
            
            System.out.println("═".repeat(50) + "\n");
            
        } catch (FileNotFoundException e) {
            System.out.println("\n✗ Error: File not found!\n");
        } catch (IOException e) {
            System.out.println("\n✗ Deserialization Error: " + e.getMessage() + "\n");
        } catch (ClassNotFoundException e) {
            System.out.println("\n✗ Error: Student class not found!\n");
        }
    }
}
