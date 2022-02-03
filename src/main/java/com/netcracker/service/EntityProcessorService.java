package com.netcracker.service;

import com.netcracker.Constants;
import com.netcracker.annotation.Attribute;
import com.netcracker.model.*;
import com.netcracker.model.Object;
import com.netcracker.repository.ObjectRepository;
import com.netcracker.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.netcracker.Constants.*;

@Service
public class EntityProcessorService<T> {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ObjectRepository objectRepository;

    Map<String, Integer> constantsMap = Constants.constMap;


    public T getEntityById(Class<? super BasicEntity> clazz, Integer id) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {

        Object objectList = objectRepository.findByObj_id(id, clazz.getSimpleName().toLowerCase());
        List<Parameter> parameterList = parameterRepository.findByObj_id(id);

        T obj = (T) clazz.getDeclaredConstructor(Integer.class).newInstance(id);

        if (objectList != null && parameterList != null) {
//            if ("User".equals(clazz.getSimpleName())) {
//
//                User user = new User();
//                user.setId(id);
//                user.setName(object.getName());
//                user.setDate(object.getDate());
//
//                for (Field field : User.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    if (field.isAnnotationPresent(Attribute.class)) {
//                        Attribute attribute = field.getAnnotation(Attribute.class);
//                        int value = attribute.value();
//
//                        for (Parameter param : parameterList) {
//                            if (value == param.getAttr_id()) {
//                                field.set(user, param.getValue());
//                            }
//
//                        }
//
//                    }
//
//                }
//
//                System.out.println(user.toString());
//                return (T) user;
//
//            } else if ("Account".equals(clazz.getSimpleName())) {
//
//                Account account = new Account();
//
//                account.setId(id);
//                account.setOwner(object.getParent_id());
//                account.setDate_of_open(object.getDate());
//
//                for (Field field : Account.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    if (field.isAnnotationPresent(Attribute.class)) {
//                        Attribute attribute = field.getAnnotation(Attribute.class);
//                        int value = attribute.value();
//
//                        for (Parameter param : parameterList) {
//                            if (value == param.getAttr_id()) {
//                                if (value == CURRENCY_ATTRIBUTE_ID) {
//                                    field.set(account, param.getValue());
//                                } else {
//                                    field.setLong(account, Long.valueOf(param.getValue()));
//                                }
//
//                            }
//
//                        }
//
//                    }
//
//                }
//
//                System.out.println(account.toString());
//                return (T) account;
//
//
//            } else if ("Transfer".equals(clazz.getSimpleName())) {
//
//                Transfer transfer = new Transfer();
//
//                transfer.setId(id);
//                transfer.setSender(object.getParent_id());
//                transfer.setDate(object.getDate());
//
//                for (Field field : Transfer.class.getDeclaredFields()) {
//                    field.setAccessible(true);
//                    if (field.isAnnotationPresent(Attribute.class)) {
//                        Attribute attribute = field.getAnnotation(Attribute.class);
//                        int value = attribute.value();
//
//                        for (Parameter param : parameterList) {
//                            if (value == param.getAttr_id()) {
//                                if (value == OPERATION_ATTRIBUTE_ID) {
//                                    field.set(transfer, param.getValue());
//                                } else if (value == SUM_ATTRIBUTE_ID) {
//                                    field.setLong(transfer, Long.valueOf(param.getValue()));
//                                } else if (value == RECIPIENT_ATTRIBUTE_ID) {
//                                    field.setInt(transfer, Integer.valueOf(param.getValue()));
//                                }
//                            }
//                        }
//
//                    }
//
//                }
//
//                System.out.println(transfer.toString());
//                return (T) transfer;
//            }
//            System.out.println("Class = " + obj.getClass().getSimpleName());
//            System.out.println("Object = " + objectList);
            for (Field field : obj.getClass().getDeclaredFields()) {
                field.setAccessible(true);
//                System.out.println("field name = " + field.getName());

                if (!field.isAnnotationPresent(Attribute.class)) {
//                    TODO: работаем с обьектом из таблицы object
                    for (Field field2 : objectList.getClass().getDeclaredFields()) {
                        field2.setAccessible(true);
                        if (field.getName().equals(field2.getName())) {
//                            System.out.println("Имя одинакового поля = " + field.getName());
//                            System.out.println(field.getType().getSimpleName());
                            switch (field.getType().getSimpleName()) {
                                case "int":
                                    field.setInt(obj, (Integer) field2.get(objectList));
                                    break;
                                case "Date":
                                case "String":
                                    field.set(obj, field2.get(objectList));
                                    break;

                            }
                        }
                    }
                } else {
//                    todo: работаем с данными из таблицы Parameter
                    for (Parameter parameter : parameterList) {
//                        System.out.println("Parameter = " + parameter);

                        for (String str : constantsMap.keySet()) {
                            if (field.getName().equals(str)
                                    && constantsMap.get(str) == parameter.getAttr_id()) {
//                                System.out.println("Equal field = " + str);
//                                System.out.println("Type = " + field.getType().getSimpleName());
                                switch (field.getType().getSimpleName()) {
                                    case "long":
                                        field.setLong(obj, Long.valueOf(parameter.getValue()));
                                        break;
                                    case "String":
                                        field.set(obj, parameter.getValue());
                                        break;
                                    case "int":
                                        field.setInt(obj, Integer.valueOf(parameter.getValue()));
                                        break;


                                }
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
