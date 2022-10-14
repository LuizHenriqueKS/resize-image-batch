package br.zul.resize.image.batch.resizeimagebatch.handler;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.zul.resize.image.batch.resizeimagebatch.model.Config;
import br.zul.resize.image.batch.resizeimagebatch.model.ResizeImage;
import br.zul.resize.image.batch.resizeimagebatch.model.ResizeImageBatch;
import br.zul.resize.image.batch.resizeimagebatch.util.JsonUtils;
import br.zul.resize.image.batch.resizeimagebatch.util.MyLog;

@Component
public class ResizeImageBatchHandler {

    @Autowired
    private ResizeImageHandler resizeImageHandler;
    
    public void handle(ResizeImageBatch command) {
        File configFile = new File(command.getJsonFile());
        Config config = JsonUtils.readFile(configFile, Config.class);
        createFolderItNotExists(config.getDestFolder());
        MyLog.log(this, "Loading file list...");
        File[] files = new File(config.getSourceFolder()).listFiles();
        for (int i = 0; i < files.length; i++) {
            File file = files[i];
            if (file.exists()) {
                File destImageFile = getDestImageFile(config.getDestFolder(), file.getName());
                ResizeImage resizeImage = ResizeImage.builder()
                    .srcImageFile(file.getAbsolutePath())
                    .destImageFile(destImageFile.getAbsolutePath())
                    .width(config.getDestWidth())
                    .height(config.getDestHeight())
                    .build();
                resizeImageHandler.handle(resizeImage);
            }
            MyLog.logsf(this, "Progress %d/%d...", i + 1, files.length);
        }
    }

    private void createFolderItNotExists(String destFolder) {
        File destFolderFile = new File(destFolder);
        if (!destFolderFile.exists()) {
            MyLog.log(this, "Creating destination folder...");
            destFolderFile.mkdirs();
        }
    }

    private File getDestImageFile(String destFolder, String filename) {
        return new File(destFolder, filename);
    }

}
