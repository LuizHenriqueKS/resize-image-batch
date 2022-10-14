package br.zul.resize.image.batch.resizeimagebatch.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Config {
    
    private String sourceFolder;
    private String destFolder;
    private Integer destWidth;
    private Integer destHeight;

}
