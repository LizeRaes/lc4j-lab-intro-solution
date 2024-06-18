package engineering.epic;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.output.Response;
import io.quarkiverse.langchain4j.ModelName;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@QuarkusMain
public class TextGeneration implements QuarkusApplication {

    private final ChatLanguageModel modelA;
    private final ChatLanguageModel modelB;
    private final StreamingChatLanguageModel modelC;

    public TextGeneration(ChatLanguageModel modelA,
                          @ModelName("modelb") ChatLanguageModel modelB,
                          StreamingChatLanguageModel modelC) {
        this.modelA = modelA;
        this.modelB = modelB;
        this.modelC = modelC;
    }

    @Override
    public int run(String... args) {
        String answerA = modelA.generate("Say Hello World");
        System.out.println("Answer A: " + answerA);

        String answerB = modelB.generate("Name five words that developers hate to hear most");
        System.out.println("Answer B: " + answerB);

        System.out.println("Answer C: ");

        CountDownLatch latch = new CountDownLatch(1);
        modelC.generate("Write a poem about unicorns and grizzly bears"
                , new StreamingResponseHandler<>() {
                    @Override
                    public void onNext(String s) {
                        System.out.println(s);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        System.out.println("Whoopsie!");
                        latch.countDown();
                    }

                    @Override
                    public void onComplete(Response<AiMessage> response) {
                        latch.countDown();
                    }
                });

        try {
            latch.await(60, TimeUnit.SECONDS);
            return 0;
        } catch (InterruptedException e) {
            return 1;
        }
    }
}
