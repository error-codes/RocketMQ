package yuan.young.order;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author error-codes【BayMax】
 * @see <a href="www.error-codes.xyz">BayMax Blog</a>
 * @since 2022/3/18 15:12
 */
public class OrderedProducer {

    public static void main(String[] args) throws Exception {

        DefaultMQProducer producer = new DefaultMQProducer("ShunXuProducerGroup");

        producer.setNamesrvAddr("www.error-codes.xyz:9876");
        producer.start();
        String[] tags = {"TagA", "TagB", "TagC", "TagD", "TagE"};

        for (int i = 0; i < 100; i++) {
            int orderId = i % 10;

            Message msg = new Message("TopicTest",
                    tags[i % tags.length],
                    "KEY" + i,
                    ("Hello RocketMQ 【" + i + "】").getBytes(RemotingHelper.DEFAULT_CHARSET));

            SendResult send = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                    Integer id = (Integer) o;
                    int index = id % list.size();
                    return list.get(index);
                }
            }, orderId);

            System.out.printf("%s%n", send);
        }
        producer.shutdown();
    }
}
