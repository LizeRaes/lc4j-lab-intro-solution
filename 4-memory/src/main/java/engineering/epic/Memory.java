package engineering.epic;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.Tokenizer;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.StreamingChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiTokenizer;
import dev.langchain4j.model.output.Response;
import io.quarkiverse.langchain4j.ModelName;
import io.quarkiverse.langchain4j.RegisterAiService;
import io.quarkus.arc.Unremovable;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.QuarkusMain;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Singleton;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@QuarkusMain
public class Memory implements QuarkusApplication {

    private final Assistant assistant;

    public Memory(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public int run(String... args) {
        runEmptyMemory();
        // for this one we need to also uncomment the MemoryProducer class and the chat-memory related properties
//        runWithMemoryEntries();

        return 0;
    }

    private void runEmptyMemory() {
        String answer = assistant.chat("Hello! My name is Gandalf.");
        System.out.println(answer);

        String answerWithName = assistant.chat("What is my name?");
        System.out.println(answerWithName);
    }

    private void runWithMemoryEntries() {
        String answer = assistant.chat("How can your app be so slow? Please do something about it!");
        System.out.print(answer);
    }

    @RegisterAiService
    @ApplicationScoped
    interface Assistant {
        String chat(String message);
    }

//    public static class MemoryProducer {
//
//        @ApplicationScoped
//        @Unremovable
//        public Tokenizer tokenizer() {
//            return new OpenAiTokenizer("gpt-3.5-turbo");
//        }
//
//        public void chatMemory(@Observes StartupEvent startupEvent, ChatMemoryProvider chatMemoryProvider) {
//            ChatMemory chatMemory = chatMemoryProvider.get("default");
//
//            // 2. Add SystemMessage to instruct the model how to behave
//            SystemMessage systemMessage = SystemMessage.from(
//                    "You are a customer assistant for a phone manufacturer. " +
//                    "You turn feedback into tickets for the internal engineering team, and send a friendly reply to the customer. " +
//                    "If the topic is not about the phone's performance or software, you politely refuse to answer");
//            chatMemory.add(systemMessage);
//
//            // Add some few-shot examples
//            populateWithExamples(chatMemory);
//        }
//
//        private static void populateWithExamples(ChatMemory chatMemory) {
//            // Adding positive feedback example to history
//            chatMemory.add(UserMessage.from(
//                    "I love the new update! The interface is very user-friendly and the new features are amazing!"));
//            chatMemory.add(AiMessage.from(
//                    "Action: forward input to positive feedback storage\nReply: Thank you very much for this great feedback! We have transmitted your message to our product development team who will surely be very happy to hear this. We hope you continue enjoying using our product."));
//
//            // Adding negative feedback example to history
//            chatMemory.add(UserMessage
//                    .from("I am facing frequent crashes after the new update on my Android device."));
//            chatMemory.add(AiMessage.from(
//                    "Action: open new ticket - crash after update Android\nReply: We are so sorry to hear about the issues you are facing. We have reported the problem to our development team and will make sure this issue is addressed as fast as possible. We will send you an email when the fix is done, and we are always at your service for any further assistance you may need."));
//
//            // Adding another positive feedback example to history
//            chatMemory.add(UserMessage
//                    .from("Your app has made my daily tasks so much easier! Kudos to the team!"));
//            chatMemory.add(AiMessage.from(
//                    "Action: forward input to positive feedback storage\nReply: Thank you so much for your kind words! We are thrilled to hear that our app is making your daily tasks easier. Your feedback has been shared with our team. We hope you continue to enjoy using our app!"));
//
//            // Adding another negative feedback example to history
//            chatMemory.add(UserMessage
//                    .from("The new feature is not working as expected. It’s causing data loss."));
//            chatMemory.add(AiMessage.from(
//                    "Action: open new ticket - data loss by new feature\nReply:We apologize for the inconvenience caused. Your feedback is crucial to us, and we have reported this issue to our technical team. They are working on it on priority. We will keep you updated on the progress and notify you once the issue is resolved. Thank you for your patience and support."));
//        }
//    }
}
