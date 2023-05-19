package com.manage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manage.entity.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position,Integer>{
     
}
