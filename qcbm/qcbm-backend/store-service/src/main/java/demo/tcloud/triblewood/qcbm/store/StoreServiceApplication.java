package demo.tcloud.triblewood.qcbm.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("demo.tcloud.triblewood.qcbm.store.persist.dao")
@SpringBootApplication
public class StoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoreServiceApplication.class, args);
	}
}
