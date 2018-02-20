package agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection= "users")
public class AgentInfo {

    // Log
    private static final Logger LOG = LoggerFactory.getLogger(AgentInfo.class);

    @Id
    private String id;

    private String password;
    private String name;
    private String username;
    private String location;
    private String email;
    private String kind;
    private String NIF;
    private String kindcode;

    private AgentInfo() {}
    
    public AgentInfo(String name, String username, String email, String kind,String NIF,String password) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.kind = kind;
        this.NIF = NIF;
        this.password = password;
        this.location = "";
        assignKindCode(kind);
    }

    private void assignKindCode(String kind) {
        switch (kind){
            case "person":
                this.kindcode = "1";
                break;
            case "entity":
                this.kindcode = "2";
                break;
            case "sensor":
                this.kindcode ="3";
                break;
        }
    }

    public AgentInfo(String name, String username, String email,
                     String kind, String NIF, String password,String location) {
        this(name,username,email,kind,NIF,password);
        this.location = location;
    }
    
    public AgentInfo(String[] data) {
        this(data[0], data[1], data[2], data[3], data[4], data[5], data[6]);
    }

    public String getPassword() { return password; }

    public void setPassword(String pw) { this.password = pw; }

    public String getName() { return name; }

    public String getUsername() { return username; }

    public String getEmail() { return email; }

    public String getLocation(){ return location; }

    public String getId() { return id; }

    public String getKind(){  return this.kind; }

    public String getKindCode(){ return kindcode; }

    public String getNIF() { return NIF; }

    @Override
    public String toString() {
        return "AgentInfo{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", kind='" + kind + '\'' +
                ", NIF='" + NIF + '\'' +
                ", kindcode='" + kindcode + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentInfo agentInfo = (AgentInfo) o;
        return Objects.equals(id, agentInfo.id) &&
                Objects.equals(password, agentInfo.password) &&
                Objects.equals(name, agentInfo.name) &&
                Objects.equals(username, agentInfo.username) &&
                Objects.equals(location, agentInfo.location) &&
                Objects.equals(email, agentInfo.email) &&
                Objects.equals(kind, agentInfo.kind) &&
                Objects.equals(NIF, agentInfo.NIF) &&
                Objects.equals(kindcode, agentInfo.kindcode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, password, name, username, location, email, kind, NIF, kindcode);
    }
}