package org.pretius.recruitment_task;


import org.junit.jupiter.api.Test;
import org.pretius.recruitment_task.util.FileManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileManagerTest {
    // few tests ;)

    @Test
    void Should_CreateDirectory_Create_And_Return_Dir_Path_When_Dir_Not_Exist() throws IOException {
        // given
        String dirName = "TEMP";
        Path path = Paths.get("./" + dirName).toAbsolutePath().normalize();

        // when
        Path result = FileManager.createDirectory(dirName);

        // then
        assertTrue(Files.exists(path));
        assertThat(path, equalTo(result));
        // cleaning
        Files.delete(result);
    }


    @Test
    void Should_CreateCountFileData_Create_And_Return_Correct_Data() {
        // given

        long allFilesCounter = 8;
        long homeFilesCounter = 0;
        long devFilesCounter = 3;
        long testFilesCounter = 5;
        String data = "All files count: " + allFilesCounter +
                "\nHOME files count: " + homeFilesCounter +
                "\nDEV files count: " + devFilesCounter +
                "\nTEST files count: " + testFilesCounter;

        // when
        var result = FileManager.createCountFileData(allFilesCounter, homeFilesCounter, devFilesCounter, testFilesCounter);

        // then
        assertThat(result, equalTo(data));

    }


}
