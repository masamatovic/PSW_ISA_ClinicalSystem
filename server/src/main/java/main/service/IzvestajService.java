package main.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import main.dto.IzvestajDTO;
import main.model.Izvestaj;
import main.model.Lek;
import main.model.Pacijent;
import main.repository.DijagnozaRepository;
import main.repository.IzvestajRepository;
import main.repository.LekRepository;
import main.repository.LekarRepository;
import main.repository.PacijentRepository;

@Service

public class IzvestajService {
	@Autowired
	private IzvestajRepository izvestajRepository;
	@Autowired
	private LekRepository lr;
	@Autowired
	private DijagnozaRepository dr;
	@Autowired
	private PacijentRepository pr;
	@Autowired
	private LekarRepository lekarr;
	
	public IzvestajDTO dodajIzvestaj(IzvestajDTO izvestajDTO) {
		// TODO Auto-generated method stub
		Izvestaj izvestaj = new Izvestaj();
		
		izvestaj.setOpis(izvestajDTO.getOpis());
		izvestaj.setLekovi(new ArrayList<Lek>());
		for (String lek : izvestajDTO.getLekovi()) {
			izvestaj.getLekovi().add(lr.findBySifra(lek));
		}
		izvestaj.setDijagnoza(dr.findBySifra(izvestajDTO.getDijagnoza()));
		izvestaj.setPacijent(pr.getOne(izvestajDTO.getIdPacijenta()));
		izvestaj.setLekar(lekarr.getOne(izvestajDTO.getIdLekara()));
		izvestaj.setOverenRecept(false);
		Pacijent pacijent = new Pacijent();
		pacijent = pr.getOne(izvestajDTO.getIdPacijenta());
		izvestajRepository.save(izvestaj);
		pacijent.izvestaj.add(izvestaj);

		IzvestajDTO izvestajdto=new IzvestajDTO(izvestaj);
		return izvestajdto;	
	}

	public List<Izvestaj> findAll() {
		// TODO Auto-generated method stub
		return izvestajRepository.findAll();
	}

	public Izvestaj findOne(Long id) {
		// TODO Auto-generated method stub
		return izvestajRepository.getOne(id);
	}
}
