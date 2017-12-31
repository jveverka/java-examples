# Simple BlockChain demo

This is simple block-chain demo for java, using sha256 hashing algorithm.
Inspired by [this](https://www.youtube.com/watch?v=_160oMzblY8&t=204s) video tutorial.

### Example of use
```
LedgerBuilder ledgerBuilder = new LedgerBuilder();
ledgerBuilder.setId("ledger 1");
ledgerBuilder.addData("data 1");
ledgerBuilder.addData("data 2");
ledgerBuilder.addData("data 3");
ledgerBuilder.addData("data 4");

Ledger ledger = ledgerBuilder.build();

boolean ledgerOk = BlockChainUtils.verifyLedger(ledger);
```

#### Build 
```gradle clean build```