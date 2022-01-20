package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.AdminRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface AdminRegionRepository extends JpaRepository<AdminRegion, Long> {

    @Query(value = "select * from AdminRegion where username = :username and mdp = :mdp", nativeQuery = true)
    List<AdminRegion> findByUsernameAndMdp(@Param("username") String username, @Param("mdp") String mdp);

}
