package com.ctoutweb.lalamiam.infra.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.io.File;

public class FileUtilityTest {

  @Test
  public void readFile_should_return_string_from_file() {
    /**
     * given
     */
    String filePath = "testFile.html";
    String unvalidPath = "unvalid.html";
    String emptyPath = "";

    /**
     * when
     */
    String textResult1 = FileUtility.readFile(filePath);
    String textResult2 = FileUtility.readFile(unvalidPath);
    String textResult3 = FileUtility.readFile(emptyPath);

    /**
     * then
     */
    Assertions.assertNotNull(textResult1);
    Assertions.assertTrue(textResult1.length() > 10);

    Assertions.assertEquals("", textResult2);

    Assertions.assertEquals("", textResult3);
  }
}
