package itx.examples.blockchain.tests;

import itx.examples.blockchain.advanced.Block;
import itx.examples.blockchain.advanced.BlockChainUtils;
import itx.examples.blockchain.advanced.Ledger;
import itx.examples.blockchain.advanced.LedgerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class AdvancedBlockChainTests {

    final private static Logger LOG = LoggerFactory.getLogger(AdvancedBlockChainTests.class);

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
