package com.example.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.demo.beans.Country;
import com.example.demo.controllers.Country_controllers;
import com.example.demo.services.CountryService;
import com.fasterxml.jackson.databind.ObjectMapper;

@TestMethodOrder(OrderAnnotation.class)
@ComponentScan(basePackages="com.example.demo")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes= {Country_controllers.class})
public class ControllerMockMvcTest {

	@Autowired
	MockMvc mockMvc;
	
	@Mock
	CountryService countryservice;
	
	@InjectMocks
	Country_controllers cc;
	
	List<Country> mycountries;
	Country country;
	
	
	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(cc).build();
	}
	
	@Test
	@Order(1)
	public void test_getAllCountries() throws Exception 
	{
		mycountries = new ArrayList<Country>();
		mycountries.add(new Country(1,"India","Delhi"));
		mycountries.add(new Country(2,"France","paris"));
		
	    when(countryservice.getAllCountries()).thenReturn(mycountries);//Mocking
	    
	    this.mockMvc.perform(get("/getcountries"))
	        .andExpect(status().isFound())
	        .andDo(print());	
	}
	
	@Test
	@Order(2)
	public void test_getCountryByID() throws Exception 
	{
		country = new Country(1,"India","Delhi");
		int mycountryId = 1;
		
		when(countryservice.getcountryById(mycountryId)).thenReturn(country);
		this.mockMvc.perform(get("/getcountries/{id}",mycountryId))
		    .andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath(".id").value(1))
		    .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("India"))
		    .andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Delhi"))
		    .andDo(print());
	}
	
	@Test
	@Order(3)
	public void test_getCountryByName() throws Exception 
	{
		country = new Country(2,"France","Paris");
		String countryname = "France";
		
		when(countryservice.getcountryByName(countryname)).thenReturn(country);
		this.mockMvc.perform(get("/getcountries/countryname").param("name", "France"))
		    .andExpect(status().isOk())
		    .andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("France"))
		    .andDo(print());
	}
	
	@Test
	@Order(4)
	public void test_addCountry() throws Exception {
		country = new Country(3, "Portugal", "Lisbon");
		
		when(countryservice.addCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper(); //to convert java object to json because mockmvc will accept only json objects not java objects
		String jsonbody = mapper.writeValueAsString(country); // [mapper.writeValueAsString(country);] this statement will return data in string format. [NOTE: json is also comes under string object only] internally it is json only (like string is a parent of json datatype)
		
		this.mockMvc.perform(post("/addcountry")
				    .content(jsonbody)
				    .contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		//.andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
		//.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Portugal"))
		//.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Lisbon"))
		.andDo(print());
	}
	
	@Test
	@Order(5)
	public void test_updateCountry() throws Exception {
		country = new Country(4, "Spain", "Madrid");
		int countryid = 4;
		
		when(countryservice.getcountryById(countryid)).thenReturn(country); // mocling
		when(countryservice.updateCountry(country)).thenReturn(country);
		
		ObjectMapper mapper = new ObjectMapper(); //to convert java object to json because mockmvc will accept only json objects not java objects
		String jsonbody = mapper.writeValueAsString(country); // [mapper.writeValueAsString(country);] this statement will return data in string format. [NOTE: json is also comes under string object only] internally it is json only (like string is a parent of json datatype)
		
		this.mockMvc.perform(put("/updatecountry/{id}",countryid)
				    .content(jsonbody)
				    .contentType(MediaType.APPLICATION_JSON))
		
		.andExpect(status().isOk())
		//.andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Spain"))
		.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Madrid"))
		.andDo(print());
	}
	
	@Test
	@Order(6)
	public void test_deleteCountry() throws Exception {
		country = new Country(5, "Japan", "Tokyo");
		int countryid = 5;
		
		when(countryservice.getcountryById(countryid)).thenReturn(country); // mocking
		//when(countryservice.updateCountry(country)).thenReturn(country);
		
		//ObjectMapper mapper = new ObjectMapper(); //to convert java object to json because mockmvc will accept only json objects not java objects
		//String jsonbody = mapper.writeValueAsString(country); // [mapper.writeValueAsString(country);] this statement will return data in string format. [NOTE: json is also comes under string object only] internally it is json only (like string is a parent of json datatype)
		
		this.mockMvc.perform(delete("/deletecountry/{id}",countryid))
				    //.content(jsonbody)
				    //.contentType(MediaType.APPLICATION_JSON))
		
		.andExpect(status().isOk())
		//.andExpect(MockMvcResultMatchers.jsonPath(".id").value(3))
		//.andExpect(MockMvcResultMatchers.jsonPath(".countryName").value("Spain"))
		//.andExpect(MockMvcResultMatchers.jsonPath(".countryCapital").value("Madrid"))
		.andDo(print());
	}
	
}
