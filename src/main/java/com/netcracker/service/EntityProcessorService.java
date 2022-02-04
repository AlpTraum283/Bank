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

}
