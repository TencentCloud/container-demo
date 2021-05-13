package demo.tcloud.triblewood.qcbm.web.vo;

import demo.tcloud.triblewood.qcbm.common.BookInfoDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;


@Data
@NoArgsConstructor
public class BookInfoVO {

    public static final FastDateFormat DF = FastDateFormat.getInstance("yyyy-MM");

    private Long isbn;
    private String title;
    private String author;
    private String press;
    private String pubDate;
    private Float price;
    private String briefIntro;
    private String imgUrl;


    public BookInfoVO(BookInfoDto dto) {

        this.isbn = dto.getIsbn();
        this.title = dto.getTitle();
        this.author = dto.getAuthor();
        this.press = dto.getPress();
        this.price = dto.getPrice();
        this.briefIntro = dto.getBriefIntro();
        this.pubDate = DF.format(dto.getPublishDate());
        this.imgUrl = dto.getImgUrl();
    }
}
