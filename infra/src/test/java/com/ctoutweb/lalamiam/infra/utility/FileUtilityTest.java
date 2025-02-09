package com.ctoutweb.lalamiam.infra.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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

  @Test
  public void getFileExtension_return_file_extension(){
    MultipartFile multipartFile = getMultipartFile();
    String extension = FileUtility.getFileExtension(multipartFile);
    Assertions.assertEquals("html", extension);
  }

  private MultipartFile getMultipartFile() {
    return new MultipartFile() {
      @Override
      public String getName() {
        return null;
      }

      @Override
      public String getOriginalFilename() {
        return "testFile.html";
      }

      @Override
      public String getContentType() {
        return null;
      }

      @Override
      public boolean isEmpty() {
        return false;
      }

      @Override
      public long getSize() {
        return 0;
      }

      @Override
      public byte[] getBytes() throws IOException {
        return new byte[0];
      }

      @Override
      public InputStream getInputStream() throws IOException {
        return null;
      }

      @Override
      public void transferTo(File dest) throws IOException, IllegalStateException {

      }
    };
  }
}
