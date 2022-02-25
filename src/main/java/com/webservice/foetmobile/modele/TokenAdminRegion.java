package com.webservice.foetmobile.modele;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_admin_region")
public class TokenAdminRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_admin_region")
    private Long idadminaegion;

    @Column(name = "token")
    private String token;

    @Column(name = "date_expiration")
    private LocalDateTime dateexpiration;

}
