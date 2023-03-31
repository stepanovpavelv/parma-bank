package parma.edu.reporting.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.model.BalancingHistory;

import java.util.ArrayList;
import java.util.List;

@Component
public class BalancingHistoryMapper {
    private final ModelMapper modelMapper;

    public BalancingHistoryMapper(@Qualifier("balancingMapper") ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public BalancingHistoryDto toDto(BalancingHistory entity) {
        return modelMapper.map(entity, BalancingHistoryDto.class);
    }

    public BalancingHistory toEntity(BalancingHistoryDto dto) {
        return modelMapper.map(dto, BalancingHistory.class);
    }

    public List<BalancingHistoryDto> toListDto(List<BalancingHistory> entities) {
        if (entities == null) {
            return null;
        }

        List<BalancingHistoryDto> list = new ArrayList<>(entities.size());
        for (BalancingHistory entity : entities) {
            list.add(toDto(entity));
        }

        return list;
    }
}