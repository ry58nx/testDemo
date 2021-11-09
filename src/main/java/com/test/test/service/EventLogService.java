package com.test.test.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.test.dto.EventLogDTO;
import com.test.test.dto.EventLogsEntity;
import com.test.test.repository.EventLogsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Component
public class EventLogService {

    public static ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    EventLogsRepository repository;

    public void parseAndFlagTheLogs(String fileName) throws IOException {
        try {
            log.info("Start processing the log file at location - {} ", fileName);
            List<EventLogDTO> logData = parseTheLogData(fileName);
            List<EventLogsEntity> eventAlertLogs = flagTheLogData(logData);
            persistData(eventAlertLogs);
            log.info("The file processing is completed");
        } catch (IOException io) {
            log.error("fatal to parse the file - {} ", io.getMessage());
            throw io;
        } catch (Exception e) {
            log.error("fatal to process the file with exception - {} ", e.getMessage());
            throw e;
        }
    }

    private void persistData(List<EventLogsEntity> eventAlertLogs) {
        eventAlertLogs.forEach(eventLogsEntity -> repository.save(eventLogsEntity));
    }

    private List<EventLogsEntity> flagTheLogData(List<EventLogDTO> logData) {
       List<EventLogsEntity> alertLogs= new ArrayList<>();

        Map<String, List<EventLogDTO>> groupedLogsById =
                logData.stream().collect(Collectors.groupingBy(EventLogDTO::getId));

        groupedLogsById.forEach((s, eventLogDTOS) -> {
            eventLogDTOS.sort(Comparator.comparing(EventLogDTO::getTimestamp).reversed());
            long executionTime = eventLogDTOS.stream().map(EventLogDTO::getTimestamp)
                    .reduce( (finishTime, startTime) -> finishTime - startTime).orElse(0L);
            alertLogs.add( EventLogsEntity.fromEventLogs(eventLogDTOS.get(0), executionTime));
        });

        return alertLogs;
    }

    private List<EventLogDTO> parseTheLogData(String fileName) throws IOException {
        Stream<String> logLines = Files.lines(Paths.get(fileName));
        return logLines.map(this::getLogDataForInputString)
                .filter(Optional::isPresent).map(Optional::get)
                .collect(toList());
    }

    private Optional<EventLogDTO> getLogDataForInputString(String line) {
        try {
            return Optional.of(objectMapper.readValue(line, EventLogDTO.class));
        } catch (JsonProcessingException e) {
            log.error("Error while parsing the file - {}" , e.getMessage());
            return Optional.empty();
        }

    }
}
