package yuan.young.test;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author error-codes【BayMax】
 * @see <a href="www.error-codes.xyz">BayMax Blog</a>
 * @since 2022/3/25 11:12
 */
public class TestConsumer01 {

    public static void main(String[] args) throws Exception {
        DefaultLitePullConsumer consumerGroup01 = new DefaultLitePullConsumer("TestConsumerGroup01");

        consumerGroup01.setNamesrvAddr("124.221.180.113:9876");

        consumerGroup01.start();


    }
}
