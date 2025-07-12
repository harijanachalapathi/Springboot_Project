package com.example.demo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.example.demo.beans.Country;
import com.example.demo.controllers.AddResponse;
import com.example.demo.repositories.CountryRepository;

@Component
@Service
public class CountryService {

	//static HashMap<Integer, Country> countryIdMap;
	
//	public CountryService() {
//		countryIdMap = new HashMap<Integer,Country>();
//		
//		Country IndCountry = new Country(1,"india","delhi");
//		Country UsCountry = new Country(2,"USA","washington");
//		Country CanadaCountry = new Country(3,"canada","chalapathi");
//		
//		countryIdMap.put(1, IndCountry);
//		countryIdMap.put(2, UsCountry);
//		countryIdMap.put(3, CanadaCountry);
//	}
	
	@Autowired
	CountryRepository countryrep;
	public List getAllCountries() 
	{
		 return countryrep.findAll(); 
	}
	
	public Country getcountryById(int id) 
	{
		return countryrep.findById(id).get();
	}
	
	public Country getcountryByName(String countryName) 
	{
		List<Country> countries= countryrep.findAll();
		Country country = null;
		for(Country con:countries) {
			if(con.getCountryName().equalsIgnoreCase(countryName))
			  country = con;
		}
		return country;
	}
	public Country addCountry(Country country) 
	{
		country.setId(getMaxId());
		countryrep.save(country);
		return country;
	}
	
	//utility method for getting id's in hashmap for adding new country
	public int getMaxId() 
	{
		return countryrep.findAll().size()+1;
	}
	
	public Country updateCountry(Country country) {
		countryrep.save(country); 
		return country;
	}
	
	public AddResponse deleteCountry(int id) {
		countryrep.deleteById(id);
		AddResponse res = new AddResponse();
		res.setMsg("Country Deleted!");
		res.setId(id);
		return res;
	}
	
	
	
	
}
