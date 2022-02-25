package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SignalementRepository extends JpaRepository<Signalement, Long> {

    @Query(value = " select * from Signalement order by dateheure desc limit 1 ", nativeQuery = true)
    Signalement findLast();

}
