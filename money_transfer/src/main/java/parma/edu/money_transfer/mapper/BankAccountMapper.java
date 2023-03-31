package parma.edu.money_transfer.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.BankAccountDto;
import parma.edu.money_transfer.model.BankAccount;

import java.util.List;

@Mapper
public interface BankAccountMapper {
    BankAccountMapper INSTANCE = Mappers.getMapper(BankAccountMapper.class);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.userId", target = "userId")
    @Mapping(source = "entity.isEnabled", target = "isEnabled")
    @Mapping(source = "entity.createDate", target = "createDate")
    @Mapping(source = "entity.updateDate", target = "updateDate")
    BankAccountDto toDto(BankAccount entity);

    @Mapping(target = "details", ignore = true)
    @Mapping(target = "operations", ignore = true)
    @InheritInverseConfiguration
    BankAccount toEntity(BankAccountDto dto);

    List<BankAccountDto> toListDto(List<BankAccount> entities);
}