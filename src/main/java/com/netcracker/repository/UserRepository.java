package com.netcracker.repository;

import com.netcracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select object.obj_id as id, object.name as name, p.value as password, object.date as date " +
            "from object, parameter p, attribute a " +
            "Where object.obj_id = p.obj_id and p.attr_id = a.id and object.type = 'user'", nativeQuery = true)
    List<User> selectUser();
}
