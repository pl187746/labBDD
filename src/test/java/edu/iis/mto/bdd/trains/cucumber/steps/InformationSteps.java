package edu.iis.mto.bdd.trains.cucumber.steps;

import org.joda.time.LocalTime;

import cucumber.api.Transform;
import cucumber.api.java.Before;
import cucumber.api.java.pl.Gdy;
import cucumber.api.java.pl.Wtedy;
import cucumber.api.java.pl.Zakładając;
import edu.iis.mto.bdd.trains.services.InMemoryTimetableService;
import edu.iis.mto.bdd.trains.services.TimetableService;

public class InformationSteps {
	
	private TimetableService timetableService;
	private String start;
	private String cel;
	private String linia;
	private LocalTime czasPrzyjazdu;
	
	@Before
	public void setUp() {
		timetableService = new InMemoryTimetableService();
	}
	
	@Zakładając("^chcę się dostać z (.*) do (.*)$")
	public void chceSieDostacZDo(String start, String cel) {
		this.start = start;
		this.cel = cel;
	}
	
	@Zakładając("^następny pociąg odjeżdża o (.*) na linii (.*)$")
	public void nastepnyPociagOdjezdzaONaLinii(@Transform(JodaLocalTimeConverter.class) LocalTime czasOdjazdu, String linia) {
		timetableService.scheduleArrivalTime(linia, czasOdjazdu);
		this.linia = linia;
	}
	
	@Gdy("^zapytam o godzinę przyjazdu$")
	public void zapytamOGodzinePrzyjazdu() {
		czasPrzyjazdu = timetableService.getArrivalTime(linia, cel);
	}
	
	@Wtedy("^powinienem uzyskać następujący szacowany czas przyjazdu: (.*)$")
	public void powinienemUzyskacSzacowanyCzasPrzyjazdu(@Transform(JodaLocalTimeConverter.class) LocalTime czasPrzyjazdu) {
		this.czasPrzyjazdu.equals(czasPrzyjazdu);
	}

}
