package ru.fridrock.jir_backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.fridrock.jir_backend.dto.tasks.TaskDto;
import ru.fridrock.jir_backend.models.TaskEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper extends IMapper<TaskDto, TaskEntity> {
    @Override
    TaskDto mapToDto(TaskEntity taskEntity);

    @Override
    TaskEntity mapToEntity(TaskDto dto);

}
