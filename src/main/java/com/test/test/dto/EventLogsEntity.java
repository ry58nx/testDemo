package com.test.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static java.util.Optional.ofNullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_log")
public class EventLogsEntity {
    @Id
    private String id;
    private String type;
    private String host;
    private long duration;
    private boolean alert;

    public static EventLogsEntity fromEventLogs(EventLogDTO eventLogDTO, long duration) {
        EventLogsEntity e = new EventLogsEntity();
        e.setAlert(duration > 4);
        e.setDuration(duration);
        e.setId(eventLogDTO.getId());
        ofNullable(eventLogDTO.getHost()).ifPresent(e::setHost);
        ofNullable(eventLogDTO.getType()).ifPresent(e::setType);
        return e;
    }
}
