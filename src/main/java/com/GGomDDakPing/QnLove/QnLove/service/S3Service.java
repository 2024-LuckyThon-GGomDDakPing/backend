package com.GGomDDakPing.QnLove.QnLove.service;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {
  private final S3Client s3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  @Value("${cloud.aws.region.static}") // 지역 정보 추가
  private String region; // 지역 변수를 추가하여 S3 URL 생성에 사용

  @Autowired
  public S3Service(S3Client s3Client) {
    this.s3Client = s3Client;
  }

  /**
   * S3에 이미지 업로드 하기
   */
  public String uploadImage(MultipartFile image) throws IOException {
    String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename(); // 고유한 파일 이름 생성

    // S3에 파일 업로드 요청 생성
    PutObjectRequest putObjectRequest = PutObjectRequest.builder()
      .bucket(bucket)
      .key(fileName)
      .contentType(image.getContentType())
      .build();

    // S3에 파일 업로드
    PutObjectResponse response = s3Client.putObject(putObjectRequest, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));

    return getPublicUrl(fileName);
  }

  private String getPublicUrl(String fileName) {
    return String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, fileName);
  }
}
