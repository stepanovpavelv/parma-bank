package parma.edu.money_transfer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.OperationTypeDto;
import parma.edu.money_transfer.model.OperationType;

import java.util.List;

@Mapper
public interface OperationTypeMapper {
    OperationTypeMapper INSTANCE = Mappers.getMapper(OperationTypeMapper.class);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.name", target = "name")
    @Mapping(source = "entity.isExpense", target = "isExpense")
    OperationTypeDto toDto(OperationType entity);

    @InheritInverseConfiguration
    OperationType toEntity(OperationTypeDto dto);

    List<OperationTypeDto> toListDto(List<OperationType> entities);
}