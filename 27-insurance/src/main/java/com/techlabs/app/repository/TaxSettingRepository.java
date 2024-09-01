package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.TaxSetting;

public interface TaxSettingRepository extends JpaRepository<TaxSetting, Long>{

}
