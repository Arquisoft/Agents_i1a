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
    private String location;
    private String email;
    private String kind;
    private String NIF;
    private String kindcode;

    private AgentInfo() {}
    
    public AgentInfo(String name, String email, String kind,String NIF,String password) {
        this.name = name;
        this.email = email;
        this.kind = kind;
        this.NIF = NIF;
        this.password = password;
        this.location = "";
        assignKindCode(kind);
    }

    private void assignKindCode(String kind) {
        if(kind.equals("person")){
            this.kindcode = "1";
        }else if(kind.equals("entity")){
            this.kindcode = "2";
        }
        else if(kind.equals("sensor")){
            this.kindcode ="3";
        }
    }


    public AgentInfo(String name, String email,
                     String kind,String NIF,String password,String location) {
        this(name,email,kind,NIF,password);
        this.location = location;

    }
    
    public AgentInfo(String[] data) {
       this(data[0], data[1], data[2], data[3], data[4], data[5]);
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLocation(){
        return location;
    }

    public String getId() {
        return NIF;
    }
    
    public void setPassword(String pw) {
        this.password = pw;
    }

    public String getKind(){
        return this.kind;
    }

    public String getKindCode(){return kindcode; }

    public String getNIF() {
        return NIF;
    }
    @Override
    public String toString() {
        return "AgentInfo{" +
                "id='" + NIF + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
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
        return Objects.equals(NIF, agentInfo.NIF) &&
                Objects.equals(password, agentInfo.password) &&
                Objects.equals(name, agentInfo.name) &&
                Objects.equals(location, agentInfo.location) &&
                Objects.equals(email, agentInfo.email) &&
                Objects.equals(kind, agentInfo.kind) &&
                Objects.equals(NIF, agentInfo.NIF) &&
                Objects.equals(kindcode, agentInfo.kindcode);
    }

    @Override
    public int hashCode() {

        return Objects.hash(NIF, password, name, location, email, kind, NIF, kindcode);
    }

}