package com.webservice.foetmobile.controlleur;

import com.mongodb.client.MongoClients;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.webservice.foetmobile.exception.ResourceNotFoundException;
import com.webservice.foetmobile.modele.Client;
import com.webservice.foetmobile.modele.Photos;
import com.webservice.foetmobile.modele.Signalement;
import com.webservice.foetmobile.modele.SignalementComplet;
import com.webservice.foetmobile.repository.ClientRepository;
import com.webservice.foetmobile.repository.PhotosRepository;
import com.webservice.foetmobile.repository.SignalementCompletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/client/")
public class ClientControlleur {

    public ClientControlleur() {
        System.out.println("ClientControlleur()");
    }

    @Autowired
    PhotosRepository photosRepository;

    @Autowired
    ClientRepository cr;

    @Autowired
    SignalementCompletRepository scr;

    @PostMapping(value = "/login/{username}/{mdp}")
    public ResponseEntity<Client> loginClient(@PathVariable("username") String username, @PathVariable("mdp") String mdp) throws ResourceNotFoundException, NoSuchAlgorithmException {
        // Creation SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(mdp.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        Client client = new Client();
        List<Client> lc = new ArrayList<Client>();
        try {
            lc = cr.findByUsernameAndMdp(username, "\\x" + sb.toString());
            if (lc.size() == 1) {
                for (Client cl : lc) {
                    client = cl;
                }
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Client not found");
        }
        return ResponseEntity.ok().body(client);
    }

    @PostMapping(value = "/clients/{nom}/{prenom}/{username}/{mdp}")
    public HashMap inscriptionClient(@PathVariable("nom") String nom,
                                     @PathVariable("prenom") String prenom,
                                     @PathVariable("username") String username,
                                     @PathVariable("mdp") String mdp) throws ResourceNotFoundException {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        try {
            // Creation SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(mdp.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            String mdpSha256 = "\\x" + sb.toString();

            cr.inscriptionClient(nom, prenom, username, mdpSha256);
            map.put("inserer", Boolean.TRUE);
            return map;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Incription error, please check");
        }
    }

    @PostMapping("/signalements/{idClient}/{designation}/{idtypesignalement}" +
            "/{idregion}/{idstatut}/{latitude}/{longitude}/{nomsphotos}")
    public HashMap envoiSignalement(@PathVariable("idClient") Long idClient,
                                 @PathVariable("designation") String designation,
                                 @PathVariable("idtypesignalement") Long idtypesignalement,
                                 @PathVariable("idregion") Long idregion,
                                 @PathVariable("idstatut") Long idstatut,
                                 @PathVariable("latitude") String latitude,
                                 @PathVariable("nomsphotos") String[] nomsphotos, @PathVariable("longitude") String longitude) throws ResourceNotFoundException {
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        Photos photos = new Photos();
        try {
            cr.envoiSignalement(idClient, designation, idtypesignalement, idregion, idstatut, latitude, longitude);

            Signalement s = cr.findByIdClient(idClient);

            photos.setIdclient(idClient.toString());
            photos.setIdSignalement(s.getId().toString());
            photos.setPhotos(nomsphotos);
            MongoOperations mo = new MongoTemplate(MongoClients.create(), "photoSignalement");
            mo.insert(photos, "photos");
            map.put("envoiSignalement", Boolean.TRUE);
            return map;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Erreur d'envoi du signalement, verifier");
        }
    }

    @GetMapping(value = "/signalements/{idclient}")
    public ResponseEntity<List<SignalementComplet>> listeSignalementParIdClient(@PathVariable("idclient") Long idclient) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        try {
            lsc = scr.findByIdclient(idclient);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: " + idclient);
        }
        return ResponseEntity.ok().body(lsc);
    }

    @GetMapping("/signalements?idclient={idclient}&idsignalement={idsignalement}")
    public HashMap seeMoreSignalement(@PathVariable(value = "idclient") Long idclient, @PathVariable(value = "idsignalement") Long idsignalement, Model m) throws ResourceNotFoundException {
        Photos photos = new Photos();
        HashMap<String, Object> map = new HashMap<>();
        try {
            MongoOperations mo = new MongoTemplate(MongoClients.create(), "photoSignalement");
            photos = mo.findOne(new Query(where("idClient").is(idclient.toString()).and("idSignalement").is(idsignalement.toString())), Photos.class);
            map.put("lesPhotos", photos);
            map.put("signalements", scr.findByIdclientAndAndId(idclient, idsignalement));
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: " + idclient);
        }
        return map;
    }

}
