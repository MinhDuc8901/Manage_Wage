package com.manage.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.manage.entity.Wage;

@Repository
public interface WageRepository extends JpaRepository<Wage, Integer> {
    @Query("Select w from Wage w where w.user = :id")
    List<Wage> getListWageByUser(@Param("id") Integer id);
}
