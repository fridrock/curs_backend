package ru.fridrock.jir_backend.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.fridrock.jir_backend.dto.projects.ProjectDto;
import ru.fridrock.jir_backend.models.ProjectEntity;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper extends IMapper<ProjectDto, ProjectEntity> {
  @Override
  ProjectDto mapToDto(ProjectEntity entity);
  @Override
  ProjectEntity mapToEntity(ProjectDto dto);

}
