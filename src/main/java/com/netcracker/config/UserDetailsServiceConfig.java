package com.netcracker.config;

import com.netcracker.model.entity.UserEntity;
import com.netcracker.service.EntityProcessorService;
import com.netcracker.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
@Service
public class UserDetailsServiceConfig implements UserDetailsService {
    @Autowired
    EntityProcessorService entityProcessorService;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        try {
            UserEntity userEntity = entityProcessorService.getEntityByIdAndType(UserEntity.class, Integer.valueOf(id));
            return UserDetailsConfig.convertUserEntityToUserDetailsConfig(userEntity);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return null;
    }
}
