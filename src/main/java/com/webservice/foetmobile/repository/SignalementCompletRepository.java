package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.SignalementComplet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface SignalementCompletRepository extends JpaRepository<SignalementComplet, Long> {

    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut,\n" +
            "        latitude,\n" +
            "        longitude\n" +
            "    FROM signalement_complet\n " +
            "\t where typesignalement = :idtypesignalement and idregion= :idregion\n" +
            "   and dateheure between :dateheure1 and :dateheure2 and nomstatut = :idstatut\n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut,latitude,longitude ", nativeQuery = true)
    List<SignalementComplet> findByIdTypesignalementAndDateheure1AndDateheure2AndIdstatut(@Param("idtypesignalement") String typesignalement,
                                                                                          @Param("dateheure1") LocalDateTime dateheure1,
                                                                                          @Param("dateheure2") LocalDateTime dateheure2,
                                                                                          @Param("idstatut") String statut,
                                                                                          @Param("idregion") Long idregion);

    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut,\n" +
            "        latitude,\n" +
            "        longitude\n" +
            "    FROM signalement_complet\n" +
            "\t where idregion = :idregion\n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut ,latitude,longitude", nativeQuery = true)
    List<SignalementComplet> findByIdregion(@Param("idregion") Long idregion);

    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut\n" +
            "    FROM signalement_complet\n" +
            "\t where idtypesignalement = :idtypesignalement\n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut ", nativeQuery = true)
    List<SignalementComplet> findByIdTypesignalement(@Param("idtypesignalement") Long idtypesignalement);

    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut\n" +
            "    FROM signalement_complet\n" +
            "\t where idclient = :idclient\n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut ", nativeQuery = true)
    List<SignalementComplet> findByIdclient(@Param("idclient") Long idclient);

    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut\n" +
            "    FROM signalement_complet\n" +
            "\t where idclient = :idclient and id = :id \n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut ", nativeQuery = true)
    SignalementComplet findByIdclientAndAndId(@Param("idclient") Long idclient,
                                              @Param("id") Long id);
    @Query(value = "SELECT  id,\n" +
            "        idClient,\n" +
            "        idTypeSignalement,\n" +
            "        idRegion,\n" +
            "        idStatut,\n" +
            "        nom,\n" +
            "        prenom,\n" +
            "        email,\n" +
            "        dateHeure,\n" +
            "        designation,\n" +
            "        typeSignalement,\n" +
            "        nomRegion,\n" +
            "        nomStatut\n" +
            "    FROM signalement_complet\n" +
            "\t where  id = :id \n" +
            "    GROUP BY id, idClient, idTypeSignalement,\n" +
            "            idRegion, idStatut, nom, prenom, email,\n" +
            "            dateHeure, designation,\n" +
            "            typeSignalement, nomRegion, nomStatut ", nativeQuery = true)
    SignalementComplet findByidMore(@Param("id") Long id);

}
