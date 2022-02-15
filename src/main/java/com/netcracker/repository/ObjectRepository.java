package com.netcracker.repository;

import com.netcracker.model.dto.database.ObjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;



@Repository
public interface ObjectRepository extends JpaRepository<ObjectDto, Integer> {

    ObjectDto getByObjIdAndType(Integer id, String type);

    List<ObjectDto> getByOwnerAndType(Integer owner, String type);

}
