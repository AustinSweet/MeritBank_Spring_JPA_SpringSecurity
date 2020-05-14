package com.meritamerica.assignment6.models;

public class MeritAmericaBankApp {
	public static void main(String[] args) {
		MeritBank.readFromFile("src/test/testMeritBank_good.txt");
		MeritBank.writeToFile("src/test/testMeritBank_Write.txt");		
	}
}