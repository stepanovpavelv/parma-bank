package parma.edu.reporting.system;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import parma.edu.reporting.dto.ActivityHistoryDto;
import parma.edu.reporting.dto.BalancingHistoryDto;
import parma.edu.reporting.model.ActivityHistory;
import parma.edu.reporting.model.BalancingHistory;

@Configuration
public class MapConfig {

    @Bean(name = "activityMapper")
    public ModelMapper modelMapperActivity() {
        ModelMapper activityMapper = new ModelMapper();
        //activityMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        activityMapper
                .createTypeMap(ActivityHistory.class, ActivityHistoryDto.class)
                .addMappings(mapper -> mapper.map(ActivityHistory::getId, ActivityHistoryDto::setId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getOperationId, ActivityHistoryDto::setOperationId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getOperationTypeId, ActivityHistoryDto::setOperationTypeId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getUserSourceId, ActivityHistoryDto::setUserSourceId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getUserSourceLogin, ActivityHistoryDto::setUserSourceLogin))
                .addMappings(mapper -> mapper.map(ActivityHistory::getAccountSourceId, ActivityHistoryDto::setAccountSourceId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getUserTargetId, ActivityHistoryDto::setUserTargetId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getUserTargetLogin, ActivityHistoryDto::setUserTargetLogin))
                .addMappings(mapper -> mapper.map(ActivityHistory::getAccountTargetId, ActivityHistoryDto::setAccountTargetId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getOperationStatusId, ActivityHistoryDto::setOperationStatusId))
                .addMappings(mapper -> mapper.map(ActivityHistory::getDate, ActivityHistoryDto::setDate))
                .addMappings(mapper -> mapper.map(ActivityHistory::getUpdateDate, ActivityHistoryDto::setUpdateDate))
                .addMappings(mapper -> mapper.map(ActivityHistory::getAmount, ActivityHistoryDto::setAmount));

        activityMapper
                .createTypeMap(ActivityHistoryDto.class, ActivityHistory.class)
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getId, ActivityHistory::setId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getOperationId, ActivityHistory::setOperationId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getOperationTypeId, ActivityHistory::setOperationTypeId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getUserSourceId, ActivityHistory::setUserSourceId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getUserSourceLogin, ActivityHistory::setUserSourceLogin))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getAccountSourceId, ActivityHistory::setAccountSourceId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getUserTargetId, ActivityHistory::setUserTargetId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getUserTargetLogin, ActivityHistory::setUserTargetLogin))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getAccountTargetId, ActivityHistory::setAccountTargetId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getOperationStatusId, ActivityHistory::setOperationStatusId))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getDate, ActivityHistory::setDate))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getUpdateDate, ActivityHistory::setUpdateDate))
                .addMappings(mapper -> mapper.map(ActivityHistoryDto::getAmount, ActivityHistory::setAmount));

        return activityMapper;
    }

    @Bean(name = "balancingMapper")
    public ModelMapper modelMapperBalancing() {
        ModelMapper balancingMapper = new ModelMapper();
        //balancingMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        balancingMapper
                .createTypeMap(BalancingHistory.class, BalancingHistoryDto.class)
                .addMappings(mapper -> mapper.map(BalancingHistory::getId, BalancingHistoryDto::setId))
                .addMappings(mapper -> mapper.map(BalancingHistory::getBankAccountId, BalancingHistoryDto::setAccountId))
                .addMappings(mapper -> mapper.map(BalancingHistory::getUserId, BalancingHistoryDto::setUserId))
                .addMappings(mapper -> mapper.map(BalancingHistory::getUserLogin, BalancingHistoryDto::setUserLogin))
                .addMappings(mapper -> mapper.map(BalancingHistory::getDate, BalancingHistoryDto::setDate))
                .addMappings(mapper -> mapper.map(BalancingHistory::getAmount, BalancingHistoryDto::setAmount));

        balancingMapper
                .createTypeMap(BalancingHistoryDto.class, BalancingHistory.class)
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getId, BalancingHistory::setId))
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getAccountId, BalancingHistory::setBankAccountId))
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getUserId, BalancingHistory::setUserId))
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getUserLogin, BalancingHistory::setUserLogin))
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getDate, BalancingHistory::setDate))
                .addMappings(mapper -> mapper.map(BalancingHistoryDto::getAmount, BalancingHistory::setAmount));

        return balancingMapper;
    }
}