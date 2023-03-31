package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dao.OperationStatusRepository;
import parma.edu.money_transfer.dao.OperationTypeRepository;
import parma.edu.money_transfer.dto.OperationStatusDto;
import parma.edu.money_transfer.dto.OperationTypeDto;
import parma.edu.money_transfer.mapper.OperationStatusMapper;
import parma.edu.money_transfer.mapper.OperationTypeMapper;
import parma.edu.money_transfer.model.OperationStatus;
import parma.edu.money_transfer.model.OperationType;

import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы со справочной информацией.
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, noRollbackFor = { NotFoundException.class })
public class DictionaryService {
    private final OperationStatusRepository operationStatusRepository;
    private final OperationTypeRepository operationTypeRepository;

    @Cacheable("all_operation_types")
    public List<OperationTypeDto> getAllTypes() {
        log.info("Getting entities from database");
        List<OperationType> types = operationTypeRepository.findAll();
        return OperationTypeMapper.INSTANCE.toListDto(types);
    }

    @Cacheable("all_operation_statuses")
    public List<OperationStatusDto> getAllStatuses() {
        log.info("Getting entities from database");
        List<OperationStatus> statuses = operationStatusRepository.findAll();
        return OperationStatusMapper.INSTANCE.toListDto(statuses);
    }

    @Cacheable(value = "operation_type", key = "#id")
    public OperationTypeDto getTypeByIdWithDto(Integer id) {
        OperationType type = getTypeById(id);
        return OperationTypeMapper.INSTANCE.toDto(type);
    }

    @Cacheable(value = "operation_status", key = "#id")
    public OperationStatusDto getStatusByIdWithDto(Integer id) {
        OperationStatus status = getStatusById(id);
        return OperationStatusMapper.INSTANCE.toDto(status);
    }

    protected OperationType getTypeById(Integer id) {
        Optional<OperationType> typeOpt = operationTypeRepository.findById(id);
        if (typeOpt.isEmpty()) {
            throw new NotFoundException("Operation type not exists");
        }

        return typeOpt.get();
    }

    protected OperationStatus getStatusById(Integer id) {
        Optional<OperationStatus> statusOpt = operationStatusRepository.findById(id);
        if (statusOpt.isEmpty()) {
            throw new NotFoundException("Operation status not exists");
        }

        return statusOpt.get();
    }
}