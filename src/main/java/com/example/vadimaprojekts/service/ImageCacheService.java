package com.example.vadimaprojekts.service;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

public class ImageCacheService {
    private Map<String, Image> imageCache = new HashMap<>();

    // Preload and store image
    public void preloadImage(String imageUrl) {
        if (!imageCache.containsKey(imageUrl)) {
            imageCache.put(imageUrl, new Image(imageUrl, true)); // true = load in background
        }
    }

    // Get cached image
    public Image getImage(String imageUrl) {
        return imageCache.get(imageUrl);
    }
}
