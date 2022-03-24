package yuan.young.scheduled;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author error-codes【BayMax】
 * @see <a href="www.error-codes.xyz">BayMax Blog</a>
 * @since 2022/3/24 14:33
 */
public class ScheduledProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("ScheduledProducerGroup");

        producer.setNamesrvAddr("49.232.161.139:9876");
        producer.start();

        int totalMessageToSend = 100;
        for (int i = 0; i < totalMessageToSend; i++) {
            Message message = new Message("TestTopic",
                    ("Hello scheduled message " + i).getBytes(StandardCharsets.UTF_8));

            message.setDelayTimeLevel(3);

            producer.send(message);
        }
        producer.shutdown();
    }
}
