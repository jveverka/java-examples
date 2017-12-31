package itx.examples.blockchain.tests;

import itx.examples.blockchain.Block;
import itx.examples.blockchain.BlockChainUtils;
import itx.examples.blockchain.Ledger;
import itx.examples.blockchain.LedgerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class BlockChainTests {

    final private static Logger LOG = LoggerFactory.getLogger(BlockChainTests.class);

    @DataProvider(name = "nonceData")
    public static Object[][] getNonceData() {
        return new Object[][] {
                { null, "0" },
                { "0", "1" },
                { "a", "b" },
                { "z", "00" },
                { "az1", "az2" },
                { "acz", "ad0" },
                { "zzz", "0000" },
                { "1zzz", "2000" },

        };
    }

    @Test(dataProvider = "nonceData")
    public void testNonce(String lastNonce, String expectedNextNonce) {
        String nextNonce = BlockChainUtils.getNextNonce(lastNonce);
        Assert.assertEquals(nextNonce, expectedNextNonce);
    }

    @DataProvider(name = "sha256data")
    public static Object[][] getSha256data() {
        return new Object[][] {
                { "", "e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855" },
                { "a", "ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb" },
                { "data", "3a6eb0790f39ac87c94f3856b2dd2c5d110e6811602261a9a923d3bb23adc8b7" }
        };
    }

    @Test(dataProvider = "sha256data")
    public void testSha256(String data, String expectedHash) {
        String sha256Hash = BlockChainUtils.createSHA256Hash(data);
        Assert.assertEquals(sha256Hash, expectedHash);
    }

    @Test
    public void testLedger() {
        //1. create ledger with some data in it
        LedgerBuilder ledgerBuilder = new LedgerBuilder();
        ledgerBuilder.setId("ledger 1");
        ledgerBuilder.addData("data 1");
        ledgerBuilder.addData("data 2");
        ledgerBuilder.addData("data 3");
        ledgerBuilder.addData("data 4");
        ledgerBuilder.addData("data 5");

        Ledger ledger = ledgerBuilder.build();

        //2. verify Ledger blocks
        for (Block block: ledger.getBlocks()) {
            boolean blockOk = BlockChainUtils.verifyBlock(block);
            Assert.assertTrue(blockOk);
        }

        //3. verify Ledger block chaining
        boolean ledgerOk = BlockChainUtils.verifyLedger(ledger);
        Assert.assertTrue(ledgerOk);

        LOG.info("Ledger: {} is OK: {}", ledger.getId(), ledgerOk);
    }

}
