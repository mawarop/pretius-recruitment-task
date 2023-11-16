package org.pretius.recruitment_task.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private static final Logger log = LogManager.getLogger();
    public static Path createDirectory(String name){
        Path path = Paths.get("./" + name);
        try{
            if(!Files.exists(path)){
                return Files.createDirectories(Paths.get("./" + name).toAbsolutePath().normalize());
            } else {
                log.warn("Directory " + path.toString() + " exist");
                return path;
            }
        }catch (IOException ioException){
            log.error("Can not create" + name + "directory");
            log.error(ioException);
            throw new IllegalStateException(ioException);
        }
    }


}
