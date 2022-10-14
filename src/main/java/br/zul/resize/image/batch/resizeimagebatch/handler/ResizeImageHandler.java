package br.zul.resize.image.batch.resizeimagebatch.handler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Component;

import br.zul.resize.image.batch.resizeimagebatch.exception.ImageReadException;
import br.zul.resize.image.batch.resizeimagebatch.exception.InterruptedExecutationException;
import br.zul.resize.image.batch.resizeimagebatch.model.ResizeImage;
import br.zul.resize.image.batch.resizeimagebatch.util.MyLog;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class ResizeImageHandler {
    
    public void handle(ResizeImage command) {
        File srcFile = new File(command.getSrcImageFile());
        File destFile = new File(command.getDestImageFile());
        try {
            BufferedImage srcImage = loadImage(srcFile);
            if (srcImage.getWidth() == command.getWidth() && srcImage.getHeight() == command.getHeight()) {
                copyFile(srcFile, destFile);
            } else {    
                srcImage = resizeSrcImage(srcImage, command.getWidth(), command.getHeight());
                BufferedImage destImage = createNewImage(command.getWidth(), command.getHeight());
                fillImageInCenter(destImage, srcImage);
                saveImage(destFile, destImage);
            }
        } catch (ImageReadException ex) {
            MyLog.error(this, ex.getMessage());
            log.error(ex);
        }
    }

    private void copyFile(File srcFile, File destFile) {
        try {
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            MyLog.error(this, "Cannot write image: " + destFile.getAbsolutePath());
            throw new InterruptedExecutationException(e);
        }
    }

    private BufferedImage resizeSrcImage(BufferedImage srcImage, int width, int height) {
        if (srcImage.getWidth() > width || srcImage.getHeight() > height) {
            return Scalr.resize(srcImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, width, height, Scalr.OP_ANTIALIAS);
        } else {
            return srcImage;
        }
    }

    private void fillImageInCenter(BufferedImage destImage, BufferedImage srcImage) {
        Graphics2D tGraphics2D = destImage.createGraphics(); //create a graphics object to paint to
        
        tGraphics2D.setBackground( Color.WHITE );
        tGraphics2D.setPaint( Color.WHITE );
        tGraphics2D.fillRect( 0, 0, destImage.getWidth(), destImage.getHeight() );

        tGraphics2D.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );

        int x = (destImage.getWidth() - srcImage.getWidth()) / 2;
        int y = (destImage.getHeight() - srcImage.getHeight()) / 2;

        tGraphics2D.drawImage( srcImage, x, y, srcImage.getWidth(), srcImage.getHeight(), null );

    }

    private void saveImage(File destFile, BufferedImage image) {
        try {
            String type = destFile.getName().toLowerCase().endsWith(".png") ? "PNG" : "JPEG";
            ImageIO.write(image, type, destFile);
        } catch (IOException ex) {
            MyLog.error(this, "Cannot write image: " + destFile.getAbsolutePath());
            throw new InterruptedExecutationException(ex);
        }
    }

    private BufferedImage createNewImage(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        return image;
    }

    public BufferedImage loadImage(File srcImage) {
        try {
            return ImageIO.read(srcImage);
        } catch (Exception ex){
            String msg = "Cannot read image: " + srcImage.getAbsolutePath();
            MyLog.error(this, msg);
            throw new ImageReadException(ex, msg);
        }
    }

}
