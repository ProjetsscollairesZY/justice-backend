package com.Justice.Justice.Models;

import jakarta.persistence.*;

@Entity
@Table(name = "admins")
public class Admin {
    
    @Id
    @Column(name = "admin_id")
    private String adminId;  
    
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "email", unique = true)
    private String email;
    
    @Column(name = "password")
    private String password;
    
    @Column(name = "tribunal_id")
    private String tribunalId;
    
    @ManyToOne
    @JoinColumn(name = "tribunal_id", insertable = false, updatable = false)
    private Tribunal tribunal;
    
    public Admin() {}
    
    public Admin(String adminId, String username, String email, String password, String tribunalId) {
        this.adminId = adminId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.tribunalId = tribunalId;
    }
    
    // Getters et Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getTribunalId() { return tribunalId; }
    public void setTribunalId(String tribunalId) { this.tribunalId = tribunalId; }
    
    public Tribunal getTribunal() { return tribunal; }
    public void setTribunal(Tribunal tribunal) { this.tribunal = tribunal; }
}