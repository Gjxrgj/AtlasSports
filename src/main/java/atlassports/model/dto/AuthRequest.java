package atlassports.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
