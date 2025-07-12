package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

import com.example.demo.beans.Country;
import com.example.demo.repositories.CountryRepository;
import com.example.demo.services.CountryService;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(classes= {ServiceMockitoTest.class})
public class ServiceMockitoTest {
	
@Mock
CountryRepository countryrep;

@InjectMocks
CountryService countryService;

public List<Country> mycountries;

@Test
@Order(1)
public void test_getAllCountries()
{
	List<Country> mycountries = new ArrayList<>();
	 
	mycountries.add(new Country(1,"India","Delhi"));
	mycountries.add(new Country(2,"usa","Washington"));
	mycountries.add(new Country(3,"Portugal","Lisbon"));
	
	when(countryrep.findAll()).thenReturn(mycountries); //Mocking
			//assertEquals(2,countryService.getAllCountries().size());
    assertEquals(mycountries, countryService.getAllCountries());
    
	}

@Test
@Order(6)
public void test_getCountryByID() {
	
	List<Country> mycountries = new ArrayList<>();
	 
	mycountries.add(new Country(1,"India","Delhi"));
	mycountries.add(new Country(2,"usa","Washington"));
	mycountries.add(new Country(3,"Portugal","Lisbon"));
	int countryID=1;
	
	when(countryrep.findAll()).thenReturn(mycountries); //mocking
	//assertThat(countryID).isEqualTo(countryService.getcountryById(countryID).getId());
	assertEquals(1,countryService.getcountryById(countryID));
}

@Test
@Order(2)
public void test_getCountryByName() {
	
	List<Country> mycountries = new ArrayList<>();
	 
	mycountries.add(new Country(1,"India","Delhi"));
	mycountries.add(new Country(2,"usa","Washington"));
	mycountries.add(new Country(3,"Portugal","Lisbon"));
	String countryname="India";
	
	when(countryrep.findAll()).thenReturn(mycountries); // Mocking
	//assertThat(countryname).isEqualTo(countryService.getcountryByName(countryname));          
    assertEquals(countryname, countryService.getcountryByName(countryname));
}


@Test
@Order(3)
public void test_AddCountry() {
	
	Country country = new Country(3,"Portugal","Lisbon");
	
	when(countryrep.save(country)).thenReturn(country);
	
     countryService.addCountry(country);
     
    //assertThat(country).isEqualTo(countryService.addCountry(country));
    assertEquals(country, countryService.addCountry(country));
}

@Test
@Order(5)
public void test_UpdateCountry() {
	
	Country country = new Country(3,"Portugal","Lisbon");
	
	when(countryrep.save(country)).thenReturn(country);
	
    countryService.updateCountry(country);
    
   //assertThat(country).isEqualTo(countryService.updateCountry(country));
    assertEquals(country,countryService.updateCountry(country));
	
}

@Test
@Order(6)
public void test_deleteCountry() {
	
	Country country = new Country(3,"Portugal","Lisbon");

	//countryService.deleteCountry(country);
	countryService.deleteCountry(3);
	verify(countryrep,times(1)).delete(country); //Mocking
}

} 
