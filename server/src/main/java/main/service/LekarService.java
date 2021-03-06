package main.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import main.dto.LekarDTO;
import main.dto.PromenaLozinkeDTO;
import main.model.AdministratorKlinickogCentra;
import main.model.Authority;
import main.model.Lekar;
import main.model.Pacijent;
import main.model.Lekar;
import main.repository.AuthorityRepository;
import main.repository.KlinikaRepository;
import main.repository.LekarRepository;
import main.repository.TipPregledaRepository;

@Service
public class LekarService {
	
	@Autowired
	private LekarRepository lekarRepository;

	@Autowired
	private KlinikaRepository klinikaRepository;
	
	@Autowired
	private TipPregledaRepository tipPregledaRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public Lekar findOne(Long id) {
		return lekarRepository.findById(id).orElseGet(null);
	}
	
	public Lekar findOne(String mail) {
		return lekarRepository.findByEmail(mail);
	}
	
	
	public Lekar izmeniLekara(Lekar lekar, LekarDTO lekarDTO) {
		  
		lekar.setIme(lekarDTO.getIme());
		lekar.setPrezime(lekarDTO.getPrezime());
		lekar.setAdresa(lekarDTO.getAdresa());
		lekar.setTelefon(lekarDTO.getTelefon());
		lekar.setGrad(lekarDTO.getGrad());
		lekar.setDrzava(lekarDTO.getDrzava());
		lekarRepository.save(lekar);
		
		return lekar;
}

	public Lekar izmeniLozinku(Lekar lekar, LekarDTO lekarDTO) {
		lekar.setPromenjenaLozinka(true);
		lekar.setLozinka(passwordEncoder.encode(lekarDTO.getLozinka()));
		lekarRepository.save(lekar);
		
		return lekar;
}
	public LekarDTO dodajLekara(LekarDTO lekarDTO) {
		// TODO Auto-generated method stub
		Lekar l = new Lekar();
		
		Authority auth = this.authorityRepository.findByUloga("LEKAR");
		List<Authority> auths = new ArrayList<>();
	    auths.add(auth);
	    l.setAuthorities(auths);
	
		l.setIme(lekarDTO.getIme());
		l.setPrezime(lekarDTO.getPrezime());
		l.setAdresa(lekarDTO.getAdresa());
		l.setGrad(lekarDTO.getGrad());
		l.setDrzava(lekarDTO.getDrzava());
		l.setTelefon(lekarDTO.getTelefon());
		l.setEmail(lekarDTO.getEmail());
		l.setJmbg(lekarDTO.getJmbg());
		l.setLozinka(passwordEncoder.encode(lekarDTO.getLozinka()));
		l.setPocetak(lekarDTO.getPocetak());
		l.setKraj(lekarDTO.getKraj());
		l.setKlinika(klinikaRepository.getOne(lekarDTO.getIdKlinike()));
		System.out.println(lekarDTO.getIdTipaPregleda());
		l.setTipPregleda(tipPregledaRepository.getOne(lekarDTO.getIdTipaPregleda()));
		l.setPromenjenaLozinka(false);
		lekarDTO.setPromenjenaLozinka(false);
		//klinikaRepository.getOne(lekarDTO.getIdKlinike()).lekar.add(l);
		for (Lekar k : lekarRepository.findAll()) {
			if (l.getEmail().equals(k.getEmail())) {
				return null;
			}
		}
		lekarRepository.save(l);
		//SendEmailTLS.main(lekarDTO.getEmail());
		LekarDTO lekardto =new LekarDTO(l);
		return lekardto;
	}

	public List<Lekar> findAll() {
		return lekarRepository.findAll();
	}

	public void remove(Long id) {
		// TODO Auto-generated method stub
		lekarRepository.deleteById(id);
	}
	
public List<Lekar> pronadjiLekara( String ime, String prezime){
		
		//lista pregleda koju vracam
		List<Lekar> ret = new ArrayList<Lekar>();
		
		List<Lekar> sviLekari = lekarRepository.findAll();
		
	
		if(ime == null && prezime == null ) {
			ret = sviLekari;
		}else if(ime != null && prezime == null){
			for(Lekar p : sviLekari) {
				if(p.getIme().contains(ime)) {
					ret.add(p);
				}
			}
		}else if(ime == null && prezime != null){
			for(Lekar p : sviLekari) {
				if(p.getPrezime().contains(prezime)) {
					ret.add(p);
				}
			}
		}else if(ime != null && prezime != null){
			for(Lekar p : sviLekari) {
				if(p.getIme().contains(ime) && p.getPrezime().contains(prezime)) {
					ret.add(p);
				}
			}
		}
		
	
		return ret;
		}
	

public List<Lekar> pronadjiLekare( String ime, String prezime, Double ocena){
		
		//lista lekara koju vracam
		List<Lekar> ret = new ArrayList<Lekar>();
		
		List<Lekar> sviLekari = lekarRepository.findAll();
		
	
		if(ime == null && prezime == null && ocena == null) {
			ret = sviLekari;
		}else if(ime == null && prezime == null && ocena != null){
			for(Lekar p : sviLekari) {
				if(p.getOcena() == ocena) {
					ret.add(p);
				}
			}
		}else if(ime == null && prezime != null && ocena == null){
			for(Lekar p : sviLekari) {
				if(p.getPrezime().contains(prezime)) {
					ret.add(p);
				}
			}
		}else if(ime != null && prezime == null && ocena == null){
			for(Lekar p : sviLekari) {
				if(p.getIme().contains(ime)) {
					ret.add(p);
				}
			}
		}else if(ime != null && prezime != null && ocena == null){
			for(Lekar p : sviLekari) {
				if(p.getIme().contains(ime) && p.getPrezime().contains(prezime)) {
					ret.add(p);
				}
			}
		}else if(ime != null && prezime == null && ocena != null){
			for(Lekar p : sviLekari) {
				if(p.getIme().contains(ime) && p.getOcena()==ocena) {
					ret.add(p);
				}
			}
		}else if(ime == null && prezime != null && ocena != null){
			for(Lekar p : sviLekari) {
				if(p.getPrezime().equals(prezime) && p.getOcena()==ocena) {
					ret.add(p);
				}
			}
		}else if(ime != null && prezime != null && ocena != null){
			for(Lekar p : sviLekari) {
				if(p.getPrezime().contains(prezime) && p.getOcena()==ocena  && p.getIme().contains(ime)) {
					ret.add(p);
				}
			}
		}
		
	
		return ret;
		}
	

		public boolean promeniLozinku(Lekar admin, PromenaLozinkeDTO lozinka) {
		
			if (lozinka.getNovaLozinka().equals(lozinka.getPonovljenaLozinka())) {
				admin.setLozinka(passwordEncoder.encode(lozinka.getNovaLozinka()));
				lekarRepository.save(admin);
				return true;
			}
			return false;
		}

}
