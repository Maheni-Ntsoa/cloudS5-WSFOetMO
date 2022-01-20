package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Client;
import com.webservice.foetmobile.modele.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByUsernameAndMdp(String username, String mdp);

    @Modifying
    @Query(value = "insert into Client values (DEFAULT, :nom, :prenom, :username, :mdp)", nativeQuery = true)
    void inscriptionClient (@Param("nom") String nom,
                            @Param("prenom") String prenom,
                            @Param("username") String username,
                            @Param("mdp") String mdp);

    @Modifying
    @Query(value = " insert into signalement values (DEFAULT, :idClient, localtimestamp(2), :designation," +
            " :idtypesignalement, :idregion, :idstatut, :latitude, :longitude) ", nativeQuery = true)
    void envoiSignalement(@Param("idClient") Long idClient,
                          @Param("designation") String designation,
                          @Param("idtypesignalement") Long idtypesignalement,
                          @Param("idregion") Long idregion,
                          @Param("idstatut") Long idstatut,
                          @Param("latitude") String latitude,
                          @Param("longitude") String longitude);

    @Query(value = "select * from signalement where idclient = :idclient order by dateheure desc limit 1", nativeQuery = true)
    Signalement findByIdClient(@Param("idclient") Long idclient);
}

