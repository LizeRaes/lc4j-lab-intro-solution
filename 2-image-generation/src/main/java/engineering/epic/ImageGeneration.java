package engineering.epic;

import dev.langchain4j.data.image.Image;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.output.Response;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class ImageGeneration implements QuarkusApplication {

    private final ImageModel model;

    public ImageGeneration(ImageModel model) {
        this.model = model;
    }

    @Override
    public int run(String... args) {
        Response<Image> response = model.generate(
                "Swiss software developers with cheese fondue, a parrot and a cup of coffee");

        System.out.println(response.content().url());
        return 0;
    }
}
