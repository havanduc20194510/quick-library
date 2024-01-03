package com.haduc.quicklibbooksmanagement.mapper;

import com.haduc.quicklibbooksmanagement.dto.UserDto;
import com.haduc.quicklibbooksmanagement.entity.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-03T23:40:43+0700",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.id( userDto.getId() );
        user.email( userDto.getEmail() );
        user.username( userDto.getUsername() );
        user.password( userDto.getPassword() );
        user.role( userDto.getRole() );
        user.createdAt( userDto.getCreatedAt() );
        user.updatedAt( userDto.getUpdatedAt() );

        return user.build();
    }

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setEmail( user.getEmail() );
        userDto.setUsername( user.getUsername() );
        userDto.setPassword( user.getPassword() );
        userDto.setRole( user.getRole() );
        userDto.setCreatedAt( user.getCreatedAt() );
        userDto.setUpdatedAt( user.getUpdatedAt() );

        return userDto;
    }
}
