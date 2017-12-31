package itx.examples.blockchain.advanced;

import itx.examples.blockchain.CommonUtils;

public final class BlockChainUtils {

    private BlockChainUtils() {
    }

    public static final String HASH_PREFIX = "01";

    public static String createSHA256Hash(Block block) {
        String blockData = block.getId() + block.getNonce() + block.getData() + block.getPreviousHash();
        return CommonUtils.createSHA256Hash(blockData);
    }

    public static String createSHA256Hash(String id, String nonce, String data, String previousHash) {
        String blockData = id + nonce + data + previousHash;
        return CommonUtils.createSHA256Hash(blockData);
    }

    public static boolean isValid(Block block) {
        return block.getHash().startsWith(HASH_PREFIX);
    }

    public static Block createGenesisBlock(String data) {
        String nextId = CommonUtils.GENESIS_BLOCK_ID;
        String nonce = CommonUtils.getNextNonce(null);
        Block genesisBlock = new BlockBuilder()
                .setId(nextId)
                .setNonce(nonce)
                .setData(data)
                .setPreviousHash(CommonUtils.GENESIS_BLOCK_PREVIOUS_HASH)
                .build();
        while (true) {
            if (isValid(genesisBlock)) break;
            nonce = CommonUtils.getNextNonce(nonce);
            genesisBlock = new BlockBuilder()
                    .from(genesisBlock)
                    .setNonce(nonce)
                    .build();
        }
        return genesisBlock;
    }

    public static Block mineNextBlock(Block lastBlock, String data) {
        String nextId = CommonUtils.getNextBlockId(lastBlock.getId());
        String nonce = CommonUtils.getNextNonce(null);
        Block nextBlock = new BlockBuilder()
                .setId(nextId)
                .setNonce(nonce)
                .setData(data)
                .setPreviousHash(lastBlock.getHash())
                .build();
        while (true) {
            if (isValid(nextBlock)) break;
            nonce = CommonUtils.getNextNonce(nonce);
            nextBlock = new BlockBuilder()
                    .from(nextBlock)
                    .setNonce(nonce)
                    .build();
        }
        return nextBlock;
    }

    public static boolean verifyBlock(Block block, Block previousBlock) {
        return (verifyBlock(block) && verifyBlock(previousBlock) && block.getPreviousHash().equals(previousBlock.getHash()));
    }

    public static boolean verifyBlock(Block block) {
        String hash = createSHA256Hash(block);
        return (hash.equals(block.getHash()) && isValid(block));
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
