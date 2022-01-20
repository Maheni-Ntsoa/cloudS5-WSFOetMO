package com.webservice.foetmobile.controlleur;

import com.webservice.foetmobile.exception.ResourceNotFoundException;
import com.webservice.foetmobile.modele.AdminRegion;
import com.webservice.foetmobile.modele.SignalementComplet;
import com.webservice.foetmobile.repository.AdminRegionRepository;
import com.webservice.foetmobile.repository.SignalementCompletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/adminRegion/")
public class AdminRegionControlleur {

    public AdminRegionControlleur() {
        System.out.println("AdminRegionControlleur()");
    }

    @Autowired
    AdminRegionRepository arr;

    @Autowired
    SignalementCompletRepository scr;

    @GetMapping(value = "/signalements")
    public ResponseEntity <List<SignalementComplet>> test() throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        try {
            lsc = scr.findAll();
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: ");
        }
        return ResponseEntity.ok().body(lsc);
    }

    @PostMapping(value = "/login/{username}/{mdp}")
    public ResponseEntity<AdminRegion> loginAdminRegion(@PathVariable("username") String username, @PathVariable("mdp") String mdp) throws ResourceNotFoundException, NoSuchAlgorithmException {
        // Creation SHA-256
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hashInBytes = md.digest(mdp.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        for (byte b : hashInBytes) {
            sb.append(String.format("%02x", b));
        }

        AdminRegion admin = new AdminRegion();
        List<AdminRegion> la = new ArrayList<AdminRegion>();
        try {
            la = arr.findByUsernameAndMdp(username, "\\x" + sb.toString());
            if (la.size() == 1) {
                for (AdminRegion ad : la) {
                    admin = ad;
                }
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("AdminRegion not found");
        }
        return ResponseEntity.ok().body(admin);
    }

    @GetMapping(value = "/signalements/region/{idregion}")
    public ResponseEntity <List<SignalementComplet>> listeSignalementParRegion(@PathVariable("idregion") Long idregion) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        try {
            lsc = scr.findByIdregion(idregion);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: " + idregion);
        }
        return ResponseEntity.ok().body(lsc);
    }

    @GetMapping(value = "/signalements/typesignalement/{idTypeSignalement}")
    public ResponseEntity <List<SignalementComplet>> listeSignalementParTypeSignalement(@PathVariable("idTypeSignalement") Long idTypeSignalement) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        try {
            lsc = scr.findByIdTypesignalement(idTypeSignalement);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: " + idTypeSignalement);
        }
        return ResponseEntity.ok().body(lsc);
    }

    @GetMapping(value = "/signalements/rechercheAvance/{idtypesignalement}/{dateheure1}/{dateheure2}/{idstatut}")
    public ResponseEntity <List<SignalementComplet>> rechercheAvance(@PathVariable("idtypesignalement") Long idtypesignalement,
                                                    @PathVariable("dateheure1") String dateheure1,
                                                    @PathVariable("dateheure2") String dateheure2,
                                                    @PathVariable("idstatut") Long idstatut) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime d1 = LocalDateTime.parse(dateheure1, formatter);
        LocalDateTime d2 = LocalDateTime.parse(dateheure2, formatter);
        try {
            lsc = scr.findByIdTypesignalementAndDateheure1AndDateheure2AndIdtatut(idtypesignalement, d1, d2, idstatut);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found");
        }
        return ResponseEntity.ok().body(lsc);
    }

}
