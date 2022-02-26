package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Signalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface SignalementRepository extends JpaRepository<Signalement, Long> {

    @Query(value = " select * from Signalement order by dateheure desc limit 1 ", nativeQuery = true)
    Signalement findLast();

    @Modifying
    @Query(value = " delete from Signalement where id = :id ", nativeQuery = true)
    void deleteById(@Param("id") String id);

}
