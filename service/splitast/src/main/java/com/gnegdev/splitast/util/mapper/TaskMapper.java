package com.gnegdev.splitast.util.mapper;

import com.gnegdev.splitast.dto.CreateUserRequest;
import com.gnegdev.splitast.dto.TaskDto;
import com.gnegdev.splitast.entity.Task;
import com.gnegdev.splitast.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TaskMapper {

//    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "report", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "status", ignore = true)
    Task toEntity(TaskDto taskDto);
//    CreateUserRequest toDto(User user);
}
