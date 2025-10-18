package entity;

public class User {

    private Integer id;
    private String name;
    private String lastName;
    private String login;
    private String password;
    private String image;

    public User() {
    }

    public User(Integer id, String name, String lastName, String login, String password, String image) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Integer getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
