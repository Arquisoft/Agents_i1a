package agent;

public class AgentDTO {

    private String name;
    private String location;
    private String email;
    private String id;
    private int kind;


    public AgentDTO() {}

    public AgentDTO(Agent user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.location = user.getLocation();
        this.id = user.getId();
        this.kind = user.getKind();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getID() {
        return id;
    }

    public void setID(String id) {
        this.id = id;
    }

    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

}
