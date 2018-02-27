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


    private String password;
    private String name;
    private String location;
    private String email;
    private String kind;
    @Id
    private String id;
    private String kindcode;

    private AgentInfo() {}
    
    public AgentInfo(String name, String email, String kind,String id,String password) {
        this.name = name;
        this.email = email;
        this.kind = kind;
        this.id = id;
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

    public AgentInfo(String name, String email, String kind, String id, String password,String location) {
        this(name,email,kind,id,password);
        this.location = location;
    }
    
    public AgentInfo(String[] data) {
        this(data[0], data[1], data[3], data[4], data[5], data[6]);
    }

    public String getPassword() { return password; }

    public void setPassword(String pw) { this.password = pw; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getLocation(){ return location; }

    public String getId() { return id; }

    public String getKind(){  return this.kind; }

    public String getKindCode(){ return kindcode; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AgentInfo agentInfo = (AgentInfo) o;
        return Objects.equals(password, agentInfo.password) &&
                Objects.equals(name, agentInfo.name) &&
                Objects.equals(location, agentInfo.location) &&
                Objects.equals(email, agentInfo.email) &&
                Objects.equals(kind, agentInfo.kind) &&
                Objects.equals(id, agentInfo.id) &&
                Objects.equals(kindcode, agentInfo.kindcode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(password, name, location, email, kind, id, kindcode);
    }

    @Override
    public String toString() {
        return "AgentInfo{" +
                "password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", email='" + email + '\'' +
                ", kind='" + kind + '\'' +
                ", id='" + id + '\'' +
                ", kindcode='" + kindcode + '\'' +
                '}';
    }
}