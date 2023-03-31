package parma.edu.money_transfer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import parma.edu.money_transfer.dto.AccountDetailsDto;
import parma.edu.money_transfer.dto.BankAccountDetailsDto;
import parma.edu.money_transfer.model.AccountDetails;
import parma.edu.money_transfer.model.BankAccount;

import java.util.List;

@Mapper(uses = { BankAccountMapper.class })
public interface AccountDetailsMapper {
    AccountDetailsMapper INSTANCE = Mappers.getMapper(AccountDetailsMapper.class);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.actualDate", target = "actualDate")
    @Mapping(source = "entity.amount", target = "amount")
    AccountDetailsDto toDto(AccountDetails entity);

    List<AccountDetailsDto> toListDto(List<AccountDetails> entities);

    @Mapping(source = "accountEntity", target = "account")
    @Mapping(source = "detailEntities", target = "detailItems")
    BankAccountDetailsDto toBankDetailsDto(BankAccount accountEntity, List<AccountDetails> detailEntities);
}