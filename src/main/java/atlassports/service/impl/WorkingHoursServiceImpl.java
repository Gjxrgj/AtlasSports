package atlassports.service.impl;

import atlassports.mappers.WorkingHoursMapper;
import atlassports.model.Resource;
import atlassports.model.WorkingHours;
import atlassports.model.dto.UpsertWorkingHoursDto;
import atlassports.model.dto.WorkingHoursDto;
import atlassports.repository.ResourceRepository;
import atlassports.repository.WorkingHoursRepository;
import atlassports.service.WorkingHoursService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.ResourceChainRegistration;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final WorkingHoursMapper workingHoursMapper;
    private final ResourceRepository resourceRepository;
    private final WorkingHoursRepository workingHoursRepository;

    public WorkingHoursServiceImpl(WorkingHoursMapper workingHoursMapper, ResourceRepository resourceRepository, WorkingHoursRepository workingHoursRepository) {
        this.workingHoursMapper = workingHoursMapper;
        this.resourceRepository = resourceRepository;
        this.workingHoursRepository = workingHoursRepository;
    }

    @Override
    @Transactional
    public List<WorkingHoursDto> createWorkingHours(Long resourceId, List<UpsertWorkingHoursDto> workingHoursDtos) {
        Resource resource = resourceRepository.findById(resourceId)
                .orElseThrow(() -> new NoSuchElementException("Resource with id " + resourceId + " doesn't exist."));

        LocalDate today = LocalDate.now();

        List<WorkingHours> existing = workingHoursRepository.findAllByResource_Id(resourceId);
        existing.forEach(wh -> wh.setValidTo(today.minusDays(1)));
        workingHoursRepository.saveAll(existing);

        List<WorkingHours> newWorkingHours = workingHoursMapper.toEntity(workingHoursDtos);
        newWorkingHours.forEach(wh -> {
            wh.setResource(resource);
            wh.setValidFrom(today);
            wh.setValidTo(null);

            if (Boolean.FALSE.equals(wh.getClosed())) {
                if (wh.getOpenTime() == null || wh.getCloseTime() == null || !wh.getOpenTime().isBefore(wh.getCloseTime())) {
                    throw new IllegalArgumentException("Invalid working hours for day " + wh.getDayOfWeek());
                }
            }
        });

        List<WorkingHours> saved = workingHoursRepository.saveAll(newWorkingHours);
        return workingHoursMapper.toDto(saved);
    }
}

