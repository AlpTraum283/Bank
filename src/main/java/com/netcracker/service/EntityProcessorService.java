package com.netcracker.service;

import com.netcracker.annotation.Attribute;
import com.netcracker.dto.ParameterDto;
import com.netcracker.model.*;
import com.netcracker.dto.ObjectDto;
import com.netcracker.repository.ObjectRepository;
import com.netcracker.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Service
public class EntityProcessorService<T> {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ObjectRepository objectRepository;
    private T obj;


    public <T extends BasicEntity> T getEntityById(Class clazz, Integer id) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        ObjectDto objectDtoList = objectRepository.findByObjId(id, clazz.getSimpleName().toLowerCase());
        List<ParameterDto> parameterDtoList = parameterRepository.findByObjId(id);

        if (objectDtoList != null && parameterDtoList != null) {
            T obj = (T) clazz.getDeclaredConstructor(Integer.class).newInstance(id);

            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                for (Field field2 : objectDtoList.getClass().getDeclaredFields()) {
                    field2.setAccessible(true);
                    if (field.getName().equals(field2.getName())) {
                        field.set(obj, field2.get(objectDtoList));
                    }
                }
            }

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
                            System.out.println("Equal field = " + field.getType().getSimpleName());
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
            System.out.println(obj.toString());
        } else {
            System.err.println("Object does not found. Type = " + clazz.getSimpleName() + ", id = " + id);
        }
        return null;
    }

    public <T extends BasicEntity> void saveEntity(T entity) throws IllegalAccessException {

        if (entity == null)
            return;

        ObjectDto objectDto = new ObjectDto();
        List<ParameterDto> parameterDtoList = new ArrayList<>();

        objectDto.setDate(entity.getDate());
        objectDto.setName(entity.getName());
        objectDto.setObjId(entity.getObjId());
        objectDto.setOwner(entity.getOwner());
        objectDto.setType(entity.getType());

        for (Field field : entity.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = field.getAnnotation(Attribute.class);
//                todo: разбиваем поля на обьекты ParameterDto, вместе с id обьекта, атрибута и значением поля
                ParameterDto parameterDto = new ParameterDto(
                        entity.getObjId(),
                        attribute.value(),
                        field.get(entity).toString()
                );
//                todo: добавляем параметр в список параметров
                parameterDtoList.add(parameterDto);
            }
        }
        System.out.println(objectDto.toString());
        parameterDtoList.forEach(System.out::println);

        objectRepository.save(objectDto);
        parameterRepository.saveAll(parameterDtoList);

    }
}
