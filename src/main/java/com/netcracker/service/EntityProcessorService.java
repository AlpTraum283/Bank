package com.netcracker.service;

import com.netcracker.annotation.Attribute;
import com.netcracker.model.*;
import com.netcracker.model.Object;
import com.netcracker.repository.ObjectRepository;
import com.netcracker.repository.ParameterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;

import static com.netcracker.Constants.*;

@Service
public class EntityProcessorService<T> {

    @Autowired
    ParameterRepository parameterRepository;
    @Autowired
    ObjectRepository objectRepository;


    public T getEntityById(Class<?> clazz, Integer id) throws IllegalAccessException {

        Object object = objectRepository.findByObj_id(id, clazz.getSimpleName().toLowerCase());
        List<Parameter> parameterList = parameterRepository.findByObj_id(id);

        if (object != null && parameterList != null) {
            if ("User".equals(clazz.getSimpleName())) {

                User user = new User();
                user.setId(id);
                user.setName(object.getName());
                user.setDate(object.getDate());

                for (Field field : User.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Attribute.class)) {
                        Attribute attribute = field.getAnnotation(Attribute.class);
                        int value = attribute.value();

                        for (Parameter param : parameterList) {
                            if (value == param.getAttr_id()) {
                                field.set(user, param.getValue());
                            }

                        }

                    }

                }

                System.out.println(user.toString());
                return (T) user;

            } else if ("Account".equals(clazz.getSimpleName())) {

                Account account = new Account();

                account.setId(id);
                account.setOwner(object.getParent_id());
                account.setDate_of_open(object.getDate());

                for (Field field : Account.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Attribute.class)) {
                        Attribute attribute = field.getAnnotation(Attribute.class);
                        int value = attribute.value();

                        for (Parameter param : parameterList) {
                            if (value == param.getAttr_id()) {
                                if (value == CURRENCY_ATTRIBUTE_ID) {
                                    field.set(account, param.getValue());
                                } else {
                                    field.setLong(account, Long.valueOf(param.getValue()));
                                }

                            }

                        }

                    }

                }

                System.out.println(account.toString());
                return (T) account;


            } else if ("Transfer".equals(clazz.getSimpleName())) {

                Transfer transfer = new Transfer();

                transfer.setId(id);
                transfer.setSender(object.getParent_id());
                transfer.setDate(object.getDate());

                for (Field field : Transfer.class.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(Attribute.class)) {
                        Attribute attribute = field.getAnnotation(Attribute.class);
                        int value = attribute.value();

                        for (Parameter param : parameterList) {
                            if (value == param.getAttr_id()) {
                                if (value == OPERATION_ATTRIBUTE_ID) {
                                    field.set(transfer, param.getValue());
                                } else if (value == SUM_ATTRIBUTE_ID) {
                                    field.setLong(transfer, Long.valueOf(param.getValue()));
                                } else if (value == RECIPIENT_ATTRIBUTE_ID) {
                                    field.setInt(transfer, Integer.valueOf(param.getValue()));
                                }
                            }
                        }

                    }

                }

                System.out.println(transfer.toString());
            }
        } else {
            System.err.println("Object does not found. Type = " + clazz.getSimpleName() + ", id = " + id);
        }

        return null;
    }

}
