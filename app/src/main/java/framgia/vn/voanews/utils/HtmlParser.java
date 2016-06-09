package framgia.vn.voanews.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by nghicv on 02/06/2016.
 */
public class HtmlParser {
    public static final String CONTAINER_SELECTOR = "div.body-container";
    public static final String TITLE_SELECTOR = "col-title";
    public static final String CONTAINER_VIDEO_SELECTOR = "div[class=intro content-offset]";
    private static final String RELATED = "div.media-block-wrapper";

    public static String parser(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        document.select(RELATED).remove();
        Element elementTitle = document.getElementsByClass(TITLE_SELECTOR).first();
        Element elementContent = document.select(CONTAINER_SELECTOR).first();
        if (elementContent == null)
            elementContent = document.select(CONTAINER_VIDEO_SELECTOR).first();
        return elementTitle.html() + elementContent.html();
    }
}
