package parma.edu.money_transfer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.money_transfer.dao.OperationStatusHistoryRepository;
import parma.edu.money_transfer.dto.OperationDto;
import parma.edu.money_transfer.dto.OperationStatusHistoryDto;
import parma.edu.money_transfer.mapper.OperationDetailsMapper;
import parma.edu.money_transfer.mapper.OperationMapper;
import parma.edu.money_transfer.model.Operation;
import parma.edu.money_transfer.model.OperationStatus;
import parma.edu.money_transfer.model.OperationStatusHistory;
import parma.edu.money_transfer.model.enums.OperationState;

import java.util.List;

import static parma.edu.money_transfer.exception.BankingException.NotFoundException;

@Service
@RequiredArgsConstructor
@Transactional(noRollbackFor = { NotFoundException.class })
public class OperationHistoryService {
    private final DictionaryService dictionaryService;
    private final OperationStatusHistoryRepository operationStatusHistoryRepository;

    protected List<OperationStatusHistoryDto> getOperationStatusHistories(Integer operationId) {
        //Operation operation = getOperationById(operationId);
        List<OperationStatusHistory> histories = operationStatusHistoryRepository.findOperationStatusHistoriesByOperationId(operationId);
        return OperationDetailsMapper.INSTANCE.toListDto(histories); //OperationDetailsMapper.INSTANCE.toOperationDetailsDto(operation, histories);
    }

    protected void saveHistoryItem(OperationDto dto, OperationState state) {
        Operation operation = OperationMapper.INSTANCE.toEntity(dto);
        OperationStatus status = getStatusByState(state);
        OperationStatusHistory history = createHistoryElement(operation, status);
        operationStatusHistoryRepository.save(history);
    }

    private OperationStatus getStatusByState(OperationState state) {
        int statusId = state.getId();
        return dictionaryService.getStatusById(statusId);
    }

    private OperationStatusHistory createHistoryElement(Operation operation, OperationStatus status) {
        var historyItem = new OperationStatusHistory();
        historyItem.setOperation(operation);
        historyItem.setStatus(status);
        return historyItem;
    }
}