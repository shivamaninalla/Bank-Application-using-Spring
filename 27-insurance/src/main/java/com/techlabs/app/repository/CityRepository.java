package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.City;

public interface CityRepository extends JpaRepository<City, Long>{

}
