package com.netcracker.repository;

import com.netcracker.model.CompositeId;
import com.netcracker.model.Parameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ParameterRepository extends JpaRepository<Parameter, CompositeId> {

    @Query(value = "Select * from parameter where obj_id=:id", nativeQuery = true)
    List<Parameter> findByObj_id(@Param("id") Integer id);
}
