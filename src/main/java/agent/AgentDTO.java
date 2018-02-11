package agent;

public class AgentDTO {

    public String name;
    public String location;
    public String email;
    public String ID;
    public String kind;
    public String kindcode;

    public AgentDTO() {}

    public AgentDTO(AgentInfo user) {
        this.name = user.getName();
        this.location = user.getLocation();
        this.email = user.getEmail();
        this.ID = user.getNIF();
        this.kind = user.getKind();
        this.kindcode = user.getKindCode();
    }
}
