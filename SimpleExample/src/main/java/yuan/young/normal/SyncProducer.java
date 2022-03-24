package yuan.young.normal;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author error-codes【BayMax】
 * @see <a href="www.error-codes.xyz">BayMax Blog</a>
 * @since 2022/3/17 16:42
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        // 创建消息生产者，并将其分配至 TongBuProducer 生产者组
        DefaultMQProducer producer = new DefaultMQProducer("TongBuProducer");

        // 设置 namesrv，集群环境下多个 namesrv 用 ； 分隔
        producer.setNamesrvAddr("www.error-codes.xyz:9876");

        // 启动
        producer.start();

        for (int i = 0; i < 100; i++) {
            // 构建消息，三个参数依次为
            // topic 主题名称
            // tags 消息TAG，用于消息过滤对消息的整体分类，比如 topic 为物流跟踪轨迹，轨迹包含揽收 出库 入库 派送 签收，可以分别给这些相同topic不同类型的数据打标签分类解析处理
            // body 消息体
            Message msg = new Message("TopicTest",
                    "TagA",
                    ("Hello RocketMQ 【" + i + "】").getBytes(RemotingHelper.DEFAULT_CHARSET));
            // 同步发送
            SendResult send = producer.send(msg, 10000);
            System.out.println(send);
        }
        producer.shutdown();
    }
}
