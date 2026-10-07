package studentregistration;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty program = new SimpleStringProperty();

    public Student(String name, String program) {
        this.name.set(name);
        this.program.set(program);
    }

    // Normal getters
    public String getName() { return name.get(); }
    public String getProgram() { return program.get(); }

    // Property accessors used by the table columns
    public StringProperty nameProperty() { return name; }
    public StringProperty programProperty() { return program; }
}
