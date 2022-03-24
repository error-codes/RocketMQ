package yuan.young.normal;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author error-codes【BayMax】
 * @see <a href="www.error-codes.xyz">BayMax Blog</a>
 * @since 2022/3/18 14:30
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        // 创建消息生产者，并将其分配至 YiBuProducer 生产者组
        DefaultMQProducer producer = new DefaultMQProducer("YiBuProducer");

        // 设置 namesrv，集群环境下多个 namesrv 用 ； 分隔
        producer.setNamesrvAddr("www.error-codes.xyz:9876");

        // 启动
        producer.start();

        // 设置失败后重试次数
        producer.setRetryTimesWhenSendAsyncFailed(0);

        int messageCount = 100;

        final CountDownLatch countDownLatch = new CountDownLatch(messageCount);

        for (int i = 0; i < messageCount; i++) {
            try {
                final int index = i;

                // 构建消息，三个参数依次为
                // topic: 主题名称
                // tags: 消息标签
                // body: 消息体
                // keys: Message索引键，多个用空格隔开，RocketMQ可以根据这些key快速检索到消息对消息关键字的提取方便查询，比如一条消息某个关键字是运单号，之后我们可以使用这个运单号作为关键字进行查询
                Message msg = new Message("TopicDemo",
                        "TagB",
                        "Async",
                        ("Hello RocketMQ 【" + i + "】").getBytes(RemotingHelper.DEFAULT_CHARSET));

                // 异步发送消息，
                producer.send(msg, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        countDownLatch.countDown();
                        // 消息发送成功
                        System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        countDownLatch.countDown();
                        // 消息发送失败
                        System.out.printf("%-10d EXCEPTION %s %n", index, throwable);
                        throwable.printStackTrace();
                    }
                });
            } catch (UnsupportedEncodingException | MQClientException | RemotingException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatch.await(5, TimeUnit.SECONDS);
        producer.shutdown();
    }
}
