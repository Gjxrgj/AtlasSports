package atlassports.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DistributionItemDto {
    private Long id;
    private String name;
    private BigDecimal percentage;
}

