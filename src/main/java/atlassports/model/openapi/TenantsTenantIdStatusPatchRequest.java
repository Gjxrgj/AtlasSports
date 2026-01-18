package atlassports.model.openapi;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import javax.annotation.processing.Generated;
import java.util.Objects;

/**
 * TenantsTenantIdStatusPatchRequest
 */

@JsonTypeName("_tenants__tenantId__status_patch_request")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-12-24T16:37:25.216838800+01:00[Europe/Skopje]", comments = "Generator version: 7.18.0")
public class TenantsTenantIdStatusPatchRequest {

    private @Nullable Boolean active;

    public TenantsTenantIdStatusPatchRequest active(@Nullable Boolean active) {
        this.active = active;
        return this;
    }

    /**
     * Get active
     *
     * @return active
     */

    @Schema(name = "active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @JsonProperty("active")
    public @Nullable Boolean getActive() {
        return active;
    }

    public void setActive(@Nullable Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TenantsTenantIdStatusPatchRequest tenantsTenantIdStatusPatchRequest = (TenantsTenantIdStatusPatchRequest) o;
        return Objects.equals(this.active, tenantsTenantIdStatusPatchRequest.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(active);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TenantsTenantIdStatusPatchRequest {\n");
        sb.append("    active: ").append(toIndentedString(active)).append("\n");
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

