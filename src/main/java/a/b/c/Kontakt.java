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

/**
 * @author Filip Dreger
 */
public class Kontakt {

	public enum TypKontaktu {
		MOBILE, WWW, EMAIL
	}

	private String zawartosc;
	private TypKontaktu typ;

	public Kontakt(String zawartosc, TypKontaktu typ) {
		this.zawartosc = zawartosc;
		this.typ = typ;
	}

	public String getZawartosc() {
		return zawartosc;
	}

	public void setZawartosc(String zawartosc) {
		this.zawartosc = zawartosc;
	}

	public TypKontaktu getTyp() {
		return typ;
	}

	public void setTyp(TypKontaktu typ) {
		this.typ = typ;
	}

	public void setTyp(String typ) {
		this.typ = TypKontaktu.valueOf(typ);
	}

	@Override
	public String toString() {
		return "Kontakt{" +
				"zawartosc='" + zawartosc + '\'' +
				", typ=" + typ +
				'}';
	}
}
