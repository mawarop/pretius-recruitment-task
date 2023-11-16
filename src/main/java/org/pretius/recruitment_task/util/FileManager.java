package org.pretius.recruitment_task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.*;

public class FileManager {
    public static final String COUNT_FILENAME = "count.txt";
    private static final Logger log = LogManager.getLogger();

    public static Path createDirectory(String name) {
        Path path = Paths.get("./" + name);
        try {
            if (!Files.exists(path)) {
                return Files.createDirectories(path.toAbsolutePath().normalize());
            } else {
                log.warn("Directory " + path + " exist");
                return path;
            }
        } catch (IOException ioException) {
            log.error("Can not create" + name + "directory");
            log.error(ioException);
            throw new IllegalStateException(ioException);
        }
    }


    public static void moveFile(Path sourceFileFullPath, Path basePath, String dirName) throws IOException {
        Files.move(sourceFileFullPath, basePath.resolve(dirName + sourceFileFullPath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
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

    private static String createCountFileData(long allFilesCounter, long homeFilesCounter, long devFilesCounter, long testFilesCounter){
        String dataToWrite = "All files count: " + allFilesCounter +
                "\nHOME files count: " + homeFilesCounter +
                "\nDEV files count: " + devFilesCounter +
                "\nTEST files count: " + testFilesCounter;
        return dataToWrite;
    }
}
