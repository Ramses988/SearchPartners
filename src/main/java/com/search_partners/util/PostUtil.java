package com.search_partners.util;


import com.search_partners.model.Post;
import com.search_partners.to.PostDto;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class PostUtil {

    private static final Map<String, String> characters = new HashMap<>();

    private PostUtil() {}

    static {
        initCharacters();
    }

    public static Post getPost(Long id) {
        Post post = new Post();
        post.setId(id);
        return post;
    }

    public static Post createNewFromTo(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .date(LocalDateTime.now())
                .build();
    }

    public static List<Post> prepareText(List<Post> posts) {
        for (Post post : posts) {
            String text = Jsoup.parse(post.getText()).text();
            if (text.length() > 200)
                text = text.substring(0, 200) + "...";
            post.setText(text);
        }
        return posts;
    }

    public static String validateText(String text) {
        for (Map.Entry<String, String> symbol : characters.entrySet()) {
            text = text.replace(symbol.getKey(), symbol.getValue());
        }
        if (text.contains("<") || text.contains(">") || text.contains("/") || text.contains("'")) {
            log.warn("Text not validate: " + text);
            text = text.replace("<", "&lt;");
            text = text.replace(">", "&gt;");
            text = text.replace("/", "&frasl;");
            text = text.replace("'", "&prime;");
        }

        for (Map.Entry<String, String> symbol : characters.entrySet()) {
            text = text.replace(symbol.getValue(), symbol.getKey());
        }
        return text;
    }

    private static void initCharacters() {
        characters.put("<br />", "[.br]");
        characters.put("<p>", "[p]");
        characters.put("</p>", "[.p]");
        characters.put("<strong>", "[strong]");
        characters.put("</strong>", "[.strong]");
        characters.put("<em>", "[em]");
        characters.put("</em>", "[.em]");
        characters.put("<s>", "[s]");
        characters.put("</s>", "[.s]");
        characters.put("<ol>", "[ol]");
        characters.put("</ol>", "[.ol]");
        characters.put("<ul>", "[ul]");
        characters.put("</ul>", "[.ul]");
        characters.put("<li>", "[li]");
        characters.put("</li>", "[.li]");
        characters.put("<h1>", "[h1]");
        characters.put("<h2>", "[h2]");
        characters.put("<h3>", "[h3]");
        characters.put("</h1>", "[.h1]");
        characters.put("</h2>", "[.h2]");
        characters.put("</h3>", "[.h3]");
        characters.put("<hr />", "[hr]");
        characters.put("<td>", "[td]");
        characters.put("</td>", "[.td]");
        characters.put("<tr>", "[tr]");
        characters.put("</tr>", "[.tr]");
        characters.put("<tbody>", "[tbody]");
        characters.put("</tbody>", "[.tbody]");
        characters.put("</table>", "[.table]");
        characters.put("<big>", "[big]");
        characters.put("</big>", "[.big]");
        characters.put("<small>", "[small]");
        characters.put("</small>", "[.small]");
        characters.put("<code>", "[code]");
        characters.put("</code>", "[.code]");
        characters.put("<tt>", "[tt]");
        characters.put("</tt>", "[.tt]");
        characters.put("</span>", "[.span]");
        characters.put("</a>", "[.a]");
        characters.put("<blockquote>", "[blockquote]");
        characters.put("</blockquote>", "[.blockquote]");
        characters.put("<q>", "[q]");
        characters.put("</q>", "[.q]");
        characters.put("<pre>", "[pre]");
        characters.put("</pre>", "[.pre]");
        characters.put("<kbd>", "[kbd]");
        characters.put("</kbd>", "[.kbd]");
        characters.put("<ins>", "[ins]");
        characters.put("</ins>", "[.ins]");
        characters.put("<h2 style=", "[h2 s");
        characters.put("<h3 style=", "[h3 s");
        characters.put("<div style=", "[div s");
        characters.put("</div>", "[.div]");
        characters.put("\">", ".]");
        characters.put("<table ", "[table ");
        characters.put("<span ", "[span ");
        characters.put("<a href=", "[a");
    }

}