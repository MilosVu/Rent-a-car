package domain;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    public static final long serialVersionUID =2L;

    public User(String username, String password, String firstname, String lastname, String email) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User() {
    }
}
