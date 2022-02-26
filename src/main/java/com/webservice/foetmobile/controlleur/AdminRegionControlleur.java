package com.webservice.foetmobile.controlleur;

import com.webservice.foetmobile.exception.ResourceNotFoundException;
import com.webservice.foetmobile.modele.*;
import com.webservice.foetmobile.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/api/adminregion")
public class AdminRegionControlleur {

    public AdminRegionControlleur(AdminRegionRepository arr,SignalementCompletRepository scr,TokenAdminRegionRepository tarr,TypeSignalementRepository tsr,StatutRepository sr) {
        this.arr = arr;
        this.scr = scr;
        this.tarr = tarr;
        this.tsr = tsr;
        this.sr = sr;
    }

    @Autowired
    AdminRegionRepository arr;

    @Autowired
    SignalementCompletRepository scr;

    @Autowired
    TokenAdminRegionRepository tarr;

    @Autowired
    TypeSignalementRepository tsr;

    @Autowired
    StatutRepository sr;

    private Boolean checkToken(TokenAdminRegion tar) {
        if(tar.getDateexpiration().isAfter(LocalDateTime.now()) == true) {
            return true;
        }
        return false;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<Map<String, String>> loginAdminRegion(@RequestBody AdminRegion c) {
        Map<String, String> map = new HashMap<>();
        String token = "";
        List<AdminRegion> la = arr.findByEmailAndMdp(c.getEmail(), c.getMdp());
        if (la.size() == 1) {
            token = arr.generateToken(la.get(0));
            Date input = new Date();
            LocalDateTime localDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime plusMinutes = localDate.plusMinutes(15);
            map.put("htt", HttpStatus.OK.toString());
            tarr.insertToken(la.get(0).getId(), token, plusMinutes);
            map.put("idregion",la.get(0).getIdregion().toString());
            map.put("Token", token);
        } else {
            map.put("Erreur", "Verifier vos informations");
        }
        return ResponseEntity.ok(map);
    }

    @GetMapping(value = "/signalements")
    public ResponseEntity<List<SignalementComplet>> listeSignalementParRegion(HttpServletRequest request) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        AdminRegion ar = new AdminRegion();
        String token = (String) request.getHeader("Authorization");
        System.out.println(token);
        TokenAdminRegion tar = tarr.findByToken(token.substring(7));
        System.out.println(checkToken(tar));
        if(checkToken(tar) == true) {
            ar = arr.findAdminRegion(tar.getIdadminaegion());
            lsc = scr.findByIdregion(ar.getIdregion());
        } else {
            throw new ResourceNotFoundException("Token expire, veuillez vous reconnecter");
        }

        return ResponseEntity.ok().body(lsc);
    }

    @GetMapping(value = "/parTypeSignalement/signalements")
    public ResponseEntity<List<SignalementComplet>> listeSignalementParTypeSignalement(@RequestParam("idtypesignalement") Long idTypeSignalement) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        try {
            lsc = scr.findByIdTypesignalement(idTypeSignalement);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found for this id :: " + idTypeSignalement);
        }
        return ResponseEntity.ok().body(lsc);
    }
    @GetMapping(value = "/signalement/{id}")
    public SignalementComplet signalementPartiel(@PathVariable("id") Long id) throws Exception {
        SignalementComplet sin =new SignalementComplet();
        try{
            sin=scr.findById(id).get();
        }catch(Exception ex){
            throw ex;
        }
        return sin;
    }
    @GetMapping(value = "/typeSignalements")
    public ResponseEntity<List<TypeSignalement>> allTypeSignalemet(HttpServletRequest request){
        List<TypeSignalement> retour =new ArrayList<TypeSignalement>();
        try{
            retour=tsr.findAll();

        }catch (Exception ex){
            throw ex;
        }
         return ResponseEntity.ok().body(retour);
    }
    @GetMapping(value = "/status")
    public ResponseEntity<List<Status>> allStatus(HttpServletRequest request){
        List<Status> retour =new ArrayList<Status>();
        try{
            retour=sr.findAll();

        }catch (Exception ex){
            throw ex;
        }
        return ResponseEntity.ok().body(retour);
    }
    @GetMapping(value = "/recherche/signalements/{typesignalement}/{statut}/{idregion}/{dateheure1}/{dateheure2}")
    public ResponseEntity<List<SignalementComplet>> rechercheAvance(@PathVariable("typesignalement") String typesignalement,
                                                                    @PathVariable("dateheure1") String dateheure1,
                                                                    @PathVariable("dateheure2") String dateheure2,
                                                                    @PathVariable("statut") String statut,
                                                                    @PathVariable("idregion") Long idregion) throws ResourceNotFoundException {
        List<SignalementComplet> lsc = new ArrayList<SignalementComplet>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("yyyy-MM-dd HH:mm:ss"));
        LocalDateTime d1 = LocalDateTime.parse(dateheure1, formatter);
        LocalDateTime d2 = LocalDateTime.parse(dateheure2, formatter);
        try {
            lsc = scr.findByIdTypesignalementAndDateheure1AndDateheure2AndIdstatut(typesignalement, d1, d2, statut,idregion);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Signalements not found");
        }
        return ResponseEntity.ok().body(lsc);
    }
}
