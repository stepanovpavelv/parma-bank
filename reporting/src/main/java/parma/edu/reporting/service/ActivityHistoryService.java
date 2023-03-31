package parma.edu.reporting.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import parma.edu.reporting.dao.jdbc.repository.ActivityHistoryRepository;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.dto.ActivityHistoryRequestDto;
import parma.edu.reporting.mapper.ActivityHistoryMapper;
import parma.edu.reporting.model.ActivityHistory;

import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityHistoryService {
    private final ActivityHistoryRepository activityHistoryRepository;
    private final ActivityHistoryMapper mapper;

    @Transactional(readOnly = true)
    public List<ActivityHistoryDto> readHistoryStatistics(ActivityHistoryRequestDto request) {
        List<ActivityHistory> activities = activityHistoryRepository.findActivityHistories(request);
        return mapper.toListDto(activities);
    }

    @Transactional(readOnly = true)
    protected ZonedDateTime readCalculatedMaxDate() {
        return activityHistoryRepository.readMaxHistoryUpdateDate();
    }

    @Transactional
    protected Integer saveHistoryStatistics(ActivityHistoryDto dto) {
        ActivityHistory entity = mapper.toEntity(dto);
        return activityHistoryRepository.saveActivityHistory(entity);
    }
}