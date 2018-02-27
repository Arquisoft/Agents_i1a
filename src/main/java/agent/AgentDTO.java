package agent;

public class AgentDTO {

    private String name;
    private String location;
    private String email;
    private String id;
    private String kind;
    private String kindcode;


    public AgentDTO() {}

    public AgentDTO(AgentInfo user) {
        this.name = user.getName();
        this.location = user.getLocation();
        this.email = user.getEmail();
        this.id = user.getId();
        this.kind = user.getKind();
        this.kindcode = user.getKindCode();
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

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getKindcode() {
        return kindcode;
    }

    public void setKindcode(String kindcode) {
        this.kindcode = kindcode;
    }
}
