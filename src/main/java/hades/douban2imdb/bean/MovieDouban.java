package hades.douban2imdb.bean;

import lombok.Data;

@Data
public class MovieDouban {
    private String name;
    private String url;
    private String rating;
    private String date;
    private String tags;
    private String comment;
    private String imdbUrl;
}
