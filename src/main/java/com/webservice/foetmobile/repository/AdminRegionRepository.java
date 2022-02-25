package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.AdminRegion;
import com.webservice.foetmobile.utilitaire.Utilitaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface AdminRegionRepository extends JpaRepository<AdminRegion, Long> {

    @Query(value = " select * from adminregion " , nativeQuery = true)
    List<AdminRegion> findTout();

    @Query(value = " select * from adminregion where id = :id " , nativeQuery = true)
    AdminRegion findAdminRegion(@Param("id") Long id);

    @Query(value = " select * from adminregion where email = :email and mdp = :mdp ", nativeQuery = true)
    List<AdminRegion> findByEmailAndMdp(@Param("email")  String email, @Param("mdp")  String mdp);

    public default String generateToken(AdminRegion adminRegion) {
        String token = adminRegion.getEmail() + adminRegion.getMdp() + LocalDateTime.now().toString();
        String tokenAdminRegion = Utilitaire.encryptPassword(token);
        return tokenAdminRegion;
    }

    @Query(value = " select * from adminregion where email = :email ", nativeQuery = true)
    AdminRegion findByEmail(@Param("email")  String email);
}
