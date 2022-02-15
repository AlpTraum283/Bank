package com.netcracker.repository;

import com.netcracker.dto.ObjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.netcracker.Constants.*;


@Repository
public interface ObjectRepository extends JpaRepository<ObjectDto, Integer> {

    ObjectDto getByObjIdAndType(Integer id, String type);

    List<ObjectDto> getByOwnerAndType(Integer owner, String type);


}
