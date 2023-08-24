package uz.cosmos.appcontactbot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {

    private final ResourceLoader resourceLoader;

    @Autowired
    public PhotoService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public Resource readPhotoFromResources(String photoPath) {
        return resourceLoader.getResource("classpath:" + photoPath);
    }
}
