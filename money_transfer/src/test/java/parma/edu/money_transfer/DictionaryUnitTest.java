package parma.edu.money_transfer;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import parma.edu.money_transfer.service.DictionaryService;

@Tag("dictionaries")
public class DictionaryUnitTest extends AbstractTest {

    @Autowired
    private DictionaryService dictionaryService;

    @Test
    @DisplayName("Call twice dictionary method but have only one log record")
    public void givenDictionaryService_cacheWorkAfterAnotherCall() {
        final int callsNumber = 3;
        final int logMessages = 2;
        final String logDbMessage = "Getting entities from database";

        // получаем Logback Logger
        Logger fooLogger = (Logger) LoggerFactory.getLogger(DictionaryService.class);

        // создаем и стартуем ListAppender
        ListAppender<ILoggingEvent> listAppender = new ListAppender<>();
        listAppender.start();

        // добавляем аппендер к логгеру
        fooLogger.addAppender(listAppender);

        for (int i = 0; i < callsNumber; i++) {
            dictionaryService.getAllTypes();
            dictionaryService.getAllStatuses();
        }

        Assertions.assertNotNull(listAppender.list);
        Assertions.assertEquals(2, listAppender.list.size());
        for (int i = 0; i < logMessages; i++) {
            Assertions.assertEquals(logDbMessage, listAppender.list.get(i).getMessage());
        }
    }
}