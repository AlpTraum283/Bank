package com.netcracker.repository;

import com.netcracker.dto.ObjectDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static com.netcracker.Constants.*;


@Repository
public interface ObjectRepository extends JpaRepository<ObjectDto, Integer> {

    @Query(value = "select * from object where obj_id=:id and type=:type", nativeQuery = true)
    ObjectDto findByObjId(@Param("id") Integer id, @Param("type") String type);

    @Query(value = "select o1.name, o2.name as account_name " +
            "from object o1, object o2 " +
            "where o1.obj_id = o2.owner and o2.type = \'" + OBJECT_TYPE_ACCOUNT + "\' and o1.type = \'" + OBJECT_TYPE_USER + "\' and o1.obj_id = :id", nativeQuery = true)
    List<String> selectUserByIdAndAccounts(@Param("id") Integer id);

    @Query(value = "select distinct o.obj_id as id, p.value as balance, p2.value as currency, o.date as date " +
            "    from object o, parameter p, parameter p2 " +
            "    where " +
            "o.obj_id = p.obj_id and o.type = \'" + OBJECT_TYPE_ACCOUNT +
            "\' and p.attr_id = \'" + BALANCE_ATTRIBUTE_ID + "\' and p2.attr_id = \'" + CURRENCY_ATTRIBUTE_ID +
            "\' and o.obj_id = :id and p.obj_id = :id and p2.obj_id = :id ", nativeQuery = true)
    Map<String,String> selectAccountById(@Param("id") Integer id);

}
