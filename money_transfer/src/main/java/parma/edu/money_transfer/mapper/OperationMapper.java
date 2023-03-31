package parma.edu.money_transfer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.OperationDto;
import parma.edu.money_transfer.model.Operation;

import java.util.List;

@Mapper(uses = { BankAccountMapper.class, OperationTypeMapper.class })
public interface OperationMapper {
    OperationMapper INSTANCE = Mappers.getMapper(OperationMapper.class);

    @Mapping(target = "history", ignore = true)
    @Mapping(source = "dto.id", target = "id")
    @Mapping(source = "dto.accountSource", target = "accountSource")
    @Mapping(source = "dto.accountTarget", target = "accountTarget")
    @Mapping(source = "dto.operationType", target = "type")
    @Mapping(source = "dto.date", target = "date")
    @Mapping(source = "dto.amount", target = "amount")
    @Mapping(source = "dto.updateDate", target = "updateDate")
    Operation toEntity(OperationDto dto);

    @InheritInverseConfiguration
    OperationDto toDto(Operation entity);

    List<OperationDto> toListDto(List<Operation> entities);
}