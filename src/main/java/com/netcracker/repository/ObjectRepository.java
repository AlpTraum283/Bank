package com.netcracker.repository;

import com.netcracker.model.Object;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjectRepository extends JpaRepository<Object, Integer> {

    @Query(value = "select * from object where obj_id=:id  and type=:type", nativeQuery = true)
    Object findByObj_id(@Param("id") Integer id, @Param("type") String type);


}
