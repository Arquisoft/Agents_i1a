package agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection= "users")
public class AgentInfo {

    // Log
    private static final Logger LOG = LoggerFactory.getLogger(AgentInfo.class);

    @Id
    private String id;

    private String password;
    private int pollingStation;
    
    private String firstName;
    private String lastName;
    private Double[] location;
    private String email;
    private String NIF;
    private String kind;

    private AgentInfo() {}
    
    public AgentInfo(String firstName, String lastName, String email, String NIF, String kind) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.NIF = NIF;
        this.kind = kind;
    }
    
    public AgentInfo(String password, String firstName, String lastName, String email, String NIF, String kind) {
        this(firstName, lastName, email, NIF, kind);
    	this.password = password;
    }

    public AgentInfo(String firstName, String lastName, String email,
                     Double[] location, String ID, String NIF, String kind,
                     String pollingStation) {
    	
    	this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.location = location;
        this.id = ID;
        this.NIF = NIF;
        this.kind = kind;
        this.pollingStation = Integer.parseInt(pollingStation);
    }
    
    //public AgentInfo(String[] data) {
    //   this(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]);
    //}

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

    public Double[] getLocation(){
        return location;
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

    public int getKindCode(){
        //TODO
        return -1;
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
		AgentInfo other = (AgentInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AgentInfo [ID=" + id + ", password=" + password
				+ ", pollingStation=" + pollingStation + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", NIF=" + NIF + ", email=" + email
                + ", kind=" + kind + "]";
	}

}