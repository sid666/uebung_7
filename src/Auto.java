public class Auto {
	public double kilometerstand;

	public double tankinhalt;

	public double maxTankVolumen;

	public double verbrauchProKm;

	public double profiltiefeReifen;

	public double profiltiefeNeuerReifen = 0.8;

	public double minimalErlaubteProfiltiefe = 0.3;

	public double reifenabnutzungProKm = 0.00025;

	public String wagenname;

	public Person fahrer;

	public Auto(double maxTankVolumen, double verbrauchProKm, String wagenname) {
		this.maxTankVolumen = maxTankVolumen;
		this.verbrauchProKm = verbrauchProKm;
		this.wagenname = wagenname;

		// gleich volltanken und frische Reifen drauf
		tanken();
		wechsleReifen();
	}

	public Person getFahrer() {
		return fahrer;
	}

	public void setFahrer(Person fahrer) {
		this.fahrer = fahrer;
	}

	public void fahre(double km) {
		// Ist ein Fahrer gesetzt und ist er alt genug?
		if (!isFahrerOk()) {
			return;
		}

		// Erstmal nachsehen, wie weit wir grundsaetzlich kommen wuerden
		double reichweite = bestimmeReichweite();

		// Will jemand weiter fahren als die Reichweite betraegt?
		// Wenn ja, dann muessen wir das beschraenken
		double wirklichGefahreneKm;
		if (km > reichweite) {
			wirklichGefahreneKm = reichweite;
		} else {
			wirklichGefahreneKm = km;
		}

		// Und nun kommt das eigentliche fahren
		kilometerstand = kilometerstand + wirklichGefahreneKm;
		tankinhalt = tankinhalt - (getBenzinverbrauchProKm() * wirklichGefahreneKm);
		profiltiefeReifen = profiltiefeReifen - (getReifenAbnutzungProKm() * wirklichGefahreneKm);

	}

	public double bestimmeReichweite() {
		// Reichweite des Tanks bestimmen
		double reichweiteTank = tankinhalt / getBenzinverbrauchProKm();

		// Reichweite der Reifen bestimmen
		double verfahrbaresRestprofil = profiltiefeReifen - minimalErlaubteProfiltiefe;
		double reichweiteReifen = verfahrbaresRestprofil / getReifenAbnutzungProKm();

		// Jetzt pruefen, was mehr einschraenkt. Die Reifen oder Tank?
		if (reichweiteReifen < reichweiteTank) {
			return reichweiteReifen;
		} else {
			return reichweiteTank;
		}
	}

	public double getReifenAbnutzungProKm() {
		// Diese Methode bestimmt, wie stark die Reifenabnutzung pro Km
		// waere, wenn das Auto jetzt fahren wuerde
		if (!isFahrerOk()) {
			return 0;
		}
		if (fahrer.getAlter() <= 20) {
			return reifenabnutzungProKm * 1.05;
		}
		if (fahrer.getAlter() >= 60) {
			return reifenabnutzungProKm * 0.95;
		}
		return reifenabnutzungProKm;
	}

	public double getBenzinverbrauchProKm() {
		// Diese Methode bestimmt, wie stark der Benzinverbrauch pro Km
		// waere, wenn das Auto jetzt fahren wuerde
		if (!isFahrerOk()) {
			return 0;
		}
		if (fahrer.getAlter() <= 20) {
			return verbrauchProKm * 1.10;
		}
		if (fahrer.getAlter() >= 60) {
			return verbrauchProKm * 0.90;
		}
		return verbrauchProKm;
	}

	public boolean isFahrerOk() {
		// Ohne Fahrer laeuft hier gar nichts
		if (fahrer == null) {
			return false;
		}
		// Juenger als 18? Dann passiert auch nix
		if (fahrer.getAlter() < 18) {
			return false;
		}
		// Sonst ist alles ok
		return true;
	}

	public void gebeDatenAus() {
		String fahrername = "Kein Fahrer";
		if (fahrer != null) {
			fahrername = fahrer.getName();
		}
		System.out.println(wagenname + ": " + fahrername + " am Steuer, " + kilometerstand + " bisher gefahren. Tankinhalt "
				+ tankinhalt + " liter und " + profiltiefeReifen + " mm Reifenprofil uebrig.");
	}

	public void tanken() {
		// Einfach Tank voll machen
		tankinhalt = maxTankVolumen;
	}

	public void wechsleReifen() {
		// Reifenprofiltiefe wieder neu
		profiltiefeReifen = profiltiefeNeuerReifen;

	}

	public void tanken(double liter) {
		// Zuviel getankt?
		if (liter + tankinhalt > maxTankVolumen) {
			// Ja: Dann einfach nur Tank voll machen
			tanken();
		} else {
			// Nein: Tank um die Tankmenge fuellen
			this.tankinhalt = liter + tankinhalt;
		}
	}

	public String getWagenname() {
		return wagenname;
	}

}
