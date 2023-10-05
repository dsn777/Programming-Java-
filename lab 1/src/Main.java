import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.awt.Desktop;
import java.net.URI;

public class Main {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Enter a request");
            String SearchRequest = reader.readLine();

            WikiSearch wikiSearch = new WikiSearch();
            URI uri = wikiSearch.Search(SearchRequest).toURI();
            Desktop.getDesktop().browse(uri);
        } catch (Exception e) {
            System.out.println("0_o");
        }
    }
}
