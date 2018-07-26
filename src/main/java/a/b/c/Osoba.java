/*
 * Copyright (c) 2009-2018 Ericsson AB, Sweden. All rights reserved.
 *
 * The Copyright to the computer program(s) herein is the property of Ericsson AB, Sweden.
 * The program(s) may be used  and/or copied with the written permission from Ericsson AB
 * or in accordance with the terms and conditions stipulated in the agreement/contract under
 * which the program(s) have been supplied.
 *
 */
package a.b.c;

import java.util.Arrays;
import java.util.List;

/**
 * @author Filip Dreger
 */
public class Osoba {
	private String imie;
	private String nazwisko;

	private List<Kontakt> kontakty;

	public Osoba(String imie, String nazwisko, List<Kontakt> kontakty) {
		this.imie = imie;
		this.nazwisko = nazwisko;
		this.kontakty = kontakty;
	}

	public String getImie() {
		return imie;
	}

	public void setImie(String imie) {
		this.imie = imie;
	}

	public String getNazwisko() {
		return nazwisko;
	}

	public void setNazwisko(String nazwisko) {
		this.nazwisko = nazwisko;
	}

	public List<Kontakt> getKontakty() {
		return kontakty;
	}

	public void setKontakty(List<Kontakt> kontakty) {
		this.kontakty = kontakty;
	}

	public List<Kontakt.TypKontaktu> typyKontaktu() {
		return Arrays.asList(Kontakt.TypKontaktu.values());
	}

	public void dodajKontakt() {
		kontakty.add(new Kontakt("a",Kontakt.TypKontaktu.MOBILE));
	}
}
