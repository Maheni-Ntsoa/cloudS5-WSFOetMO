package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Consommateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ConsommateurRepository extends JpaRepository<Consommateur, Long> {

}
