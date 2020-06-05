package hades.douban2imdb.service;

import java.io.IOException;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import hades.douban2imdb.bean.MovieDouban;
import hades.douban2imdb.util.Config;
import hades.douban2imdb.util.JsoupUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Douban {

    private final String movieBaseUrl = "https://movie.douban.com";
    private final String movieWishBaseUrl = movieBaseUrl + "/people/#people#/wish";// 想读
    private final String movieCollectBaseUrl = movieBaseUrl + "/people/#people#/collect";// 读过
    private final String movieReviewsBaseUrl = movieBaseUrl + "/people/#people#/reviews";// 影评
    private final String movieDoBaseUrl = movieBaseUrl + "/people/#people#/do";// 在读

    private final String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.61 Safari/537.36";
    @SuppressWarnings("serial")
    private final Map<String, String> headers = new HashMap<String, String>() {
        {
            put("Host", "movie.douban.com");
        }
    };

    public void runMovie(String peopleId) throws IOException {
        log.info("run : peopleId = {}", peopleId);
        String movieWishUrl = movieWishBaseUrl.replace("#people#", peopleId);
        String movieCollectUrl = movieCollectBaseUrl.replace("#people#", peopleId);
        String movieDoUrl = movieDoBaseUrl.replace("#people#", peopleId);
        String movieReviewsUrl = movieReviewsBaseUrl.replace("#people#", peopleId);

        Proxy proxy = null;
        int maxRetryNum = 1;
        runMovieList(movieWishUrl, proxy, maxRetryNum);
    }

    private void runMovieList(String url, Proxy proxy, int maxRetryNum) throws IOException {
        log.info("runMovieList : url = {}", url);
        L: while (url != null) {
            Response response = JsoupUtils.getResponse(url, headers, userAgent, proxy, Method.GET, maxRetryNum, log);
            Document dom = response.parse();
            Elements infoDivs = dom.getElementById("content")
                    .select("div.grid-16-8 > div.article > div.grid-view > div.item > div.info");

            for (Element infoDiv : infoDivs) {
                Element a = infoDiv.select("ul > li.title > a").get(0);
                Elements spans = infoDiv.select("ul > li > span");
                MovieDouban movieDouban = new MovieDouban();
                movieDouban.setName(a.text());
                movieDouban.setUrl(a.attr("href"));
                for (Element span : spans) {
                    if (span.hasClass("rating1-t") || span.hasClass("rating2-t") || span.hasClass("rating3-t")
                            || span.hasClass("rating4-t") || span.hasClass("rating5-t")) {
                        movieDouban.setRating(span.className().replace("rating", "").replace("-t", ""));
                    } else if (span.hasClass("date")) {
                        movieDouban.setDate(span.text());
                    } else if (span.hasClass("tags")) {
                        movieDouban.setTags(span.text());
                    } else if (span.hasClass("comment")) {
                        movieDouban.setComment(span.text());
                    }
                }
                movieDouban.setImdbUrl(runMovieSubject(movieDouban.getUrl(), proxy, maxRetryNum));
                log.info(movieDouban.toString());
                break L;
            }
            Elements pageAs = dom.select("div.grid-16-8 > div.article > div.paginator > span.next > a");
            if (pageAs.size() > 0) {
                url = movieBaseUrl + pageAs.get(0).attr("href");
            } else {
                url = null;
            }
        }
    }

    private String runMovieSubject(String url, Proxy proxy, int maxRetryNum) throws IOException {
        log.info("runMovieSubject : url = {}", url);
        Response response = JsoupUtils.getResponse(url, headers, userAgent, proxy, Method.GET, maxRetryNum, log);
        Document dom = response.parse();
        Element infoDiv = dom.getElementById("info");
        Element a = JsoupUtils.getFirst(infoDiv, " > a");
        return a.attr("href");
    }

    public static void main(String[] args) throws Exception {
        Config.init();
        new Douban().runMovie(Config.get("douban.peopleId"));
    }
}
