package com.webservice.foetmobile.modele;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Immutable
@Table(name = "`signalement_complet`")
@Subselect("SELECT * from signalement_complet")
public class SignalementComplet {

    @Id
    private Long id;
    private Long idclient;
    private Long idtypesignalement;
    private Long idregion;
    private Long idstatut;
    private String nom;
    private String prenom;
    private String email;
    private LocalDateTime dateheure;
    private String designation;
    private String typesignalement;
    private String nomregion;
    private String nomstatut;
    private String latitude;
    private String longitude;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdclient() {
        return idclient;
    }

    public void setIdclient(Long idclient) {
        this.idclient = idclient;
    }

    public Long getIdtypesignalement() {
        return idtypesignalement;
    }

    public void setIdtypesignalement(Long idtypesignalement) {
        this.idtypesignalement = idtypesignalement;
    }

    public Long getIdregion() {
        return idregion;
    }

    public void setIdregion(Long idregion) {
        this.idregion = idregion;
    }

    public Long getIdstatut() {
        return idstatut;
    }

    public void setIdstatut(Long idstatut) {
        this.idstatut = idstatut;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public LocalDateTime getDateheure() {
        return dateheure;
    }

    public void setDateheure(LocalDateTime dateheure) {
        this.dateheure = dateheure;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getTypesignalement() {
        return typesignalement;
    }

    public void setTypesignalement(String typesignalement) {
        this.typesignalement = typesignalement;
    }

    public String getNomregion() {
        return nomregion;
    }

    public void setNomregion(String nomregion) {
        this.nomregion = nomregion;
    }

    public String getNomstatut() {
        return nomstatut;
    }

    public void setNomstatut(String nomstatut) {
        this.nomstatut = nomstatut;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

