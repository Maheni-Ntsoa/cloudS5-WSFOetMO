package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.TokenAdminRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface TokenAdminRegionRepository extends JpaRepository<TokenAdminRegion, Long> {

    @Query(value = " select * from token_admin_region where token = :token", nativeQuery = true)
    public TokenAdminRegion findByToken(@Param("token") String token);

    @Modifying
    @Query(value = " insert into token_admin_region values (default, :idadminregion, :token, :dateexpiration) ", nativeQuery = true)
    public void insertToken(@Param("idadminregion") Long idadminregion,
                            @Param("token") String token,
                            @Param("dateexpiration") LocalDateTime dateexpiration);

}
