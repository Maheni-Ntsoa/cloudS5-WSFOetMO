package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.SignalementComplet;
import com.webservice.foetmobile.modele.TypeSignalement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeSignalementRepository extends JpaRepository<TypeSignalement, Long> {
}
