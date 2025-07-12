package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.beans.Country;
import com.example.demo.controllers.Country_controllers;
import com.example.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {
	
	@Mock
	CountryService countryservice;
	
	@InjectMocks
	Country_controllers cc;
	
	List<Country> mycountries;
	Country country;
	
@Test
@Order(1)
public void test_getAllCountries() {
	
	mycountries = new ArrayList<Country>();
	mycountries.add(new Country(1,"India","Delhi"));
	mycountries.add(new Country(2,"Portugal","Lisbon"));
	
	 when(countryservice.getAllCountries()).thenReturn(mycountries); //Mocking
	 ResponseEntity<List<Country>> res = cc.getCountries();
     assertEquals(HttpStatus.FOUND,res.getStatusCode());
     assertEquals(2,res.getBody().size());
}

@Test
@Order(2)
public void test_getCountryByID() {
	
	country = new Country(2,"England","London");
	int countryid = 2;
	
	when(countryservice.getcountryById(countryid)).thenReturn(country); //Mocking
	 ResponseEntity<Country> res = cc.getCountryByID(countryid);
	 
	 assertEquals(HttpStatus.OK,res.getStatusCode());
	 assertEquals(countryid, res.getBody().getId());
	 
}

@Test
@Order(3)
public void test_getCountryByName() {
	country =  new Country(1,"Chalapathi","Nemo");
	String countryName = "Chalapathi";
	
	when(countryservice.getcountryByName(countryName)).thenReturn(country);
	ResponseEntity<Country> res = cc.getCountryByName(countryName);
	
	assertEquals(HttpStatus.FOUND, res.getStatusCode());
	assertEquals(countryName,res.getBody().getCountryName());	
}




}
