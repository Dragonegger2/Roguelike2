package com.sad.function.rogue.systems;

import com.badlogic.gdx.graphics.Texture;

public class AssetManager {
    private static AssetManager assetManagerInstance;

    private com.badlogic.gdx.assets.AssetManager manager;

    private AssetManager() {
        manager = new com.badlogic.gdx.assets.AssetManager();
    }

    public static AssetManager getInstance() {
        if(assetManagerInstance == null) {
            assetManagerInstance = new AssetManager();
        }

        return assetManagerInstance;
    }


    public Texture forceLoadAndReturnOfTexture(String localPath) {
        if(!manager.isLoaded(localPath)) {
            manager.load(localPath, Texture.class);
            manager.finishLoadingAsset(localPath);
        }

        return manager.get(localPath, Texture.class);
    }

    public Texture get(String localPath) {
        if(!manager.isLoaded(localPath)) {
            manager.load(localPath, Texture.class);
            manager.finishLoadingAsset(localPath);
        }
        return manager.get(localPath, Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
