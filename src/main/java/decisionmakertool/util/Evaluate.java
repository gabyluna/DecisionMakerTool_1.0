/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package decisionmakertool.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import ionelvirgilpop.drontoapi.service.WebService;
import ionelvirgilpop.drontoapi.util.PitfallSelector;
import ionelvirgilpop.drontoapi.service.IWebService;

import ionelvirgilpop.drontoapi.except.UnexpectedErrorException;
import ionelvirgilpop.drontoapi.filter.Criteria;
import ionelvirgilpop.drontoapi.filter.Filter;
import ionelvirgilpop.drontoapi.filter.IFilter;
import ionelvirgilpop.drontoapi.pitfallmanager.IPitfallManager;
import ionelvirgilpop.drontoapi.pitfallmanager.Pitfall;
import ionelvirgilpop.drontoapi.pitfallmanager.PitfallManager;
import ionelvirgilpop.drontoapi.pitfallmanager.Report;
import ionelvirgilpop.drontoapi.ver.Version;

public class Evaluate {

    public Evaluate() {

    }

    public static double polygonArea(double X[], double Y[],
            int n) {
        // Initialze area 
        double area = 0.0;

        // Calculate value of shoelace formula 
        int j = n - 1;
        for (int i = 0; i < n; i++) {
            area += (X[j] + X[i]) * (Y[j] - Y[i]);

            // j is previous vertex to i 
            j = i;
        }

        // Return absolute value 
        return Math.abs(area / 2.0);
    }

    public static void coordenadasXY(int dimension[], int n) {
        double X[] = new double[n];
        double Y[] = new double[n];
        double angulo = 0;
        double intervalo = 0;
        intervalo = 360 / n;
        angulo = intervalo;

        //Coordenates X
        for (int i = 0; i < n; i++) {
            X[i] = dimension[i] * Math.cos(Math.toRadians(angulo));
            Y[i] = dimension[i] * Math.sin(Math.toRadians(angulo));
            Y[i] = format(Y[i]);
            angulo = angulo + intervalo;
        }

        System.out.println("area:" + polygonArea(X, Y, n));
    }

    private static double format(double value) {
        return (double) Math.round(value * 1000000) / 1000000; //you can change this to round up the value(for two position use 100...)
    }

    public void evaluate() {
        //Create a client that communicates with the OOPS! web service provider.
        IWebService webService = new WebService();
        try {
            //Set the ontology that will be evaluated.
            //webService.setOntologyIRI("http://purl.org/goodrelations/v1");
            webService.setOntologyFile("c:/tmp/ontoFinal.owl");
            //Select only some pitfalls
            PitfallSelector pitfallSelector = new PitfallSelector();
            //Select pitfalls based on a filter rather than directly
            IFilter filter = new Filter();
            filter.filterPitfalls(Criteria.CORRECTNESS);//filter pitfalls by correctness
            filter.filterPitfalls(Criteria.COMPLETENESS);//also filter by completeness
            pitfallSelector.selectPitfalls(filter);//add filtered pitfalls to the selection
            //tell the web service which pitfalls to check based on a PitfallSelector
            webService.setPitfallSelector(pitfallSelector);
            //View selected pitfalls
            System.out.println(pitfallSelector.toString());
            //Create a model of the response to manage it
            IPitfallManager manager = new PitfallManager(webService.getResponse());
            //Generate a CSV report of the evaluation result
            manager.generateReport(new FileOutputStream(new File("c:/tmp/csv_report.csv")), Report.CSV_REPORT);
        } catch (IOException | UnexpectedErrorException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        /*Evaluate ex01 = new Evaluate();
		ex01.evaluate();*/
        //int dimensiones[] = {1,5,5,2,3,3,1,1,1};
        //int dimensiones[] = {1,1,1,3,3,2,5,5,1};
        int dimensiones[] = {1, 5, 5, 5, 1, 1, 1, 5, 5};
        int n = 9;
        coordenadasXY(dimensiones, n);

        double X[] = {0.76, 0.17, -0.5, -2.81, -2.81, -1, 0.86, 3.83, 1};
        double Y[] = {0, 64, 0.98, 0.86, 1.02, -1.02, -1.73, -4.92, -3.21, 0};

        //System.out.println(polygonArea(X, Y, n)); 
    }
}
