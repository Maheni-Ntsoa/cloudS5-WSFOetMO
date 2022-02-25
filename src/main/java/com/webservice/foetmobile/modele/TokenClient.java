package com.webservice.foetmobile.modele;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "token_client")
public class TokenClient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_client")
    private Long idclient;

    @Column(name = "token")
    private String token;

    @Column(name = "date_expiration")
    private LocalDateTime dateexpiration;



}
