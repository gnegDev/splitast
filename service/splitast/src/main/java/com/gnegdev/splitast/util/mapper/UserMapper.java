package com.gnegdev.splitast.util.mapper;

import com.gnegdev.splitast.dto.CreateUserRequest;
import com.gnegdev.splitast.entity.User;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    User toEntity(CreateUserRequest userDto);

//    CreateUserRequest toDto(User user);
}
