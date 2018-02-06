package hello;

public class CitizenDTO {

    public String firstName;
    public String lastName;
    public Integer age;
    public String ID;
    public String email;

    public CitizenDTO() {}

    public CitizenDTO(UserInfo user) {
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.age = user.getAge();
        this.ID = user.getNIF();
        this.email = user.getEmail();
    }
}
