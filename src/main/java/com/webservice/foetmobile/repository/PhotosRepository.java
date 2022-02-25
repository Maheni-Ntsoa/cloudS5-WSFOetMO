package com.webservice.foetmobile.repository;

import com.webservice.foetmobile.modele.Photos;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface PhotosRepository extends MongoRepository<Photos, String> {

    public default void savePhoto(Photos photo) {
        this.save(photo);
    }
}
