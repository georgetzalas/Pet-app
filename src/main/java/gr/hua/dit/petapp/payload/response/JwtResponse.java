package gr.hua.dit.petapp.payload.response;

import java.util.List;

public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String username;
    private String email;
    private List<String> roles;
    private String surname;
    private String region;

    public JwtResponse(String accessToken, Long id, String name, String username, String email, List<String> roles,String surname, String region) {
        this.token = accessToken;
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.surname = (surname != null) ? surname : "N/A"; // Default to "N/A" if null
        this.region = (region != null) ? region : "N/A";   // Default to "N/A" if null
    }
    public JwtResponse(String accessToken, Long id, String name, String username, String email, List<String> roles) {
        this(accessToken, id, name, username, email, roles, null, null);
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<String> getRoles() {
        return roles;
    }

    public String getSurname() {
        return (surname != null) ? surname : "N/A";
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getRegion() {
        return (region != null) ? region : "N/A";
    }

    public void setRegion(String region) {
        this.region = region;
    }
}