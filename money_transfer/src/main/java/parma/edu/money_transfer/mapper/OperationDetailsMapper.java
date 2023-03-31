package parma.edu.money_transfer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.OperationDetailsDto;
import parma.edu.money_transfer.dto.OperationStatusHistoryDto;
import parma.edu.money_transfer.model.Operation;
import parma.edu.money_transfer.model.OperationStatusHistory;

import java.util.List;

@Mapper(uses = { OperationStatusMapper.class, OperationMapper.class })
public interface OperationDetailsMapper {
    OperationDetailsMapper INSTANCE = Mappers.getMapper(OperationDetailsMapper.class);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.date", target = "date")
    @Mapping(source = "entity.status", target = "status")
    OperationStatusHistoryDto toDto(OperationStatusHistory entity);

    List<OperationStatusHistoryDto> toListDto(List<OperationStatusHistory> entities);

    @Mapping(source = "operationEntity", target = "operation")
    @Mapping(source = "historyEntities", target = "statuses")
    OperationDetailsDto toOperationDetailsDto(Operation operationEntity, List<OperationStatusHistory> historyEntities);
}