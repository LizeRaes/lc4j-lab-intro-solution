
import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.openai.OpenAiImageModel;
import dev.langchain4j.model.output.Response;

import java.net.URISyntaxException;
import java.nio.file.Paths;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

public class _2_ImageGeneration {

    static class createPng {

        // -----------------------
        // a. generate a png image
        // -----------------------
        // Assignment:
        //      - connect to OpenAi's Dall-E ImageModel
        //      - generate and inspect your image
        public static void main(String[] args) {

            ImageModel model = OpenAiImageModel.builder()
                    .modelName("dall-e-2")
                    .apiKey(System.getenv("OPENAI_API_KEY"))
                    .build();

            Response<Image> response = model.generate(
                    "Swiss software developers with cheese fondue, a parrot and a cup of coffee");

            System.out.println(response.content().url());
        }
    }

    static class savePngLocally {

        public static void main(String[] args) throws URISyntaxException {

            // -----------------------
            // a. handle and store a png image
            // -----------------------
            // Assignment:
            //      - connect to OpenAi's Dall-E ImageModel and set some more parameters
            //      - generate your image and persist it (use builder with .persistTo( ... ) )

            // TODO make an overview of the options in readme
            ImageModel model = OpenAiImageModel.builder()
                    .apiKey(System.getenv("OPENAI_API_KEY"))
                    .modelName("dall-e-2")
                    //.quality(DALL_E_QUALITY_HD)
                    .logRequests(true)
                    .logResponses(true)
                    .persistTo(Paths.get("src/main/resources/result-images"))
                    .build();

            Response<Image> response = model.generate("2 funny cats");


        }
    }
}
