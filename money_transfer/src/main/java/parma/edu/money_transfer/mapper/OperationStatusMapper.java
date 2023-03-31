package parma.edu.money_transfer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.OperationStatusDto;
import parma.edu.money_transfer.model.OperationStatus;

import java.util.List;

@Mapper
public interface OperationStatusMapper {
    OperationStatusMapper INSTANCE = Mappers.getMapper(OperationStatusMapper.class);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    OperationStatusDto toDto(OperationStatus entity);

    @InheritInverseConfiguration
    OperationStatus toEntity(OperationStatusDto dto);

    List<OperationStatusDto> toListDto(List<OperationStatus> entities);
}