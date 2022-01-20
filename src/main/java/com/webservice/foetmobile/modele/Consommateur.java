package com.webservice.foetmobile.modele;

import javax.persistence.*;

@Entity
@Table(name = "Consommateur")
public class Consommateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "mdp")
    private String mdp;

    public Consommateur(String username, String mdp) {
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
