package parma.edu.reporting.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.model.ActivityHistory;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActivityHistoryMapper {
    private final ModelMapper modelMapper;

    public ActivityHistoryMapper(@Qualifier("activityMapper") ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ActivityHistoryDto toDto(ActivityHistory entity) {
        return modelMapper.map(entity, ActivityHistoryDto.class);
    }

    public ActivityHistory toEntity(ActivityHistoryDto dto) {
        return modelMapper.map(dto, ActivityHistory.class);
    }

    public List<ActivityHistoryDto> toListDto(List<ActivityHistory> entities) {
        if (entities == null) {
            return null;
        }

        List<ActivityHistoryDto> list = new ArrayList<>(entities.size());
        for (ActivityHistory entity : entities) {
            list.add(toDto(entity));
        }

        return list;
    }
}