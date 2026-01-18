package atlassports.service.impl;

import atlassports.mappers.ScheduleOverrideMapper;
import atlassports.model.ScheduleOverride;
import atlassports.model.dto.ScheduleOverrideDto;
import atlassports.model.dto.UpsertScheduleOverrideDto;
import atlassports.repository.ScheduleOverrideRepository;
import atlassports.service.ScheduleOverrideService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScheduleOverrideServiceImpl implements ScheduleOverrideService {

    private final ScheduleOverrideRepository scheduleOverrideRepository;
    private final ScheduleOverrideMapper scheduleOverrideMapper;

    public ScheduleOverrideServiceImpl(ScheduleOverrideRepository scheduleOverrideRepository, ScheduleOverrideMapper scheduleOverrideMapper) {
        this.scheduleOverrideRepository = scheduleOverrideRepository;
        this.scheduleOverrideMapper = scheduleOverrideMapper;
    }

    @Override
    public ScheduleOverrideDto createScheduleOverride(UpsertScheduleOverrideDto upsertScheduleOverrideDto) {
        ScheduleOverride scheduleOverride = scheduleOverrideMapper.toEntity(upsertScheduleOverrideDto);

        ScheduleOverride savedScheduleOverride = scheduleOverrideRepository.save(scheduleOverride);

        return scheduleOverrideMapper.toDto(savedScheduleOverride);
    }

    @Override
    public List<ScheduleOverrideDto> createMultipleScheduleOverrides(List<UpsertScheduleOverrideDto> upsertScheduleOverrideDtos) {
        List<ScheduleOverride> scheduleOverrides = new ArrayList<>();

        for(UpsertScheduleOverrideDto upsertScheduleOverrideDto: upsertScheduleOverrideDtos){

            scheduleOverrides.add(scheduleOverrideMapper.toEntity(upsertScheduleOverrideDto));
        }

        List<ScheduleOverride> savedScheduleOverrides = scheduleOverrideRepository.saveAll(scheduleOverrides);

        return scheduleOverrideMapper.toDto(savedScheduleOverrides);
    }
}
