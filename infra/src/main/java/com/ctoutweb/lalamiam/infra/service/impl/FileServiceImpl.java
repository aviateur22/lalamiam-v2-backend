package com.ctoutweb.lalamiam.infra.service.impl;

import com.ctoutweb.lalamiam.infra.service.FileService;
import com.ctoutweb.lalamiam.infra.utility.TextUtility;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.*;

import static com.ctoutweb.lalamiam.infra.constant.ApplicationConstant.AWS_S3_PATH;

@Service
public class FileServiceImpl implements FileService {
  @Value("${aws.access.key}")
  private String accessKey;

  @Value("${aws.secret.key}")
  private String secretKey;

  @Value("${aws.bucket.name}")
  private String bucketName;

  @Value("${aws.region}")
  private String region;

  S3Client client;

  @PostConstruct
  public void checkCredentials() {

    var credentials = AwsBasicCredentials.create(accessKey, secretKey);
    client = S3Client
            .builder()
            .region(Region.of(region))
            .credentialsProvider(StaticCredentialsProvider.create(credentials))
            .build();
  }
  @Override
  public String uploadFile(MultipartFile file) throws IOException {
    // Génération d'un nom de fichier
    String randomFileName = TextUtility.getRandomNameUUID();

    // Flux sur le fichier
    InputStream inputStream = file.getInputStream();

    PutObjectRequest putObjectRequest = PutObjectRequest.builder().bucket(bucketName).key(randomFileName).build();
    client.putObject(putObjectRequest, RequestBody.fromInputStream(inputStream, file.getSize()));

    // Renvoie le path du fichier sur AWS
    return String.format(AWS_S3_PATH, bucketName, region, randomFileName);

  }

  @Override
  public MultipartFile getFile(String path) {
    return null;
  }

  /**
   * Convertion MultipartFile
   * @param file  MultipartFile
   * @return InputStream
   * @throws IOException
   */
  private InputStream convertMultipartFileToInputStream(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      throw new IllegalArgumentException("The uploaded file is empty");
    }
    return new ByteArrayInputStream(file.getBytes()); // No need for a physical file
  }

}
