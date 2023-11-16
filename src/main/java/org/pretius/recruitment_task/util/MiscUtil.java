package org.pretius.recruitment_task.util;

import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.time.LocalDateTime;

public class MiscUtil {
    public static boolean isEvenHour(LocalDateTime ldtCreationTime) {
        return ldtCreationTime.getHour() % 2 == 0;
    }

    public static boolean isXmlFile(String fileExtension) {
        return fileExtension.equals("xml");
    }

    public static boolean isJarFile(String fileExtension) {
        return fileExtension.equals("jar");
    }

    public static boolean isEntryCreateEvent(WatchEvent<?> event) {
        return event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE);
    }
}
