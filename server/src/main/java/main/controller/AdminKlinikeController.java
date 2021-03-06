package main.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.dto.AdminKlinikeDTO;
import main.dto.PromenaLozinkeDTO;
import main.dto.ZahtevZaOdmorDTO;
import main.dto.ZahtevZaPregledDTO;
import main.model.AdministratorKlinickogCentra;
import main.model.AdministratorKlinike;
import main.model.ZahtevZaOdmor;
import main.model.ZahtevZaPregled;
import main.service.AdminKlinikeService;


@CrossOrigin
@RestController
@RequestMapping(value = "/adminKlinike")
public class AdminKlinikeController {
	
	@Autowired
	private AdminKlinikeService adminKlinikeService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	
	
	
	@PostMapping(value = "/dodaj", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_CENTRA')")
	public ResponseEntity<AdminKlinikeDTO> dodajAdministratora(@RequestBody AdminKlinikeDTO administratorDTO) {
		
		AdminKlinikeDTO administratordto = new AdminKlinikeDTO();
		try {
			administratordto = adminKlinikeService.dodajAdministratora(administratorDTO);
			if (administratordto ==null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(administratordto, HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/get/{id}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> getAdmina(@PathVariable Long id) {
		
		AdministratorKlinike ak = adminKlinikeService.findOne(id);
		
		if (ak == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}

		AdminKlinikeDTO akDTO = new AdminKlinikeDTO(ak);

		return new ResponseEntity<>(akDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeni")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> izmeni(@RequestBody AdminKlinikeDTO adminKlinikeDTO){

		try {
			adminKlinikeService.izmeniAdminaKlinike(adminKlinikeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
	
	@PutMapping(value = "/izmeniLozinku")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<AdminKlinikeDTO> izmeniLozinku(@RequestBody AdminKlinikeDTO adminKlinikeDTO){
		
		try {
			adminKlinikeService.izmeniLozinku(adminKlinikeDTO);
		} catch (ValidationException e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}
	@GetMapping(value = "/izlistaj")
	@PreAuthorize("hasAuthority('ADMIN_CENTRA')")
	public ResponseEntity<List<AdminKlinikeDTO>> getIzlistaj() {
		
		List<AdministratorKlinike> listaAdmina = adminKlinikeService.findAll();
		List<AdminKlinikeDTO> adminKlinikeDTO = new ArrayList<AdminKlinikeDTO>();
		for (AdministratorKlinike k : listaAdmina) {
			adminKlinikeDTO.add(new AdminKlinikeDTO(k));
		}
		
		return new ResponseEntity<>(adminKlinikeDTO, HttpStatus.OK);
	}

	@GetMapping(value = "/izlistajZahteveZaOdmor/{idAdmina}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<ZahtevZaOdmorDTO>> getIzlistaj(@PathVariable Long idAdmina) {
		Collection<ZahtevZaOdmor> listaZahteva = new ArrayList<ZahtevZaOdmor>();
		List<ZahtevZaOdmorDTO> listaZahtevaDTO = new ArrayList<ZahtevZaOdmorDTO>();

		List<AdministratorKlinike> admini = adminKlinikeService.findAll();
		for (AdministratorKlinike administratorKlinike : admini) {
			if(administratorKlinike.getId().equals(idAdmina)) {
				listaZahteva = administratorKlinike.getZahtevZaOdmor();
			}
		}
		
		for (ZahtevZaOdmor zzo : listaZahteva) {
			if(zzo.getOdobren()==null) {
				listaZahtevaDTO.add(new ZahtevZaOdmorDTO(zzo));
			}
		}

		return new ResponseEntity<>(listaZahtevaDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/izlistajZahteveZaPregled/{idAdmina}")
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<List<ZahtevZaPregledDTO>> getIzlistajZahteveZaPregled(@PathVariable Long idAdmina) {
		Collection<ZahtevZaPregled> listaZahteva = new ArrayList<ZahtevZaPregled>();
		List<ZahtevZaPregledDTO> listaZahtevaDTO = new ArrayList<ZahtevZaPregledDTO>();

		List<AdministratorKlinike> admini = adminKlinikeService.findAll();
		for (AdministratorKlinike administratorKlinike : admini) {
			if(administratorKlinike.getId().equals(idAdmina)) {
				listaZahteva = administratorKlinike.getZahtevZaPregled();
			}
		}
		
		for (ZahtevZaPregled zzp : listaZahteva) {
			if(zzp.getSala() == null && zzp.getStatus().equals("na_cekanju")) {
				System.out.println(zzp.getVrstaPregleda());
				listaZahtevaDTO.add(new ZahtevZaPregledDTO(zzp));
			}
			
		}
		
		return new ResponseEntity<>(listaZahtevaDTO, HttpStatus.OK);
	}
	
	
	@PostMapping(value = "/promeniSvojuLozinku/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasAuthority('ADMIN_KLINIKE')")
	public ResponseEntity<?> promeniLozinku(@PathVariable Long id, @RequestBody PromenaLozinkeDTO promenaLozinkeDTO){
		
		AdministratorKlinike admin = adminKlinikeService.findOne(id);
		
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(admin.getEmail(),
						promenaLozinkeDTO.getStaraLozinka()));
		
		AdministratorKlinike adminKC = (AdministratorKlinike) authentication.getPrincipal();
		if (adminKC == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		boolean promena = adminKlinikeService.promeniLozinku(admin, promenaLozinkeDTO);
		
		if (promena == true) {
			return new ResponseEntity<>(null, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
	} 
	
	
	
	
}
