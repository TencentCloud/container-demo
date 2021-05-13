package demo.tcloud.triblewood.qcbm.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages= "demo.tcloud.triblewood.qcbm.web")
public class QcbmGateway {

	public static void main(String[] args) {
		SpringApplication.run(QcbmGateway.class, args);
	}
}
