package com.test.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventLogDTO {
    private String id;
    private EventLogState state;
    @Nullable
    private String type;
    @Nullable
    private String host;
    private long timestamp;
}

