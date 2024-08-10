package com.example.ctms;

import eu.europa.ec.eurostat.jgiscotools.feature.Feature;
import eu.europa.ec.eurostat.searoute.GeoDistanceUtil;
import eu.europa.ec.eurostat.searoute.SeaRouting;
import org.locationtech.jts.geom.MultiLineString;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CtmsApplication {


	public static void main(String[] args) {
		SpringApplication.run(CtmsApplication.class, args);



	}


}
