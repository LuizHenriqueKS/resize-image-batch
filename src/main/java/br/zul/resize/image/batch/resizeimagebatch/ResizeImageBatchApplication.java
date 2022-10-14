package br.zul.resize.image.batch.resizeimagebatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.zul.resize.image.batch.resizeimagebatch.handler.ResizeImageBatchHandler;
import br.zul.resize.image.batch.resizeimagebatch.model.ResizeImageBatch;
import br.zul.resize.image.batch.resizeimagebatch.util.MyLog;

@SpringBootApplication
public class ResizeImageBatchApplication implements CommandLineRunner {

	@Autowired
	private ResizeImageBatchHandler resizeImageBatchHandler;

	public static void main(String[] args) {
		SpringApplication.run(ResizeImageBatchApplication.class, args);
	}

	public void sendHelp() {
		MyLog.log(this, "resize-image-batch.jar config.json");
	}

	@Override
	public void run(String... args) throws Exception {
		if (args.length == 0) {
			sendHelp();	
		} else {
			ResizeImageBatch command = new ResizeImageBatch(args[0]);
			resizeImageBatchHandler.handle(command);
		}
	}

}
