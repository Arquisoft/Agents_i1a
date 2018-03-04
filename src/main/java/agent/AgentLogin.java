package agent;

public class AgentLogin {

    private String id, password;
    private int kind;

    public String getId() { return id; }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getKind() { return kind; }

    public void setKind(int kind) { this.kind = kind;}
    
}
