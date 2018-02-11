package agent;

public class AgentDTO {

    public String Name;
    public String Location;
    public String Email;
    public String ID;
    public String Kind;
    public int KindCode;

    public AgentDTO() {}

    public AgentDTO(UserInfo user) {
        this.Name = user.getFirstName() + user.getLastName();
        //this.Location = user.getLocation();
        this.Email = user.getEmail();
        this.ID = user.getNIF();
        this.Kind = user.getKind();
        //this.KindCode;
    }
}
