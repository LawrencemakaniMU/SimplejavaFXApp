package studentregistration;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class StudentService {
    // Demo programs for the combo box
    public static final List<String> PROGRAMS = List.of(
            "Computer Science",
            "Information Technology",
            "Electrical Engineering",
            "Mechanical Engineering",
            "Civil Engineering");

    private final ObservableList<Student> students = FXCollections.observableArrayList();

    public ObservableList<Student> getStudents() {
        return students;
    }

    public void register(String name, String program) {
        // Tidy the name: trim the ends and collapse repeated spaces
        String cleanName = (name == null) ? "" : name.trim().replaceAll("\\s+", " ");

        if (cleanName.length() < 2) {
            throw new IllegalArgumentException("Name must have at least 2 characters.");
        }
        if (!cleanName.matches("[\\p{L} .'-]+")) {
            throw new IllegalArgumentException("Name may only contain letters, spaces, hyphens and apostrophes.");
        }
        if (program == null) {
            throw new IllegalArgumentException("Please choose a program.");
        }

        boolean duplicate = students.stream().anyMatch(s ->
                s.getName().equalsIgnoreCase(cleanName) && s.getProgram().equals(program));
        if (duplicate) {
            throw new IllegalArgumentException(cleanName + " is already registered for " + program + ".");
        }

        students.add(new Student(cleanName, program));
    }
}
