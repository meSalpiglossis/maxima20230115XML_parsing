public class Employee {
    public Integer id;
    public String firstName;
    public String lastName;
    public String location;

    @Override
    public String toString() {
        return "Employee [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", location=" + location + "]";
    }
}
