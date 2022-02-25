package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.AdminRegion;
import com.webservice.foetmobile.modele.Client;
import com.webservice.foetmobile.utilitaire.Utilitaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query(value = " select * from client " , nativeQuery = true)
    List<Client> findTout();

    @Query(value = " select * from client where email = :email and mdp = :mdp ", nativeQuery = true)
    List<Client> findByEmailAndMdp(@Param("email")  String email, @Param("mdp")  String mdp);

    public default String generateToken(Client c){
        String token = c.getEmail() + c.getMdp() + LocalDateTime.now().toString();
        String tokenAdminRegion = Utilitaire.encryptPassword(token);
        return tokenAdminRegion;
    }

    @Modifying
    @Query(value = "insert into Client values (DEFAULT, :nom, :prenom, :email, :mdp)", nativeQuery = true)
    void inscriptionClient(@Param("nom") String nom,
                           @Param("prenom") String prenom,
                           @Param("email") String email,
                           @Param("mdp") String mdp);

    @Modifying
    @Query(value = " insert into signalement values (DEFAULT, :idClient, localtimestamp(2), :designation," +
            " :idtypesignalement, 0, 1, :latitude, :longitude) ", nativeQuery = true)
    void envoiSignalement(@Param("idClient") Long idClient,
                          @Param("designation") String designation,
                          @Param("idtypesignalement") Long idtypesignalement,
                          @Param("latitude") String latitude,
                          @Param("longitude") String longitude);

}

