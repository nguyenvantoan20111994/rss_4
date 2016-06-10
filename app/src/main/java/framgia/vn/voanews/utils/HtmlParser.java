package framgia.vn.voanews.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by nghicv on 02/06/2016.
 */
public class HtmlParser {
    public static final String CONTAINER_SELECTOR = "div.body-container";
    public static final String CONTAINER_VIDEO_SELECTOR = "p.descText";
    public static final String CONTAINER_IMAGE = "div.header-container";
    public static final String RELATED = "div.media-block-wrapper";
    public static final String REMOVE_DIV = "div.col-xs-12.col-md-2.pull-left.article-sharing";
    public static final String HTML_STYLE = "<style>img{display: inline;height: auto;max-width: 100%;}";
    public static final String REMOVE_BUTTON = "p.buttons.load-more";

    public static String parser(String url) throws IOException {
        Document document = Jsoup.connect(url).get();
        document.select(RELATED).remove();
        document.select(REMOVE_DIV).remove();
        document.select(REMOVE_BUTTON).remove();
        String title = document.title();
        Element elementContent = document.select(CONTAINER_SELECTOR).first();
        Elements elementImage = document.select(CONTAINER_IMAGE);
        Elements elementVideo = document.select(CONTAINER_VIDEO_SELECTOR);
        if (elementContent == null) {
            if (elementImage.size() == 0) {
                return "<H2>" + title + "</H2>" + elementVideo.text();
            }
            return elementImage.html() + HTML_STYLE;
        }
        return "<H2>" + title + "</H2>" + elementContent.html();
    }
}