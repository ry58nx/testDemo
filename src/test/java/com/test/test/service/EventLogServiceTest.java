package com.test.test.service;

import com.test.test.dto.EventLogsEntity;
import com.test.test.repository.EventLogsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EventLogServiceTest {

    @Mock
    EventLogsRepository repository;
    @InjectMocks
    private EventLogService service;

    @Test
    void parseAndFlagTheLogs_ifInvalidFileInput_ThrowException() throws IOException {
        assertThrows(IOException.class, () -> service.parseAndFlagTheLogs("anyFile"));
    }

    @Test
    void parseAndFlagTheLogs_ifValidFileInput_executeSuccessfully() throws IOException {
        service.parseAndFlagTheLogs("src/main/resources/Test.txt");
        verify(repository, times(4)).save(any(EventLogsEntity.class));
    }

}