package com.ds.assignment.coursemanagementservice.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public Storage storage() throws IOException {
        // Load the service account key file from the classpath
        ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
        InputStream inputStream = resource.getInputStream();

        // Initialize the Storage instance using the service account key file
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        return StorageOptions.newBuilder().setCredentials(credentials).build().getService();
    }


}
