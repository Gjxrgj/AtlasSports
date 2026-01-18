package atlassports.service;

import atlassports.model.dto.ResourceDto;
import atlassports.model.dto.UpsertResourceDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ResourceService {
    Page<ResourceDto> getAllResources(Pageable page);

    ResourceDto createResource(UpsertResourceDto upsertResourceDto);
}
