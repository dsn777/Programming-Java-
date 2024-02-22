package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class Book {
    public String urlCover;
    public String title;
    public String description;
    public String author;
    public Book(String urlCover, String title, String description, String author) {
        this.urlCover = urlCover;
        this.title = title;
        this.description = description;
        this.author = author;
    }
    @Override
    public String toString() {
        return "Адрес картинки: " + urlCover + "\n" +
                "Название: " + title + "\n" +
                "Автор: " + author + "\n" +
                "Описание: " + description + "\n";

    }
}

class HTMLParser {

    private Book element2Book (Element element) {
        String imgURL = element.select("img").attr("src");
        String title = element.select("li.title").select("a").get(0).text();
        String author = element.select("li.author").select("a").get(0).text();
        String description = element.getElementsByTag("li").get(2).text();

        return new Book(imgURL, title, author, description);
    }
    public List<Book> parseHTML(String url) {
        try {
            Document document = Jsoup.connect(url).get();
            Elements neededElements = new Elements();
            Elements elem = document.select("div.items");
            Element childElement = elem.get(0);

            //Получение первых 3х, нужных нам элементов (книг)
            for (int i = 0, amount = 0; amount < 3; i++) {
                Element element = childElement.child(i);
                if (!element.hasText())
                    continue;

                neededElements.add(element);
                amount++;
            }

            //Получение списка первых 3x книг, упакованных в класс Book
            List<Book> books = new ArrayList<>();
            for (Element element : neededElements) {
                books.add(element2Book(element));
            }
            return books;
        } catch (IOException exception) {
            System.out.println("IOException");
        }
        return null;
    }
}

public class Main {

    public static void main(String[] args) {
        HTMLParser parser = new HTMLParser();
        List<Book> top3books = parser.parseHTML("https://bookscafe.net/genre/bankovskoe_delo.html");

        //Вывод списка первых 3х книг
        for (Book book : top3books) {
            System.out.println(book);
        }
    }
}