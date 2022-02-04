package com.netcracker.repository;

import com.netcracker.dto.ObjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ObjectRepository extends JpaRepository<ObjectDto, Integer> {

    @Query(value = "select * from object where obj_id=:id and type=:type", nativeQuery = true)
    ObjectDto findByObjId(@Param("id") Integer id, @Param("type") String type);


}
