package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.SignalementComplet;
import com.webservice.foetmobile.modele.TypeSignalement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface TypeSignalementRepository extends JpaRepository<TypeSignalement, Long> {
}
