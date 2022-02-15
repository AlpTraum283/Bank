package com.netcracker.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.model.dto.database.ObjectDto;
import com.netcracker.model.entity.AccountEntity;
import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import static com.netcracker.Constants.OBJECT_TYPE_ACCOUNT;

@RestController
public class UserEntityController {

    @Autowired
    UserEntityService userEntityService;
    @Autowired
    EntityProcessorService entityProcessorService;

    @GetMapping("/user/{id}")
    public ResponseEntity getUserAndAccountsById(@PathVariable(value = "id") Integer id) throws InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException, IllegalAccessException, JsonProcessingException {

        UserEntity userEntity = (UserEntity) entityProcessorService.getEntityByIdAndType(UserEntity.class, id);
        List<ObjectDto> objectDtoList = userEntityService.getByOwnerAndType(userEntity.getObjId(), OBJECT_TYPE_ACCOUNT);
//        todo: Вернуть обьекты transfer на основе id, после чего засунуть их в список
        List<AccountEntity> accountEntityList = new ArrayList<>();
//        todo: Парсим обьекты dto на обьекты Transfer, заполняя ими список
        for (ObjectDto sample : objectDtoList) {
            AccountEntity accountEntity =
                    (AccountEntity) entityProcessorService.getEntityByIdAndType(AccountEntity.class, sample.getObjId());
            accountEntityList.add(accountEntity);
        }
//        todo: формируем подстроку в виде выпадающего списка для каждой транзакции
        StringBuilder accountFormattedList = new StringBuilder("");
        int counter = 1;
        for (AccountEntity entity : accountEntityList) {

            accountFormattedList.append("{");
            accountFormattedList.append("\"account_id\":\"").append(entity.getObjId()).append("\"");
            if (counter < accountEntityList.size()) {
                accountFormattedList.append(",\"balance\": \"").append(entity.getBalance()).append("\"},");
                counter++;
            } else {
                accountFormattedList.append(",\"balance\": \"").append(entity.getBalance()).append("\"}");
            }

        }
        String response = "{" +
                "\"user_id\": \"" + id +
                "\", \"accounts\": [" + accountFormattedList +
                "]" +
                "}";

        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObject = mapper.readTree(response);

        return ResponseEntity.ok().body(jsonObject);
    }
}
