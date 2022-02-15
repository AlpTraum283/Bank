package com.netcracker.repository;

import com.netcracker.model.dto.database.ParameterDto;
import com.netcracker.model.entity.CompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterDto, CompositeId> {

    List<ParameterDto> findByObjId(@Param("id") Integer id);


}
