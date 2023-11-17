package org.pretius.recruitment_task;

import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.pretius.recruitment_task.util.FileManager;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.pretius.recruitment_task.util.MiscUtil.*;

public class SegregateApp {
    private static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        Path homeDir = FileManager.createDirectory("HOME");

        FileManager.createDirectory("DEV");
        FileManager.createDirectory("TEST");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            registerWatcherForDir(homeDir, watchService);

            long allFilesCounter = 0;
            long homeFilesCounter = 0;
            long devFilesCounter = 0;
            long testFilesCounter = 0;

            FileManager.createCountFile(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);
            startWatchingFilesEvents(watchService, devFilesCounter, testFilesCounter, allFilesCounter, homeFilesCounter);

        } catch (IOException | InterruptedException exception) {
            log.error(exception);
            throw new IllegalStateException(exception);
        }

    }

    private static void registerWatcherForDir(Path dir, WatchService watchService) throws IOException {
        dir.register(watchService,
                StandardWatchEventKinds.ENTRY_CREATE);
    }

    private static void startWatchingFilesEvents(WatchService watchService, long devFilesCounter, long testFilesCounter, long allFilesCounter, long homeFilesCounter) throws InterruptedException, IOException {
        WatchKey key;
        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {

                Path sourceFileDir = (Path) key.watchable();
                Path sourceFileFullPath = sourceFileDir.resolve((Path) event.context());
                Path basePath = Paths.get(".").toAbsolutePath().normalize();
                String fileExtension = FilenameUtils.getExtension(sourceFileFullPath.toString());

                if (isEntryCreateEvent(event)) {
                    if (isJarFile(fileExtension)) {
                        try {
                            FileTime creationTime = (FileTime) Files.getAttribute(sourceFileFullPath, "creationTime");
                            LocalDateTime ldtCreationTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());

                            if (isEvenHour(ldtCreationTime)) {
                                FileManager.moveFile(sourceFileFullPath, basePath, "DEV/");
                                devFilesCounter++;

                            } else {
                                FileManager.moveFile(sourceFileFullPath, basePath, "TEST/");
                                testFilesCounter++;
                            }
                        } catch (IOException ioException) {
                            log.error(ioException);
                            throw new IllegalStateException(ioException);
                        }
                        allFilesCounter++;

                    } else if (isXmlFile(fileExtension)) {
                        FileManager.moveFile(sourceFileFullPath, basePath, "DEV/");
                        devFilesCounter++;
                        allFilesCounter++;

                    }
                }
                FileManager.createCountFile(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);
            }
            key.reset();
        }
    }

}