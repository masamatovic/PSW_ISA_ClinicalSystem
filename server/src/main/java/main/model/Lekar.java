package main.model;

/***********************************************************************
 * Module:  Lekar.java
 * Author:  23nik
 * Purpose: Defines the Class Lekar
 ***********************************************************************/

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Lekar {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ime", nullable = false)
   private String ime;
	
	@Column(name = "prezime", nullable = false)
   private String prezime;
	
	@Column(name = "email", nullable = false)
   private String email;
	
	@Column(name = "lozinka", nullable = false)
   private String lozinka;
	
	@Column(name = "adresa", nullable = false)
   private String adresa;
	
	@Column(name = "grad", nullable = false)
   private String grad;
	
	@Column(name = "drzava", nullable = false)
   private String drzava;
	
	@Column(name = "telefon", nullable = false)
   private String telefon;
	
	@Column(name = "jmpg", nullable = false)
   private String jmbg;
	
	@Column(name = "ocena", nullable = false)
   private int ocena;
	
	@Column(name = "brojRecenzija", nullable = false)
   private int brojRecenzija;
   
	 @OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
   public java.util.Collection<Izvestaj> izvestaj;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "klinika_id", referencedColumnName = "id")
	 public Klinika klinika;
	 
	 @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "radniKalendar_id", referencedColumnName = "id")
	  public RadniKalendar radniKalendar;
	 
	 @OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	 public java.util.Collection<Pregled> pregled;
	 
	 @OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
	 public java.util.Collection<Operacija> operacija;
	 
	 @OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
     public java.util.Collection<ZahtevZaOdmor> zahtevZaOdmor;
	 
	 @OneToMany(mappedBy = "lekar", fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
     public java.util.Collection<Recept> recept;

public Lekar() {
	super();
	// TODO Auto-generated constructor stub
}
public Lekar(String ime, String prezime, String email, String lozinka, String adresa, String grad, String drzava,
		String telefon, String jmbg, int ocena, int brojRecenzija, Collection<Izvestaj> izvestaj) {
	super();
	this.ime = ime;
	this.prezime = prezime;
	this.email = email;
	this.lozinka = lozinka;
	this.adresa = adresa;
	this.grad = grad;
	this.drzava = drzava;
	this.telefon = telefon;
	this.jmbg = jmbg;
	this.ocena = ocena;
	this.brojRecenzija = brojRecenzija;
	this.izvestaj = izvestaj;
}
public Long getId() {
	return id;
}
public void setId(Long id) {
	this.id = id;
}
public String getIme() {
	return ime;
}
public void setIme(String ime) {
	this.ime = ime;
}
public String getPrezime() {
	return prezime;
}
public void setPrezime(String prezime) {
	this.prezime = prezime;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getLozinka() {
	return lozinka;
}
public void setLozinka(String lozinka) {
	this.lozinka = lozinka;
}
public String getAdresa() {
	return adresa;
}
public void setAdresa(String adresa) {
	this.adresa = adresa;
}
public String getGrad() {
	return grad;
}
public void setGrad(String grad) {
	this.grad = grad;
}
public String getDrzava() {
	return drzava;
}
public void setDrzava(String drzava) {
	this.drzava = drzava;
}
public String getTelefon() {
	return telefon;
}
public void setTelefon(String telefon) {
	this.telefon = telefon;
}
public String getJmbg() {
	return jmbg;
}
public void setJmbg(String jmbg) {
	this.jmbg = jmbg;
}
public int getOcena() {
	return ocena;
}
public void setOcena(int ocena) {
	this.ocena = ocena;
}
public int getBrojRecenzija() {
	return brojRecenzija;
}
public void setBrojRecenzija(int brojRecenzija) {
	this.brojRecenzija = brojRecenzija;
}
public java.util.Collection<Izvestaj> getIzvestaj() {
	return izvestaj;
}
public void setIzvestaj(java.util.Collection<Izvestaj> izvestaj) {
	this.izvestaj = izvestaj;
}
public Klinika getKlinika() {
	return klinika;
}
public void setKlinika(Klinika klinika) {
	this.klinika = klinika;
}
public RadniKalendar getRadniKalendar() {
	return radniKalendar;
}
public void setRadniKalendar(RadniKalendar radniKalendar) {
	this.radniKalendar = radniKalendar;
}
public java.util.Collection<Pregled> getPregled() {
	return pregled;
}
public void setPregled(java.util.Collection<Pregled> pregled) {
	this.pregled = pregled;
}
public java.util.Collection<Operacija> getOperacija() {
	return operacija;
}
public void setOperacija(java.util.Collection<Operacija> operacija) {
	this.operacija = operacija;
}
public java.util.Collection<ZahtevZaOdmor> getZahtevZaOdmor() {
	return zahtevZaOdmor;
}
public void setZahtevZaOdmor(java.util.Collection<ZahtevZaOdmor> zahtevZaOdmor) {
	this.zahtevZaOdmor = zahtevZaOdmor;
}
public java.util.Collection<Recept> getRecept() {
	return recept;
}
public void setRecept(java.util.Collection<Recept> recept) {
	this.recept = recept;
}
@Override
public String toString() {
	return "Lekar [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", email=" + email + ", lozinka=" + lozinka
			+ ", adresa=" + adresa + ", grad=" + grad + ", drzava=" + drzava + ", telefon=" + telefon + ", jmbg=" + jmbg
			+ ", ocena=" + ocena + ", brojRecenzija=" + brojRecenzija + ", izvestaj=" + izvestaj + ", klinika="
			+ klinika + ", radniKalendar=" + radniKalendar + ", pregled=" + pregled + ", operacija=" + operacija
			+ ", zahtevZaOdmor=" + zahtevZaOdmor + ", recept=" + recept + "]";
}
   
    
  

}