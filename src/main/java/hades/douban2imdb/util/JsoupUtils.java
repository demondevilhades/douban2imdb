package hades.douban2imdb.util;

import java.io.IOException;
import java.net.Proxy;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;

import hades.douban2imdb.exception.AppException;
import hades.douban2imdb.exception.GetResponesFailedException;
import hades.douban2imdb.exception.HttpErrorException;
import hades.douban2imdb.exception.HttpForbiddenException;

public class JsoupUtils {

    public static Element getFirst(Element originElement, String cssQuery) {
        Elements elements = originElement.select(cssQuery);
        if (elements == null || elements.size() < 1) {
            throw new AppException();
        }
        return elements.get(0);
    }

    public static Response getResponse(String url, Map<String, String> headers, String userAgent, Proxy proxy,
            Method method, int maxRetryNum, Logger log) {
        int retryNum = maxRetryNum;
        Exception exp = null;
        do {
            try {
                return Jsoup.connect(url).headers(headers).userAgent(userAgent).proxy(proxy).method(method).execute();
            } catch (IOException e) {
                if (e instanceof HttpStatusException) {
                    int statusCode = ((HttpStatusException) e).getStatusCode();
                    log.info("statusCode=" + statusCode);
                    if (HttpStatus.SC_NOT_FOUND == statusCode) {
                        throw new HttpErrorException(e);
                    } else if(HttpStatus.SC_FORBIDDEN == statusCode) {
                        throw new HttpForbiddenException();
                    }
                }
                log.info("retryNum=" + --retryNum);
                if (retryNum > 0) {
                    printErrAndSleep(e, log);
                }
                exp = e;
            }
        } while (retryNum > 0);
        log.error("getResponse failed : " + url);
        throw new GetResponesFailedException(exp);
    }

    private static void printErrAndSleep(Throwable e, Logger log) {
        log.error(e.getMessage());
        SleepUtil.sleep20();
    }
}
