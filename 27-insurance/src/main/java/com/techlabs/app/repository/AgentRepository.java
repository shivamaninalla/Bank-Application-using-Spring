package com.techlabs.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.techlabs.app.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long>{

}
