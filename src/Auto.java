public class Auto {
	private double kilometerstand;

	private double tankinhalt;

	private final double maxTankVolumen;

	private final double verbrauchProKm;

	private double profiltiefeReifen;

	private double profiltiefeNeuerReifen = 0.8;

	private double minimalErlaubteProfiltiefe = 0.3;

	private double reifenabnutzungProKm = 0.00025;
	
	private final int fahrgestellnummer;
	
	private static int letztefgn = 0;

	private String wagenname;

	private Person fahrer;

	public Auto(double maxTankVolumen, double verbrauchProKm, String wagenname) {
		this.maxTankVolumen = maxTankVolumen;
		this.verbrauchProKm = verbrauchProKm;
		this.wagenname = wagenname;
		
		fahrgestellnummer=letztefgn+1;
		letztefgn=fahrgestellnummer;

		// gleich volltanken und frische Reifen drauf
		tanken();
		wechsleReifen();
		System.out.println(fahrgestellnummer);
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

	private double bestimmeReichweite() {
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

	private double getReifenAbnutzungProKm() {
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

	private double getBenzinverbrauchProKm() {
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

	private boolean isFahrerOk() {
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
