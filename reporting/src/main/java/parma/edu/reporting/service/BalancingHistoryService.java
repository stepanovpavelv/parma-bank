package parma.edu.reporting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.reporting.dao.jdbc.repository.BalancingHistoryRepository;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.dto.BalancingHistoryRequestDto;
import parma.edu.reporting.mapper.BalancingHistoryMapper;
import parma.edu.reporting.model.BalancingHistory;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BalancingHistoryService {
    private final BalancingHistoryRepository balancingHistoryRepository;
    private final BalancingHistoryMapper mapper;

    @Transactional(readOnly = true)
    public List<BalancingHistoryDto> readBalancingStatistics(BalancingHistoryRequestDto request) {
        List<BalancingHistory> histories = balancingHistoryRepository.findBalancingActivities(request);
        return mapper.toListDto(histories);
    }

    @Transactional(readOnly = true)
    protected ZonedDateTime readCalculatedMaxDate() {
        return balancingHistoryRepository.readMaxBalancingUpdateDate();
    }

    @Transactional
    protected Integer saveBalancingStatistics(BalancingHistoryDto dto) {
        BalancingHistory entity = mapper.toEntity(dto);
        return balancingHistoryRepository.saveBalancingHistory(entity);
    }
}