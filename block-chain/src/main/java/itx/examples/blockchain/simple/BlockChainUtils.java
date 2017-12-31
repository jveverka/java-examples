package itx.examples.blockchain.simple;

import itx.examples.blockchain.CommonUtils;

public final class BlockChainUtils {

    private BlockChainUtils() {
    }

    public static String createSHA256Hash(Block block) {
        String blockData = block.getId() + block.getData() + block.getPreviousHash();
        return CommonUtils.createSHA256Hash(blockData);
    }

    public static String createSHA256Hash(String id, String data, String previousHash) {
        String blockData = id + data + previousHash;
        return CommonUtils.createSHA256Hash(blockData);
    }

    public static Block createGenesisBlock(String data) {
        String nextId = CommonUtils.GENESIS_BLOCK_ID;
        Block genesisBlock = new BlockBuilder()
                .setId(nextId)
                .setData(data)
                .setPreviousHash(CommonUtils.GENESIS_BLOCK_PREVIOUS_HASH)
                .build();
        return genesisBlock;
    }

    public static Block createNextBlock(Block lastBlock, String data) {
        String nextId = CommonUtils.getNextBlockId(lastBlock.getId());
        Block nextBlock = new BlockBuilder()
                .setId(nextId)
                .setData(data)
                .setPreviousHash(lastBlock.getHash())
                .build();
        return nextBlock;
    }

    public static boolean verifyBlock(Block block, Block previousBlock) {
        return (verifyBlock(block) && verifyBlock(previousBlock) && block.getPreviousHash().equals(previousBlock.getHash()));
    }

    public static boolean verifyBlock(Block block) {
        String hash = createSHA256Hash(block);
        return (hash.equals(block.getHash()));
    }

    public static boolean verifyLedger(Ledger ledger) {
        for (int i=(ledger.size()-1); i>0; i--) {
            Block previousBlock = ledger.getBlockAt(i - 1);
            Block block = ledger.getBlockAt(i);
            if (!verifyBlock(block, previousBlock)) {
                return false;
            }
        }
        return true;
    }

}
