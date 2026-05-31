package com.habitloop.dto;

public class AuthResponse {
    private String token;
    private String username;
    private String email;
    private Integer level;
    private Integer xp;

    public AuthResponse() {}

    public AuthResponse(String token, String username, String email,
                        Integer level, Integer xp) {
        this.token = token;
        this.username = username;
        this.email = email;
        this.level = level;
        this.xp = xp;
    }

    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public Integer getLevel() { return level; }
    public Integer getXp() { return xp; }

    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setLevel(Integer level) { this.level = level; }
    public void setXp(Integer xp) { this.xp = xp; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private String username;
        private String email;
        private Integer level;
        private Integer xp;

        public Builder token(String t) { this.token = t; return this; }
        public Builder username(String u) { this.username = u; return this; }
        public Builder email(String e) { this.email = e; return this; }
        public Builder level(Integer l) { this.level = l; return this; }
        public Builder xp(Integer x) { this.xp = x; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, username, email, level, xp);
        }
    }
}