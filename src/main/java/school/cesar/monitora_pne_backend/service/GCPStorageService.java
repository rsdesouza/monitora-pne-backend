package school.cesar.monitora_pne_backend.service;

import com.google.cloud.storage.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;

@Service
public class GCPStorageService {

    private final Storage storage;

    public GCPStorageService(Storage storage) {
        this.storage = storage;
    }

    public String readFile(String bucketName, String fileName) throws IOException {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        return new String(blob.getContent(), StandardCharsets.UTF_8);
    }

    public void writeFile(String bucketName, String fileName, String content) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, content.getBytes(StandardCharsets.UTF_8));
    }
}
