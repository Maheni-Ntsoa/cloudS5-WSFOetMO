package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.TokenAdminRegion;
import com.webservice.foetmobile.modele.TokenClient;
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
public interface TokenClientRepository extends JpaRepository<TokenClient, Long> {

    @Query(value = " select * from token_client where token = :token", nativeQuery = true)
    public TokenClient findByToken(@Param("token") String token);

    @Modifying
    @Query(value = "insert into token_client values (DEFAULT, :id_client, :token, :date_expiration)", nativeQuery = true)
    public void insertToken(@Param("id_client") Long idclient,
                            @Param("token") String token,
                            @Param("date_expiration") LocalDateTime dateexpiration);
}
