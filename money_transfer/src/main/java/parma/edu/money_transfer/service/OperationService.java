package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dao.OperationRepository;
import parma.edu.money_transfer.dto.OperationDetailsDto;
import parma.edu.money_transfer.dto.OperationDto;
import parma.edu.money_transfer.dto.OperationStatusHistoryDto;
import parma.edu.money_transfer.mapper.OperationMapper;
import parma.edu.money_transfer.model.Operation;
import parma.edu.money_transfer.model.enums.OperationState;

import static parma.edu.money_transfer.exception.BankingException.NotFoundException;
import static parma.edu.money_transfer.exception.BankingException.ForbiddenException;

import java.util.List;
import java.util.Optional;

/**
 * Сервис по работе с банковскими операциями.
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperationService {
    private final OperationValidationService operationValidationService;
    private final OperationHistoryService operationHistoryService;
    private final OperationKafkaConsumer operationKafkaConsumer;
    private final OperationRepository operationRepository;

    public OperationDto getOperationByIdWithDto(Integer id) {
        Operation operation = getOperationById(id);
        return OperationMapper.INSTANCE.toDto(operation);
    }

    public List<OperationDto> getOperationByIds(List<Integer> ids) {
        List<Operation> operations = operationRepository.findAllById(ids);
        return OperationMapper.INSTANCE.toListDto(operations);
    }

    public List<OperationDto> getOperationsBySourceId(Integer sourceAccountId) {
        List<Operation> operations = operationRepository.getOperationsByAccountSourceId(sourceAccountId);
        return OperationMapper.INSTANCE.toListDto(operations);
    }

    public OperationDetailsDto getStatusHistoryByOperationId(Integer operationId) {
        OperationDto dto = getOperationByIdWithDto(operationId);
        List<OperationStatusHistoryDto> histories = operationHistoryService.getOperationStatusHistories(operationId);
        return new OperationDetailsDto(dto, histories);
    }

    @Transactional(noRollbackFor = { ForbiddenException.class, NotFoundException.class })
    public Integer createOperation(OperationDto operationDto) {
        // сразу создание (только предварительная валидация без проверки баланса)
        operationValidationService.validateOperation(operationDto);

        Operation operation = OperationMapper.INSTANCE.toEntity(operationDto);
        operation = operationRepository.save(operation);
        operationDto.setId(operation.getId());

        operationHistoryService.saveHistoryItem(operationDto, OperationState.CREATED);

        sleepMainThread(300L);
        operationKafkaConsumer.send(operationDto);

        return operation.getId();
    }

    private void sleepMainThread(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private Operation getOperationById(Integer id) {
        Optional<Operation> operationOpt = operationRepository.findById(id);
        if (operationOpt.isEmpty()) {
            throw new NotFoundException("Operation not exists", id);
        }

        return operationOpt.get();
    }
}