package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dto.OperationDto;

import static parma.edu.money_transfer.exception.BankingException.BadRequestException;
import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = { BadRequestException.class, NotFoundException.class })
public class OperationValidationService {
    private final DictionaryService dictionaryService;
    private final BankAccountService bankAccountService;

    protected void validateOperation(OperationDto dto) {
        validateOperationReference(dto);
        validateOperationType(dto);
        validateAccountSource(dto);
        validateAccountTarget(dto);
        validateAmount(dto);
    }

    private void validateOperationReference(OperationDto dto) {
        if (dto == null) {
            throw new BadRequestException("Operation is not correct, null value!");
        }
    }

    private void validateOperationType(OperationDto dto) {
        if (dto.getOperationType() == null || dto.getOperationType().getId() == null) {
            throw new BadRequestException("Operation type is null!", dto);
        } else {
            dto.setOperationType(dictionaryService.getTypeByIdWithDto(dto.getOperationType().getId()));
        }
    }

    private void validateAccountSource(OperationDto dto) {
        if (dto.getAccountSource() == null || dto.getAccountSource().getId() == null) {
            throw new BadRequestException("Account source is null!", dto);
        } else {
            dto.setAccountSource(bankAccountService.getAccountByIdWithDto(dto.getAccountSource().getId()));
        }
    }

    private void validateAccountTarget(OperationDto dto) {
        if (!dto.getOperationType().getIsExpense() && (dto.getAccountTarget() != null && dto.getAccountTarget().getId() != null)) {
            throw new BadRequestException("Account target must be null due to incoming operation!", dto);
        } else if (dto.getAccountTarget() != null && dto.getAccountTarget().getId() != null) {
            dto.setAccountTarget(bankAccountService.getAccountByIdWithDto(dto.getAccountTarget().getId()));
        }
    }

    private void validateAmount(OperationDto dto) {
        if (dto.getAmount() <= 0) {
            throw new BadRequestException("Operation amount is negative or zero!", dto);
        }
    }
}