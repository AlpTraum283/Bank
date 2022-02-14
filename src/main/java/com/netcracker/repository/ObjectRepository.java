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

    @Query(value = "select * from object where obj_id=:id and type=:type", nativeQuery = true)
    ObjectDto findByObjId(@Param("id") Integer id, @Param("type") String type);

    @Query(value = "select o.name as Accounts " +
            "from object o " +
            "where o.owner = :id and o.type = \'" + OBJECT_TYPE_ACCOUNT + "\'", nativeQuery = true)
    List<String> selectAccountsByUserId(@Param("id") Integer id);

    @Query(value = "select o.name as User " +
            "from object o " +
            "where o.obj_id = :id and o.type = \'" + OBJECT_TYPE_USER + "\'", nativeQuery = true)
    String selectUserById(@Param("id") Integer id);

    @Query(value = "select distinct o.obj_id as id, p.value as balance, p2.value as currency, o.date as date " +
            "    from object o, parameter p, parameter p2 " +
            "    where " +
            "o.obj_id = p.obj_id and o.type = \'" + OBJECT_TYPE_ACCOUNT +
            "\' and p.attr_id = \'" + BALANCE_ATTRIBUTE_ID + "\' and p2.attr_id = \'" + CURRENCY_ATTRIBUTE_ID +
            "\' and o.obj_id = :id and p.obj_id = :id and p2.obj_id = :id ", nativeQuery = true)
    Map<String, String> selectAccountById(@Param("id") Integer id);

    @Query(value = "select o.name as accountName " +
            "from object o " +
            "where o.obj_id = 2 and o.type = \'" + OBJECT_TYPE_ACCOUNT + "\'", nativeQuery = true)
    String returnAccountById(@Param("id") Integer id);

    @Query(value = "select p.value as TYPE, p2.value as SUM, o.date as DATE " +
            "from object o, parameter p, parameter p2 " +
            "where o.owner = :id and o.type = \'" + OBJECT_TYPE_TRANSFER + "\' and p.attr_id = " + OPERATION_ATTRIBUTE_ID + " and p2.attr_id = " + SUM_ATTRIBUTE_ID +
            " and o.obj_id = p.obj_id and o.obj_id = p2.obj_id " +
            " and o.date between :startDate and :endDate ", nativeQuery = true)
    List<Map<String, String>> selectOperationsByAccountId(
            @Param("id") Integer id, @Param("startDate") Date startDate, @Param("endDate") Date endDate);


    @Query(value = "select p.value as balance " +
            "from object o, parameter p " +
            "where o.obj_id = :id and p.attr_id = \'" + BALANCE_ATTRIBUTE_ID + "\' and p.obj_id = :id", nativeQuery = true)
    Integer selectBalanceByAccountId(@Param("id") Integer id);

}
