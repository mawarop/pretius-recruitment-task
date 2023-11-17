package org.pretius.recruitment_task;

import org.junit.jupiter.api.Test;
import org.pretius.recruitment_task.util.MiscUtil;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MiscUtilTest {
    // few tests ;)

    @Test
    void Should_isEvenHour_True_When_Even() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 16, 22, 43);
        // when
        boolean result = MiscUtil.isEvenHour(localDateTime);
        // then
        assertTrue(result);
    }

    @Test
    void Should_isEvenHour_False_When_Odd() {
        // given
        LocalDateTime localDateTime = LocalDateTime.of(2023, 11, 16, 21, 43);
        // when
        boolean result = MiscUtil.isEvenHour(localDateTime);
        // then

        assertFalse(result);
    }

    @Test
    void Should_isXmlFile_True_When_Is_XML() {
        // given
        String fileExtension = "xml";
        // when
        boolean result = MiscUtil.isXmlFile(fileExtension);

        // then
        assertTrue(result);
    }

    @Test
    void Should_isXmlFile_False_When_Is_Not_XML() {
        // given
        String fileExtension = "txt";

        // when
        boolean result = MiscUtil.isXmlFile(fileExtension);

        // then
        assertFalse(result);

    }

    @Test
    void Should_isJarFile_True_When_Is_Jar() {
        // given
        String fileExtension = "jar";
        // when
        boolean result = MiscUtil.isJarFile(fileExtension);

        // then
        assertTrue(result);
    }

    @Test
    void Should_isJarFile_False_When_Is_Not_JAR() {
        // given
        String fileExtension = "txt";

        // when
        boolean result = MiscUtil.isJarFile(fileExtension);

        // then
        assertFalse(result);

    }

    @Test
    void Should_isEntryCreateEvent_True_When_Is_ENTRY_CREATE() {
        // given
        WatchEvent<Path> watchEvent = new WatchEvent<Path>() {
            @Override
            public Kind<Path> kind() {
                return StandardWatchEventKinds.ENTRY_CREATE;
            }

            @Override
            public int count() {
                return 0;
            }

            @Override
            public Path context() {
                return null;
            }
        };
        // when
        boolean result = MiscUtil.isEntryCreateEvent(watchEvent);

        // then
        assertTrue(result);
    }

    @Test
    void Should_isEntryCreateEvent_False_When_Is_Not_ENTRY_CREATE() {
        // given
        WatchEvent<Path> watchEvent = new WatchEvent<Path>() {
            @Override
            public Kind<Path> kind() {
                return StandardWatchEventKinds.ENTRY_DELETE;
            }

            @Override
            public int count() {
                return 0;
            }

            @Override
            public Path context() {
                return null;
            }
        };

        // when
        boolean result = MiscUtil.isEntryCreateEvent(watchEvent);

        // then
        assertFalse(result);
    }


}
