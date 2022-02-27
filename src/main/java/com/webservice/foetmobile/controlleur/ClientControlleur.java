package com.webservice.foetmobile.controlleur;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoClients;
import com.webservice.foetmobile.exception.ResourceNotFoundException;
import com.webservice.foetmobile.modele.*;
import com.webservice.foetmobile.repository.*;
import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/client")
public class ClientControlleur {

    public ClientControlleur(PhotosRepository pr, ClientRepository cr, SignalementCompletRepository scr, SignalementRepository sr, TokenClientRepository tcr, TypeSignalementRepository tsr) {
        this.pr = pr;
        this.cr = cr;
        this.scr = scr;
        this.sr = sr;
        this.tcr = tcr;
        this.tsr = tsr;
    }

    @Autowired
    PhotosRepository pr;

    @Autowired
    ClientRepository cr;

    @Autowired
    SignalementCompletRepository scr;

    @Autowired
    SignalementRepository sr;

    @Autowired
    TokenClientRepository tcr;

    @Autowired
    TypeSignalementRepository tsr;

    private Boolean checkToken(TokenClient tc) {
        if (tc.getDateexpiration().isAfter(LocalDateTime.now()) == true) {
            return true;
        }
        return false;
    }

    private String creationSha256(String mdp) throws NoSuchAlgorithmException {
//         Creation SHA -256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(mdp.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }
        String mdpSha256 = "\\x" + sb.toString();
        return mdpSha256;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> loginClient(@RequestBody Client client) {
        Map<String, String> map = new HashMap<>();
        String token = "";
        List<Client> la = cr.findByEmailAndMdp(client.getEmail(), client.getMdp());
        if (la.size() == 1) {
            token = cr.generateToken(la.get(0));
            Date input = new Date();
            LocalDateTime localDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime plusMinutes = localDate.plusMinutes(30);

            tcr.insertToken(la.get(0).getId(), token, plusMinutes);
            map.put("Token", token);
        } else {
            map.put("message", "Verifier vos informations");
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/inscription")
    public Client inscriptionClient(@Valid @RequestBody Client client) throws ResourceNotFoundException {
        return cr.save(client);
    }

    @GetMapping("/typesignalement/all")
    public ResponseEntity<Map> findAllTypeSignalement() {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map.put("data", tsr.findAll());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @PostMapping("/signalements/insert")
    public ResponseEntity<HashMap> insert(HttpServletRequest request, @RequestParam String es, @RequestParam MultipartFile file1) throws IOException {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Photos sary = new Photos();
        String token = (String) request.getHeader("Authorization");
        System.out.println(token);
        Signalement s = sr.findLast();
        System.out.println("idSignalementLast : " + s.getId());
        Signalement signa = new Signalement();

        TokenClient tc = tcr.findByToken(token.substring(7));
        if (checkToken(tc) == false) {
            map.put("message", "Token expire, veuillez vous reconnecter");
        } else {
            ObjectMapper objm = new ObjectMapper();
            signa = objm.readValue(es, Signalement.class);
            cr.envoiSignalement(tc.getIdclient(), signa.getDesignation(), signa.getIdtypesignalement(), signa.getLatitude(), signa.getLongitude());
            Binary[] nomsphotos = new Binary[1];
            nomsphotos[0] = new Binary(BsonBinarySubType.BINARY, file1.getBytes());
//            nomsphotos[1] = new Binary(BsonBinarySubType.BINARY, es.getFile2().getBytes());
//            nomsphotos[2] = new Binary(BsonBinarySubType.BINARY, es.getFile3().getBytes());

            Long t = s.getId() + 1;

            sary.setIdclient(tc.getIdclient().toString());
            System.out.println("idclient " + sary.getIdclient());
            sary.setIdSignalement(t.toString());
            System.out.println("idSignalement " + sary.getIdSignalement());
            sary.setPhotos(nomsphotos);
            System.out.println("nomphotos " + sary.getPhotos()[0]);

            MongoOperations mo = new MongoTemplate(MongoClients.create(), "photoSignalement");
            mo.insert(sary, "photos");

            map.put("envoiSignalement", Boolean.TRUE);
        }
        return ResponseEntity.ok(map);
    }

    @DeleteMapping(value = "/signalements/{idSignalement}")
    public ResponseEntity<Map> deleteSignalement(HttpServletRequest request,
                                                 @PathVariable("idSignalement") String idSignalement) {
        String token = (String) request.getHeader("Authorization");
        TokenClient tc = tcr.findByToken(token.substring(7));
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (checkToken(tc) == false) {
                map.put("message", "Token expire, veuillez vous reconnecter");
            } else {
                sr.deleteById(Long.parseLong(idSignalement));
                MongoOperations mo = new MongoTemplate(MongoClients.create(), "photoSignalement");
                mo.findAndRemove(new Query(Criteria.where("idSignalement").is(idSignalement)), Photos.class);
                map.put("message", Boolean.TRUE);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping(value = "/signalements")
    public ResponseEntity<Map> listeSignalementParIdClient(HttpServletRequest request) {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        String token = (String) request.getHeader("Authorization");
        TokenClient tc = tcr.findByToken(token.substring(7));
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            if (checkToken(tc) == false) {
                map.put("message", "Token expire, veuillez vous reconnecter");
            } else {
                lsc = scr.findByIdclient(tc.getIdclient());
                map.put("data", lsc);
            }
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
    }

    @GetMapping("/signalements/voirplus/{idsignalement}")
    public ResponseEntity<HashMap> seeMoreSignalement(HttpServletRequest request, @PathVariable(value = "idsignalement") Long idsignalement) {
        Photos photos = new Photos();
        HashMap<String, Object> map = new HashMap<>();
        String token = (String) request.getHeader("Authorization");
        TokenClient tc = tcr.findByToken(token.substring(7));
        if (checkToken(tc) == false) {
            map.put("message", "Token expire, veuillez vous reconnecter");
        } else {
            MongoOperations mo = new MongoTemplate(MongoClients.create(), "photoSignalement");
            photos = mo.findOne(new Query(where("idClient").is(tc.getIdclient().toString()).and("idSignalement").is(idsignalement.toString())), Photos.class);
            map.put("lesPhotos", photos);
            map.put("signalements", scr.findByIdclientAndAndId(Long.parseLong(tc.getIdclient().toString()), idsignalement));
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
