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

public class SegregateApp {
    private static final Logger log = LogManager.getLogger();
    public static final String COUNT_FILENAME = "count.txt";

    public static void main(String[] args) {
        Path homeDir = FileManager.createDirectory("HOME");
        Path devDir = FileManager.createDirectory("DEV");
        Path testDir = FileManager.createDirectory("TEST");

        try (WatchService watchService = FileSystems.getDefault().newWatchService()) {
            homeDir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE);

            WatchKey key;
            long allFilesCounter = 0;
            long homeFilesCounter = 0;
            long devFilesCounter = 0;
            long testFilesCounter = 0;


            createCountFile(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);

            while ((key = watchService.take()) != null) {
                for (WatchEvent<?> event : key.pollEvents()) {

                    Path sourceFileDir = (Path) key.watchable();
                    Path sourceFileFullPath = sourceFileDir.resolve((Path) event.context());

                    Path basePath = Paths.get(".").toAbsolutePath().normalize();
                    String fileExtension = FilenameUtils.getExtension(sourceFileFullPath.toString());
                    if (event.kind().equals(StandardWatchEventKinds.ENTRY_CREATE)) {
                        if (fileExtension.equals("jar")) {
                            try {
                                FileTime creationTime = (FileTime) Files.getAttribute(sourceFileFullPath, "creationTime");
                                LocalDateTime ldtCreationTime = LocalDateTime.ofInstant(creationTime.toInstant(), ZoneId.systemDefault());
                                if (ldtCreationTime.getHour() % 2 == 0) {
                                    Files.move(sourceFileFullPath, basePath.resolve("DEV/" + sourceFileFullPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);

                                    devFilesCounter++;

                                } else {
                                    Files.move(sourceFileFullPath, basePath.resolve("TEST/" + sourceFileFullPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                                    testFilesCounter++;
                                }
                            } catch (IOException ioException) {
                                log.error(ioException);
                                throw new IllegalStateException(ioException);
                            }
                            allFilesCounter++;

                        } else if (fileExtension.equals("xml")) {
                            Files.move(sourceFileFullPath, basePath.resolve("DEV/" + sourceFileFullPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
                            devFilesCounter++;
                            allFilesCounter++;

                        }
                    }

                }
                createCountFile(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);
                key.reset();
            }
        } catch (IOException | InterruptedException exception) {
            log.error(exception);
            throw new IllegalStateException(exception);
        }

    }
    public static void createCountFile(long allFilesCounter, long homeFilesCounter, long devFilesCounter, long testFilesCounter) {
        String countFileData = createCountFileData(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);
        Path countFilePath = Paths.get(COUNT_FILENAME);

        try {
            if (Files.exists(countFilePath)) {
                Files.delete(countFilePath);
                Files.write(countFilePath, countFileData.getBytes(), StandardOpenOption.CREATE_NEW);
            }
            Files.write(countFilePath, countFileData.getBytes(), StandardOpenOption.CREATE_NEW);
        } catch (IOException ioException) {
            log.warn(ioException);
            throw new IllegalStateException(ioException);
        }
    }

    public static String createCountFileData(long allFilesCounter, long homeFilesCounter, long devFilesCounter, long testFilesCounter){
        String dataToWrite = "All files count: " + allFilesCounter +
                "\nHOME files count: " + homeFilesCounter +
                "\nDEV files count: " + devFilesCounter +
                "\nTEST files count: " + testFilesCounter;
        return dataToWrite;
    }

}