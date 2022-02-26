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

    @Query(value = "SELECT * from signalement_complet\n"+
            "\t where typesignalement = :idtypesignalement and idregion= :idregion\n" +
            "   and dateheure between :dateheure1 and :dateheure2 and nomstatut = :idstatut", nativeQuery = true)
    List<SignalementComplet> findByIdTypesignalementAndDateheure1AndDateheure2AndIdstatut(@Param("idtypesignalement") String typesignalement,
                                                                                          @Param("dateheure1") LocalDateTime dateheure1,
                                                                                          @Param("dateheure2") LocalDateTime dateheure2,
                                                                                          @Param("idstatut") String statut,
                                                                                          @Param("idregion") Long idregion);

    @Query(value = "SELECT * from signalement_complet where idregion = :idregion", nativeQuery = true)
    List<SignalementComplet> findByIdregion(@Param("idregion") Long idregion);

    @Query(value = "SELECT * from signalement_complet where idtypesignalement = :idtypesignalement", nativeQuery = true)
    List<SignalementComplet> findByIdTypesignalement(@Param("idtypesignalement") Long idtypesignalement);

    @Query(value = "SELECT * from signalement_complet where idClient = :idclient", nativeQuery = true)
    List<SignalementComplet> findByIdclient(@Param("idclient") Long idclient);

    @Query(value = "SELECT * from signalement_complet where idClient = :idclient and id = :id", nativeQuery = true)
    SignalementComplet findByIdclientAndAndId(@Param("idclient") Long idclient,
                                              @Param("id") Long id);
    @Query(value = "SELECT * from signalement_complet where id = :id", nativeQuery = true)
    SignalementComplet findByidMore(@Param("id") Long id);

}
