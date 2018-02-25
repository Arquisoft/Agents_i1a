package agent;

public class AgentDTO {

    private String name;
    private String location;
    private String email;
    private String ID;
    private String kind;
    private String kindcode;

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
