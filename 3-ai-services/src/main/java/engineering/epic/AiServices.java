package engineering.epic;

import dev.langchain4j.model.output.structured.Description;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.util.List;

@QuarkusMain
public class AiServices implements QuarkusApplication {

    private final Assistant assistant;
    private final TextUtils textUtils;
    private final Chef chef;

    public AiServices(Assistant assistant, TextUtils textUtils, Chef chef) {
        this.assistant = assistant;
        this.textUtils = textUtils;
        this.chef = chef;
    }

    @Override
    public int run(String... args) {
//        runAssistant();
//        runTextUtils();
        runChef();
        return 0;
    }

    private void runAssistant() {
        String userMessage = "Translate 'Plus-Values des cessions de valeurs mobilières, de droits sociaux et gains assimilés'";
        String answer = assistant.chat(userMessage);
        System.out.println(answer);
    }

    private void runTextUtils() {
        String translation = textUtils.translate("Hello, how are you?", "italian");
        System.out.println(translation); // Ciao, come stai?

        String text = "AI, or artificial intelligence, is a branch of computer science that aims to create "
                      + "machines that mimic human intelligence. This can range from simple tasks such as recognizing "
                      + "patterns or speech to more complex tasks like making decisions or predictions.";

        // Try out summarizer
        List<String> bulletPoints = textUtils.summarize(text, 3);
        bulletPoints.forEach(System.out::println);

        // Try out DateTime extractor
        text = "The tranquility pervaded the evening of 1968, just fifteen minutes shy of midnight,"
               + " following the celebrations of Independence Day.";
        LocalDateTime dateTime = textUtils.extractDateTimeFrom(text);
        System.out.println(dateTime); // 1968-07-04T23:45
    }

    private void runChef() {
        Recipe recipe = chef.createRecipeFrom("cucumber", "tomato", "frietsaus", "onion", "olives", "lemon");
        System.out.println(recipe);
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {
        String chat(String message);
    }

    @RegisterAiService
    @ApplicationScoped
    interface TextUtils {

        @SystemMessage("You are a professional translator into {{language}}")
        @UserMessage("Translate the following text: {{text}}")
        String translate(String text, String language);

        @SystemMessage("Summarize every message from the user in {{n}} bullet points. Provide only bullet points.")
        List<String> summarize(@UserMessage String text, int n);

        @UserMessage("Extract date and time from {{it}}")
        LocalDateTime extractDateTimeFrom(String text);
    }

    static class Recipe {

        @Description("short title, 3 words maximum")
        private String title;

        @Description("short description, 2 sentences maximum")
        private String description;

        @Description("each step should be described in 6 to 8 words, steps should rhyme with each other")
        private List<String> steps;

        private Integer preparationTimeMinutes;

        @Override
        public String toString() {
            return "Recipe {" +
                   " title = \"" + title + "\"" +
                   ", description = \"" + description + "\"" +
                   ", steps = " + steps +
                   ", preparationTimeMinutes = " + preparationTimeMinutes +
                   " }";
        }
    }

    // 2. AIService interface declaration
    @RegisterAiService
    @ApplicationScoped
    interface Chef {
        Recipe createRecipeFrom(String... ingredients);
    }
}
