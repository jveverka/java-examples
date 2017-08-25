package itx.examples.rabbitmq.client1;

import itx.examples.rabbitmq.common.MessageData;
import itx.examples.rabbitmq.common.Setup;
import itx.examples.rabbitmq.common.dispatcher.MessageDispatcher;
import itx.examples.rabbitmq.common.dispatcher.MessageDispatcherImpl;
import itx.examples.rabbitmq.common.receiver.MessageReceiver;
import itx.examples.rabbitmq.common.receiver.MessageReceiverImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        final String messagePayload = "message";
        LOG.info("Rabbit MQ test started ");
        List<TestResult> testResultList = new ArrayList<>();

        MessageDispatcher messageDispatcher = new MessageDispatcherImpl(Setup.CLIENT_REQUEST_QUEUE);
        messageDispatcher.start();

        MessageListenerImpl messageListener = new MessageListenerImpl();
        MessageReceiver messageReceiver = new MessageReceiverImpl(Setup.CLIENT_RESPONSE_QUEUE, messageListener);
        messageReceiver.start();

        LOG.info("Warm-up started ...");
        for (int i=0; i<10; i++) {
            sendMessages(i,10, messagePayload, messageListener, messageDispatcher);
            Thread.sleep(1_000);
        }

        LOG.info("Testing started ...");
        Thread.sleep(5_000);
        for (int i=0; i<100; i++) {
            TestResult testResult = sendMessages(i, 10_000, messagePayload, messageListener, messageDispatcher);
            testResultList.add(testResult);
            Thread.sleep(100);
        }

        LOG.info("ALL TESTS finished !");
        printResults(testResultList);

        messageDispatcher.shutdown();
        messageReceiver.shutdown();

        LOG.info("done.");
    }

    private static TestResult sendMessages(int testOrdinal, int messageCount, String messagePayload,
                                 MessageListenerImpl messageListener,
                                 MessageDispatcher messageDispatcher) throws IOException, InterruptedException {
        long duration = System.nanoTime();
        final MessageData[] messages = new MessageData[messageCount];
        messageListener.init(messages);
        for (int i=0; i<messageCount; i++) {
            LOG.debug(" Sent [{}]", i);
            MessageData messageData = new MessageData(i, messagePayload);
            messageDispatcher.sendMessage(messageData);
        }
        messageListener.await();

        boolean testResult = true;
        for (int i=0; i<messageCount; i++) {
            if(messages[i] == null) {
                testResult = false;
            } else {
                if (i != messages[i].getContextId() || !messagePayload.equals(messages[i].getMessage())) {
                    testResult = false;
                }
            }
            messages[i] = null;
        }
        duration = System.nanoTime() - duration;
        float durationMs = duration/1000_000f;
        TestResult testResultData = new TestResult(testOrdinal, durationMs, messageCount, testResult);
        LOG.info("Send messages [{}/{}]:{} in {}ms -> {} msg/sec",
                testOrdinal, messageCount, testResult, durationMs, testResultData.getMessagesPerSecond());

        return testResultData;
    }

    private static void printResults(List<TestResult> testResultList) {
        float avgMsgsPerSec = 0;
        float minMsgPerSec = Float.MAX_VALUE;
        float maxMsgPerSec = Float.MIN_VALUE;
        boolean allOK = true;
        for (TestResult tr: testResultList) {
            LOG.info("[{}] {} msg/sec", tr.getTestOrdinal(), tr.getMessagesPerSecond());
            avgMsgsPerSec = avgMsgsPerSec + tr.getMessagesPerSecond();
            allOK = tr.isTestResult() & allOK;
            if (minMsgPerSec > tr.getMessagesPerSecond()) minMsgPerSec = tr.getMessagesPerSecond();
            if (maxMsgPerSec < tr.getMessagesPerSecond()) maxMsgPerSec = tr.getMessagesPerSecond();
        }
        LOG.info("min|average|max: {}|{}|{} msg/sec, OK={}",
                minMsgPerSec, (avgMsgsPerSec/testResultList.size()), maxMsgPerSec, allOK);
    }


}
