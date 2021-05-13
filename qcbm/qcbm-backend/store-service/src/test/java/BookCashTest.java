import com.alibaba.fastjson.JSON;
import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

public class BookCashTest {

    public static void main(String[] args) throws Exception {

        Jedis jedis = new Jedis("127.0.0.1", 6379);

        BookInfoDto book1 = new BookInfoDto();
        book1.setIsbn(11111L);
        book1.setAuthor("John");
        book1.setTitle("Spring in Action");

        BookInfoDto book2 = new BookInfoDto();
        book2.setIsbn(22222L);
        book2.setAuthor("Tom");
        book2.setTitle("Tomcat in Action");

        BookInfoDto book3 = new BookInfoDto();
        book3.setIsbn(33333L);
        book3.setAuthor("K8S");
        book3.setTitle("Kubernetes in Action");

        Map<String, String> bookMap = new HashMap<>();
        bookMap.put(book1.getIsbn().toString(), JSON.toJSONString(book1));
        bookMap.put(book2.getIsbn().toString(), JSON.toJSONString(book2));

        jedis.hmset("books", bookMap);

        jedis.hset("books", book3.getIsbn().toString(), JSON.toJSONString(book3));
        bookMap = jedis.hgetAll("books");
        System.out.println(JSON.toJSONString(bookMap));

        jedis.hdel("books", book2.getIsbn().toString());
        bookMap = jedis.hgetAll("books");
        System.out.println(JSON.toJSONString(bookMap));
    }
}
