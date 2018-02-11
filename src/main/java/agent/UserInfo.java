package agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "users")
public class UserInfo {

    // Log
    private static final Logger LOG = LoggerFactory.getLogger(UserInfo.class);

    @Id
    private String id;

    private String password;
    private String address;
    private int pollingStation;
    
    private String firstName;
    private String lastName;
    private String NIF;
    private String email;
    private String kind;

    private UserInfo() {}
    
    public UserInfo(String firstName, String lastName, String email, String kind) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.kind = kind;
    }
    
    public UserInfo(String password, String firstName, String lastName, String email, String kind) {
        this(firstName, lastName, email, kind);
    	this.password = password;
    }

    public UserInfo(String firstName, String lastName, String email,
            String address, String ID, String NIF, String kind,
            String pollingStation) {
    	
    	this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.address = address;
        this.id = ID;
        this.NIF = NIF;
        this.kind = kind;
        this.pollingStation = Integer.parseInt(pollingStation);
    }
    
    public UserInfo(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getId() {
        return id;
    }
    
    public void setPassword(String pw) {
        this.password = pw;
    }

    public String getNIF() {
        return NIF;
    }

    public int getPollingStation() {
        return pollingStation;
    }

    public String getKind(){
        return this.kind;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInfo other = (UserInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserInfo [ID=" + id + ", password=" + password + ", address=" + address
				+ ", pollingStation=" + pollingStation + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", NIF=" + NIF + ", email=" + email
                + ", kind=" + kind + "]";
	}

}