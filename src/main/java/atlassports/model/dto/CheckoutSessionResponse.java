package atlassports.model.dto;

import lombok.Data;

@Data
public class CheckoutSessionResponse {
    private String sessionId;
    private String checkoutUrl;
}
