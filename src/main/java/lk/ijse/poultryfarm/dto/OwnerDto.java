package lk.ijse.poultryfarm.dto;

public class OwnerDto {

    private int ownerId;
    private String name;
    private String password;
    private String email;
    private String username;

    public OwnerDto(int ownerId, String name, String password, String email, String username) {
        this.ownerId = ownerId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.username = username;
    }

    public OwnerDto() {
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
