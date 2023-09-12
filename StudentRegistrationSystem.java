import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Course {
    private String code;
    private String title;
    private String description;
    private int capacity;
    private List<String> schedule;
    private List<Student> enrolledStudents;

    public Course(String code, String title, String description, int capacity) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.capacity = capacity;
        this.schedule = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<String> getSchedule() {
        return schedule;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public int getAvailableSlots() {
        return capacity - enrolledStudents.size();
    }

    public boolean enrollStudent(Student student) {
        if (getAvailableSlots() > 0) {
            enrolledStudents.add(student);
            return true;
        }
        return false;
    }

    public boolean dropStudent(Student student) {
        return enrolledStudents.remove(student);
    }

    public void addSchedule(String schedule) {
        this.schedule.add(schedule);
    }

    @Override
    public String toString() {
        return "Course: " + code + " - " + title + "\nDescription: " + description + "\nCapacity: " + capacity
                + "\nSchedule: " + schedule + "\nEnrolled Students: " + enrolledStudents.size() + "\n";
    }
}

class Student {
    private String studentID;
    private String name;
    private List<Course> registeredCourses;

    public Student(String studentID, String name) {
        this.studentID = studentID;
        this.name = name;
        this.registeredCourses = new ArrayList<>();
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public List<Course> getRegisteredCourses() {
        return registeredCourses;
    }

    public boolean registerCourse(Course course) {
        if (course.enrollStudent(this)) {
            registeredCourses.add(course);
            return true;
        }
        return false;
    }

    public boolean dropCourse(Course course) {
        if (registeredCourses.contains(course)) {
            if (course.dropStudent(this)) {
                registeredCourses.remove(course);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentID + "\nName: " + name + "\nRegistered Courses: " + registeredCourses.size()
                + "\n";
    }
}

public class StudentRegistrationSystem {
    public static void main(String[] args) {
        List<Course> courses = new ArrayList<>();
        List<Student> students = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n\n ----|| STUDENT REGISTRATION SYSTEM ||---");
            System.out.println("1. Add Course");
            System.out.println("2. List Available Courses");
            System.out.println("3. Register Student");
            System.out.println("4. Drop Student from Course");
            System.out.println("5. List Students");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Add Course
                    System.out.print("Enter Course Code: ");
                    String courseCode = scanner.nextLine();
                    System.out.print("Enter Course Title: ");
                    String courseTitle = scanner.nextLine();
                    System.out.print("Enter Course Description: ");
                    String courseDescription = scanner.nextLine();
                    System.out.print("Enter Course Capacity: ");
                    int courseCapacity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Course newCourse = new Course(courseCode, courseTitle, courseDescription, courseCapacity);

                    System.out.print("Enter Course Schedule (e.g., Mon 10:00 AM - 12:00 PM): ");
                    String schedule = scanner.nextLine();
                    newCourse.addSchedule(schedule);

                    courses.add(newCourse);
                    break;

                case 2:
                    // List Available Courses
                    System.out.println("Available Courses:");
                    for (Course course : courses) {
                        if (course.getAvailableSlots() > 0) {
                            System.out.println(course);
                        }
                    }
                    break;

                case 3:
                    // Register Student
                    System.out.print("Enter Student ID: ");
                    String studentID = scanner.nextLine();
                    System.out.print("Enter Student Name: ");
                    String studentName = scanner.nextLine();

                    Student newStudent = new Student(studentID, studentName);
                    students.add(newStudent);

                    System.out.println("Available Courses:");
                    for (int i = 0; i < courses.size(); i++) {
                        Course course = courses.get(i);
                        if (course.getAvailableSlots() > 0) {
                            System.out.println(i + 1 + ". " + course.getTitle());
                        }
                    }

                    System.out.print("Enter the number of the course to register for: ");
                    int courseNumber = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    if (courseNumber >= 1 && courseNumber <= courses.size()) {
                        Course selectedCourse = courses.get(courseNumber - 1);
                        if (newStudent.registerCourse(selectedCourse)) {
                            System.out.println("Student registered for the course.");
                        } else {
                            System.out.println("Course is full. Registration failed.");
                        }
                    } else {
                        System.out.println("Invalid course selection.");
                    }
                    break;

                case 4:
                    // Drop Student from Course
                    System.out.print("Enter Student ID: ");
                    String studentIDToDrop = scanner.nextLine();
                    Student studentToDrop = null;
                    for (Student student : students) {
                        if (student.getStudentID().equals(studentIDToDrop)) {
                            studentToDrop = student;
                            break;
                        }
                    }

                    if (studentToDrop != null) {
                        System.out.println("Courses registered by " + studentToDrop.getName() + ":");
                        List<Course> registeredCourses = studentToDrop.getRegisteredCourses();
                        for (int i = 0; i < registeredCourses.size(); i++) {
                            Course courseToDrop = registeredCourses.get(i);
                            System.out.println(i + 1 + ". " + courseToDrop.getTitle());
                        }

                        System.out.print("Enter the number of the course to drop: ");
                        int courseNumberToDrop = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        if (courseNumberToDrop >= 1 && courseNumberToDrop <= registeredCourses.size()) {
                            Course courseToDrop = registeredCourses.get(courseNumberToDrop - 1);
                            if (studentToDrop.dropCourse(courseToDrop)) {
                                System.out.println("Student dropped from the course.");
                            } else {
                                System.out.println("Failed to drop the course.");
                            }
                        } else {
                            System.out.println("Invalid course selection.");
                        }
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;

                case 5:
                    // List Students
                    System.out.println("Registered Students:");
                    for (Student student : students) {
                        System.out.println(student);
                    }
                    break;

                case 6:
                    // Exit
                    System.out.println("Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}
