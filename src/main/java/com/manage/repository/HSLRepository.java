package com.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.entity.HSL;

@Repository
public interface HSLRepository extends JpaRepository<HSL,Integer>{
    
}
