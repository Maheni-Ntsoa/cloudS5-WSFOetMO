package com.webservice.foetmobile.modele;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Signalement")
public class Signalement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "idClient")
    private Long idClient;

    @Column(name = "dateheure")
    private LocalDateTime dateheure;

    @Column(name = "designation")
    private String designation;

    @Column(name = "idtypesignalement")
    private Long idtypesignalement;

    @Column(name = "idregion")
    private Long idregion;

    @Column(name = "idstatut")
    private Long idstatut;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    public Signalement() {
        super();
    }

    public Signalement(Long idClient, LocalDateTime dateheure, String designation, Long idtypesignalement, Long idregion, Long idstatut, String latitude, String longitude) {
        super();
        this.idClient = idClient;
        this.dateheure = dateheure;
        this.designation = designation;
        this.idtypesignalement = idtypesignalement;
        this.idregion = idregion;
        this.idstatut = idstatut;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
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