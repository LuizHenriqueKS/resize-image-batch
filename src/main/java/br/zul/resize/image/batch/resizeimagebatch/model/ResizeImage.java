package br.zul.resize.image.batch.resizeimagebatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResizeImage {

    private String srcImageFile;
    private String destImageFile;
    private int width;
    private int height;

}
