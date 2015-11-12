public class TausendMinutenRennen {

	public static void main(String[] args) {

		Auto[] autos = new Auto[5];

		autos[0] = new Auto(40, 0.10, "Wagen 1");
		autos[1] = new Auto(44, 0.11, "Wagen 2");
		autos[2] = new Auto(48, 0.12, "Wagen 3");
		autos[3] = new Auto(52, 0.13, "Wagen 4");
		autos[4] = new Auto(56, 0.14, "Wagen 5");

		autos[0].setFahrer(new Person("Homer Simpson", 1960));
		autos[1].setFahrer(new Person("Marge Simpson", 1960));
		autos[2].setFahrer(new Person("Abe Simpson", 1914));
		autos[3].setFahrer(new Person("Otto", 1988));

		for (int i = 0; i < 1000; i++) {

			System.out.println();
			System.out.println("************************************************");
			System.out.println("Rennminute " + i);
			System.out.println();

			// Alle 10 min wechselt der Fahrer
			if (i % 10 == 0) {
				wechlseFahrer(autos);
			}

			// ansonsten wird gefahren oder getankt oder die reifen
			// gewechselt...
			for (Auto each : autos) {
				nutzeDieMinute(each);
				each.gebeDatenAus();
			}

		}

	}

	public static void wechlseFahrer(Auto[] autos) {
		// Zuerst mal klaeren: Was ist das leere Auto und welche sind besetzt?
		Auto leeresAuto = null;
		Auto[] besetzteAutos = new Auto[autos.length - 1];

		int indexBesetzteAutos = 0;
		for (Auto each : autos) {
			if (each.getFahrer() == null) {
				// Wir haben das leere Auto gefunden...
				leeresAuto = each;
			} else {
				// Ein nicht leeres Auto... merken...
				besetzteAutos[indexBesetzteAutos] = each;
				indexBesetzteAutos++;
			}
		}

		if (leeresAuto == null) {
			// das passiert nur, wenn es gar kein leeres Auto gibt...
			return;
		}

		// Nun zufaellig ein besetztes waehlen
		int z = Zufall.getZufallInt(0, besetzteAutos.length - 1);
		Auto besetztesAuto = besetzteAutos[z];

		// Und den Fahrer wechseln in das leere
		leeresAuto.setFahrer(besetztesAuto.getFahrer());

		// Und das besetzte Auto leer machen
		besetztesAuto.setFahrer(null);

	}

	public static void nutzeDieMinute(Auto auto) {
		// Wir entscheiden zufaellig, was in dieser Minute passiert
		int zufall = Zufall.getZufallInt(1, 100);
		switch (zufall) {
		case 1:
			System.out.println(auto.getWagenname() + " tankt!");
			auto.tanken();
			break;
		case 2:
			System.out.println(auto.getWagenname() + " wechselt die Reifen!");
			auto.wechsleReifen();
			break;
		default:
			double strecke = Zufall.getZufallDouble(3, 4);
			auto.fahre(strecke);
		}
	}
}
