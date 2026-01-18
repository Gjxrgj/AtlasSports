package atlassports.service.impl;

import atlassports.mappers.ResourceMapper;
import atlassports.model.Resource;
import atlassports.model.WorkingHours;
import atlassports.model.dto.ResourceDto;
import atlassports.model.dto.UpsertResourceDto;
import atlassports.repository.ResourceRepository;
import atlassports.service.ResourceService;
import atlassports.service.ScheduleOverrideService;
import atlassports.service.WorkingHoursService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final ResourceMapper resourceMapper;
    private final WorkingHoursService workingHoursService;
    private final ScheduleOverrideService scheduleOverrideService;

    public ResourceServiceImpl(ResourceRepository resourceRepository, ResourceMapper resourceMapper, WorkingHoursService workingHoursService, ScheduleOverrideService scheduleOverrideService) {
        this.resourceRepository = resourceRepository;
        this.resourceMapper = resourceMapper;
        this.workingHoursService = workingHoursService;
        this.scheduleOverrideService = scheduleOverrideService;
    }

    @Override
    public Page<ResourceDto> getAllResources(Pageable page) {
        return resourceRepository.findAll(page).map(resourceMapper::toDto);
    }

    @Override
    @Transactional
    public ResourceDto createResource(UpsertResourceDto upsertResourceDto) {
        Resource resource = resourceMapper.toEntity(upsertResourceDto);
        Resource savedResource = resourceRepository.save(resource);

        workingHoursService.createWorkingHours(savedResource.getId(), upsertResourceDto.getWorkingHours());

        scheduleOverrideService.createMultipleScheduleOverrides(upsertResourceDto.getScheduleOverrideDtos());

        return resourceMapper.toDto(savedResource);
    }
}
