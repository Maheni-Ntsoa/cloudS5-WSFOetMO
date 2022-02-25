package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Status;
import com.webservice.foetmobile.modele.TypeSignalement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatutRepository extends JpaRepository<Status, Long> {

}
