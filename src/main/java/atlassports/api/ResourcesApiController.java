package atlassports.api;

import atlassports.model.dto.ResourceDto;
import atlassports.model.dto.UpsertResourceDto;
import atlassports.model.openapi.ResourcesGet200Response;
import atlassports.repository.ResourceRepository;
import atlassports.service.ResourceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.processing.Generated;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
@Controller
@RequestMapping("${openapi.atlasSports.base-path:/api/resources}")
public class ResourcesApiController implements ResourcesApi {

    private final NativeWebRequest request;
    private final ResourceService resourceService;

    @Autowired
    public ResourcesApiController(NativeWebRequest request, ResourceService resourceService) {
        this.request = request;
        this.resourceService = resourceService;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<ResourceDto>> resourcesGet(Pageable page) {
        return ResponseEntity.ok(resourceService.getAllResources(page));
    }

    @Override
    @PostMapping
    public ResponseEntity<ResourceDto> resourcesPost(@Valid @RequestBody UpsertResourceDto upsertResourceDto) {
        return ResponseEntity.ok(resourceService.createResource(upsertResourceDto));
    }
}
