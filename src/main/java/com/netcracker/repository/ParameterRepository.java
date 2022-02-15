package com.netcracker.repository;

import com.netcracker.dto.ParameterDto;
import com.netcracker.model.CompositeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParameterRepository extends JpaRepository<ParameterDto, CompositeId> {

    @Query(value = "Select * from parameter where obj_id=:id", nativeQuery = true)
    List<ParameterDto> findByObjId(@Param("id") Integer id);

//    Integer selectByObjIdAndAttrId(Integer objId, Integer attrId);


}
