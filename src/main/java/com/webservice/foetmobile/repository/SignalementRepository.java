package com.webservice.foetmobile.repository;

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
public interface SignalementRepository extends JpaRepository<Signalement, Long> {

    @Modifying
    @Query("update Signalement s set s.idregion = :idregion where s.id = :idsignalement")
    void affecterRegion(@Param("idsignalement") Long idsignalement, @Param("idregion") Long idregion);

    @Query("select s from Signalement s where s.idregion = 0")
    List<Signalement> findByIdRegionNull();

    @Query("select s from Signalement s where s.idtypesignalement=:idtypesignalement " +
            " and s.dateheure = :dateheure and s.idstatut = :idstatut")
    List<Signalement> findByIdtypesignalementAndDateheureAndAndIdstatut(@Param("idtypesignalement") Long idtypesignalement,
                                                                        @Param("dateheure") String dateheure,
                                                                        @Param("idstatut") Long idstatut);

}
