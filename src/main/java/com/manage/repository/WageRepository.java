package com.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.entity.Wage;

@Repository
public interface WageRepository extends JpaRepository<Wage,Integer>{
    
}
