import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FlagDownloader {

    public static void download(String code3, String code2) throws Exception {
        String url = "https://flagcdn.com/w80/" + code2 + ".png";

        // create folder if it doesn't exist
        Path folder = Path.of("resources/flags");
        Files.createDirectories(folder);

        // output file
        Path output = folder.resolve(code3 + ".png");

        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, output, StandardCopyOption.REPLACE_EXISTING);
        }
    }
}