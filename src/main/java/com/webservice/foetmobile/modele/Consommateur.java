package com.webservice.foetmobile.modele;

import javax.persistence.*;

@Entity
@Table(name = "Consommateur")
public class Consommateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "mdp")
    private String mdp;

    public Consommateur(String email, String mdp) {
        this.email = email;
        this.mdp = mdp;
    }

    public Consommateur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
