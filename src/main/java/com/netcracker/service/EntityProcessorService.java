package com.netcracker.service;

import com.netcracker.annotation.Attribute;
import com.netcracker.model.dto.database.ParameterDto;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.BasicEntity;
import com.netcracker.repository.ObjectRepository;
import com.netcracker.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class EntityProcessorService {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ObjectRepository objectRepository;


    public <T extends BasicEntity> T getEntityByIdAndType(Class<T> clazz, Integer id) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        ObjectDto objectDtoList = objectRepository.getByObjIdAndType(id, clazz.getSimpleName().toLowerCase());
        List<ParameterDto> parameterDtoList = parameterRepository.findByObjId(id);

        if (objectDtoList != null && parameterDtoList != null) {
            T obj = clazz.
                    getDeclaredConstructor(Integer.class, Integer.class, String.class, Date.class, String.class).
                    newInstance(id, objectDtoList.getOwner(), objectDtoList.getName(), objectDtoList.getDate(), objectDtoList.getType());


            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

//                System.out.println("field name = " + field.getName());

                if (field.isAnnotationPresent(Attribute.class)) {
//                  todo: работаем с данными из таблицы Parameter

                    Attribute attribute = field.getAnnotation(Attribute.class);
                    int attrValue = attribute.value();

                    for (ParameterDto parameterDto : parameterDtoList) {
//                        System.out.println("Parameter = " + parameter);

                        if (attrValue == parameterDto.getAttrId()) {
//                            System.out.println("Equal field = " + field.getType().getSimpleName());
                            switch (field.getType().getSimpleName()) {
                                case "long":
                                    field.setLong(obj, Long.valueOf(parameterDto.getValue()));
                                    break;
                                case "int":
                                    field.setInt(obj, Integer.valueOf(parameterDto.getValue()));
                                    break;
                                case "String":
                                    field.set(obj, parameterDto.getValue());
                                    break;
                            }
                        }
                    }
                }
            }
//            System.out.println(obj.toString());
            return obj;
        } else {
            System.err.println("Object does not found. Type = " + clazz.getSimpleName() + ", id = " + id);
            return null;
        }

    }

    @Transactional
    public <T extends BasicEntity> ObjectDto saveEntity(T entity) throws IllegalAccessException {

        if (entity == null)
            return null;

        ObjectDto objectDto = new ObjectDto();
        List<ParameterDto> parameterDtoList = new ArrayList<>();

        objectDto.setDate(entity.getDate());
        objectDto.setName(entity.getName());
        objectDto.setObjId(entity.getObjId());
        objectDto.setOwner(entity.getOwner());
        objectDto.setType(entity.getType());


        ObjectDto objectId = objectRepository.save(objectDto);

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = field.getAnnotation(Attribute.class);
//                разбиваем поля на обьекты ParameterDto, вместе с id обьекта, атрибута и значением поля
                if (field.get(entity) != null) {
                    ParameterDto parameterDto = new ParameterDto(
                            objectId.getObjId(),
                            attribute.value(),
                            field.get(entity).toString()
                    );
                    parameterDtoList.add(parameterDto);
                }
            }
        }
        parameterRepository.saveAll(parameterDtoList);
        return objectId;
    }

    public <T extends BasicEntity> T getEntityByNameAndType(Class<T> clazz, String name) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        ObjectDto objectDtoList = objectRepository.getByNameAndType(name, clazz.getSimpleName().toLowerCase());

        if (objectDtoList == null)
            return null;

        List<ParameterDto> parameterDtoList = parameterRepository.findByObjId(objectDtoList.getObjId());

        if (objectDtoList != null && parameterDtoList != null) {
            T obj = clazz.
                    getDeclaredConstructor(Integer.class, Integer.class, String.class, Date.class, String.class).
                    newInstance(objectDtoList.getObjId(), objectDtoList.getOwner(), objectDtoList.getName(), objectDtoList.getDate(), objectDtoList.getType());


            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);

//                System.out.println("field name = " + field.getName());

                if (field.isAnnotationPresent(Attribute.class)) {
//                  todo: работаем с данными из таблицы Parameter

                    Attribute attribute = field.getAnnotation(Attribute.class);
                    int attrValue = attribute.value();

                    for (ParameterDto parameterDto : parameterDtoList) {
//                        System.out.println("Parameter = " + parameter);

                        if (attrValue == parameterDto.getAttrId()) {
//                            System.out.println("Equal field = " + field.getType().getSimpleName());
                            switch (field.getType().getSimpleName()) {
                                case "long":
                                    field.setLong(obj, Long.valueOf(parameterDto.getValue()));
                                    break;
                                case "int":
                                    field.setInt(obj, Integer.valueOf(parameterDto.getValue()));
                                    break;
                                case "String":
                                    field.set(obj, parameterDto.getValue());
                                    break;
                            }
                        }
                    }
                }
            }
//            System.out.println(obj.toString());
            return obj;
        } else {
            System.err.println("Object does not found. Type = " + clazz.getSimpleName() + ", name = " + name);
            return null;
        }
    }

}
