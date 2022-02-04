package com.netcracker.service;

import com.netcracker.Constants;
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
import java.util.List;
import java.util.Map;

@Service
public class EntityProcessorService<T> {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ObjectRepository objectRepository;
    private T obj;

//    Map<String, Integer> constantsMap = Constants.constMap;


    public T getEntityById(Class<? super BasicEntity> clazz, Integer id) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException, NoSuchFieldException {

        ObjectDto objectDtoList = objectRepository.findByObj_id(id, clazz.getSimpleName().toLowerCase());
        List<ParameterDto> parameterDtoList = parameterRepository.findByObj_id(id);


//        obj.name = objectDtoList.getName();

        if (objectDtoList != null && parameterDtoList != null) {
            T obj = (T) clazz.getDeclaredConstructor(Integer.class).newInstance(id);

            Field fieldObjId = obj.getClass().getSuperclass().getDeclaredField("objId");
            fieldObjId.setAccessible(true);
            fieldObjId.set(obj, objectDtoList.getObjId());

            Field fieldName = obj.getClass().getDeclaredField("name");
            fieldName.setAccessible(true);
            fieldName.set(obj, objectDtoList.getName());

            Field fieldDate = obj.getClass().getDeclaredField("date");
            fieldDate.setAccessible(true);
            fieldDate.set(obj, objectDtoList.getDate());

            Field fieldOwner = obj.getClass().getDeclaredField("owner");
            fieldOwner.setAccessible(true);
            fieldOwner.set(obj, objectDtoList.getOwner());

            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
//                System.out.println("field name = " + field.getName());

                if (!field.isAnnotationPresent(Attribute.class)) {
//                    TODO: работаем с обьектом из таблицы object
//                    for (Field field2 : objectDtoList.getClass().getDeclaredFields()) {
//                        field2.setAccessible(true);
//                        if (field.getName().equals(field2.getName())) {
////                            System.out.println("Имя одинакового поля = " + field.getName());
////                            System.out.println(field.getType().getSimpleName());
//                            switch (field.getType().getSimpleName()) {
//                                case "int":
//                                    field.setInt(obj, (Integer) field2.get(objectDtoList));
//                                    break;
//                                case "Date":
//                                case "String":
//                                    field.set(obj, field2.get(objectDtoList));
//                                    break;
//
//                            }
//                        }
//                    }
                } else {
//                    todo: работаем с данными из таблицы Parameter
                    for (ParameterDto parameterDto : parameterDtoList) {
//                        System.out.println("Parameter = " + parameter);
                        Attribute attribute = field.getAnnotation(Attribute.class);
                        int attrValue = attribute.value();

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

//                        for (String str : constantsMap.keySet()) {
//                            if (field.getName().equals(str)
//                                    && constantsMap.get(str) == parameterDto.getAttr_id()) {
////
////                                System.out.println("Type = " + field.getType().getSimpleName());
//                                switch (field.getType().getSimpleName()) {
//                                    case "long":
//                                        field.setLong(obj, Long.valueOf(parameterDto.getValue()));
//                                        break;
//                                    case "String":
//                                        field.set(obj, parameterDto.getValue());
//                                        break;
//                                    case "int":
//                                        field.setInt(obj, Integer.valueOf(parameterDto.getValue()));
//                                        break;
//
//
//                                }
//                            }
//                        }
                    }
                }
            }
            System.out.println(obj.toString());
        } else {
            System.err.println("Object does not found. Type = " + clazz.getSimpleName() + ", id = " + id);
        }

        return null;
    }

}
