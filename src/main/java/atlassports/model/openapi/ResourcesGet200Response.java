package atlassports.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.springframework.lang.Nullable;

import javax.annotation.processing.Generated;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ResourcesGet200Response
 */

@JsonTypeName("_resources_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
public class ResourcesGet200Response {

    @Valid
    private List<@Valid Object> content = new ArrayList<>();

    private @Nullable Integer page;

    private @Nullable Integer size;

    private @Nullable Integer totalElements;

    private @Nullable Integer totalPages;

    private @Nullable Boolean first;

    private @Nullable Boolean last;

    public ResourcesGet200Response content(List<@Valid Object> content) {
        this.content = content;
        return this;
    }

    public ResourcesGet200Response addContentItem(Object contentItem) {
        if (this.content == null) {
            this.content = new ArrayList<>();
        }
        this.content.add(contentItem);
        return this;
    }

    /**
     * Get content
     *
     * @return content
     */

    @Schema(name = "content", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("content")
    public List<@Valid Object> getContent() {
        return content;
    }

    public void setContent(List<@Valid Object> content) {
        this.content = content;
    }

    public ResourcesGet200Response page(@Nullable Integer page) {
        this.page = page;
        return this;
    }

    /**
     * Get page
     *
     * @return page
     */

    @Schema(name = "page", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("page")
    public @Nullable Integer getPage() {
        return page;
    }

    public void setPage(@Nullable Integer page) {
        this.page = page;
    }

    public ResourcesGet200Response size(@Nullable Integer size) {
        this.size = size;
        return this;
    }

    /**
     * Get size
     *
     * @return size
     */

    @Schema(name = "size", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("size")
    public @Nullable Integer getSize() {
        return size;
    }

    public void setSize(@Nullable Integer size) {
        this.size = size;
    }

    public ResourcesGet200Response totalElements(@Nullable Integer totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    /**
     * Get totalElements
     *
     * @return totalElements
     */

    @Schema(name = "totalElements", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalElements")
    public @Nullable Integer getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(@Nullable Integer totalElements) {
        this.totalElements = totalElements;
    }

    public ResourcesGet200Response totalPages(@Nullable Integer totalPages) {
        this.totalPages = totalPages;
        return this;
    }

    /**
     * Get totalPages
     *
     * @return totalPages
     */

    @Schema(name = "totalPages", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("totalPages")
    public @Nullable Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(@Nullable Integer totalPages) {
        this.totalPages = totalPages;
    }

    public ResourcesGet200Response first(@Nullable Boolean first) {
        this.first = first;
        return this;
    }

    /**
     * Get first
     *
     * @return first
     */

    @Schema(name = "first", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("first")
    public @Nullable Boolean getFirst() {
        return first;
    }

    public void setFirst(@Nullable Boolean first) {
        this.first = first;
    }

    public ResourcesGet200Response last(@Nullable Boolean last) {
        this.last = last;
        return this;
    }

    /**
     * Get last
     *
     * @return last
     */

    @Schema(name = "last", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("last")
    public @Nullable Boolean getLast() {
        return last;
    }

    public void setLast(@Nullable Boolean last) {
        this.last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ResourcesGet200Response resourcesGet200Response = (ResourcesGet200Response) o;
        return Objects.equals(this.content, resourcesGet200Response.content) &&
                Objects.equals(this.page, resourcesGet200Response.page) &&
                Objects.equals(this.size, resourcesGet200Response.size) &&
                Objects.equals(this.totalElements, resourcesGet200Response.totalElements) &&
                Objects.equals(this.totalPages, resourcesGet200Response.totalPages) &&
                Objects.equals(this.first, resourcesGet200Response.first) &&
                Objects.equals(this.last, resourcesGet200Response.last);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content, page, size, totalElements, totalPages, first, last);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class ResourcesGet200Response {\n");
        sb.append("    content: ").append(toIndentedString(content)).append("\n");
        sb.append("    page: ").append(toIndentedString(page)).append("\n");
        sb.append("    size: ").append(toIndentedString(size)).append("\n");
        sb.append("    totalElements: ").append(toIndentedString(totalElements)).append("\n");
        sb.append("    totalPages: ").append(toIndentedString(totalPages)).append("\n");
        sb.append("    first: ").append(toIndentedString(first)).append("\n");
        sb.append("    last: ").append(toIndentedString(last)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

