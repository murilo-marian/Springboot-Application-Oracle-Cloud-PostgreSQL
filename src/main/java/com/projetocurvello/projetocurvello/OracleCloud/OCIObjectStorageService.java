//package com.projetocurvello.projetocurvello.OracleCloud;
//
//import com.oracle.bmc.Region;
//import com.oracle.bmc.auth.SimpleAuthenticationDetailsProvider;
//import com.oracle.bmc.objectstorage.ObjectStorage;
//import com.oracle.bmc.objectstorage.ObjectStorageClient;
//import com.oracle.bmc.objectstorage.requests.PutObjectRequest;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//
//@Service
//public class OCIObjectStorageService {
//
//    private final ObjectStorage client;
//    private final String namespace;
//    private final String bucketName;
//    private final Region region;
//
//    public OCIObjectStorageService(
//            @Value("${oci.user-ocid}") String userOcid,
//            @Value("${oci.tenancy-ocid}") String tenancyOcid,
//            @Value("${oci.fingerprint}") String fingerprint,
//            @Value("${oci.private-key-path}") String privateKeyPath,
//            @Value("${oci.namespace}") String namespace,
//            @Value("${oci.bucket-name}") String bucketName,
//            RestTemplate restTemplate) throws IOException {
//
//        this.namespace = namespace;
//        this.bucketName = bucketName;
//
//        this.region = Region.SA_SAOPAULO_1;
//
//        SimpleAuthenticationDetailsProvider provider = SimpleAuthenticationDetailsProvider.builder()
//                .userId(userOcid)
//                .tenantId(tenancyOcid)
//                .fingerprint(fingerprint)
//                .privateKeySupplier(() -> {
//                    try {
//                        return new FileInputStream(privateKeyPath);
//                    } catch (FileNotFoundException e) {
//                        throw new RuntimeException(e);
//                    }
//                })
//                .region(region)
//                .build();
//
//        this.client = new ObjectStorageClient(provider);
//        this.client.setRegion(region);
//    }
//
//    public String uploadFile(String objectName, InputStream fileInputStream, long fileSize) {
//        PutObjectRequest request = PutObjectRequest.builder()
//                .namespaceName(namespace)
//                .bucketName(bucketName)
//                .objectName(objectName)
//                .putObjectBody(fileInputStream)
//                .contentLength(fileSize)
//                .build();
//
//        client.putObject(request);
//
//        return String.format(
//                "https://objectstorage.%s.oraclecloud.com/n/%s/b/%s/o/%s",
//                region.getRegionId(), namespace, bucketName, objectName
//        );
//    }
//}
