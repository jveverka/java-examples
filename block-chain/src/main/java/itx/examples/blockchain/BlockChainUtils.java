package itx.examples.blockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class BlockChainUtils {

    private BlockChainUtils() {
    }

    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvxywz";
    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            digest = null;
        }
    }

    public static final String HASH_PREFIX = "01";
    public static final String GENESIS_BLOCK_ID = "0";
    public static final String GENESIS_BLOCK_PREVIOUS_HASH = "0000000000000000000000000000000000000000000000000000000000000000";

    public static String getNextBlockId(String lastBlockId) {
        Long id = Long.parseLong(lastBlockId) + 1;
        return id.toString();
    }

    public static String getNextNonce(String lastNonce) {
        if (lastNonce == null) return String.valueOf(ALPHABET.charAt(0));
        int maxIndex = ALPHABET.length() - 1;
        StringBuilder nextNonce = new StringBuilder(lastNonce);
        int remainder = 0;
        for (int i=(lastNonce.length()-1); i>=0; i--) {
            int nextIndex = ALPHABET.indexOf(lastNonce.charAt(i)) + 1;
            if (nextIndex <= maxIndex) {
                nextNonce.setCharAt(i, ALPHABET.charAt(nextIndex));
                remainder = 0;
                break;
            } else {
                nextNonce.setCharAt(i, ALPHABET.charAt(0));
                remainder = 1;
            }
        }
        if (remainder != 0) {
            return ALPHABET.charAt(0) + nextNonce.toString();
        } else {
            return nextNonce.toString();
        }
    }

    public static String createSHA256Hash(String data) {
        byte[] encodedHash = digest.digest(data.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(encodedHash);
    }

    public static String bytesToHex(byte[] bytes) {
        StringBuffer result = new StringBuffer();
        for (byte byt : bytes) result.append(Integer.toString((byt & 0xff) + 0x100, 16).substring(1));
        return result.toString();
    }

    public static String createSHA256Hash(Block block) {
        String blockData = block.getId() + block.getNonce() + block.getData() + block.getPreviousHash();
        return createSHA256Hash(blockData);
    }

    public static String createSHA256Hash(String id, String nonce, String data, String previousHash) {
        String blockData = id + nonce + data + previousHash;
        return createSHA256Hash(blockData);
    }

    public static boolean isValid(Block block) {
        return block.getHash().startsWith(HASH_PREFIX);
    }

    public static Block createGenesisBlock(String data) {
        String nextId = GENESIS_BLOCK_ID;
        String nonce = getNextNonce(null);
        Block genesisBlock = new BlockBuilder()
                .setId(nextId)
                .setNonce(nonce)
                .setData(data)
                .setPreviousHash(GENESIS_BLOCK_PREVIOUS_HASH)
                .build();
        while (true) {
            if (isValid(genesisBlock)) break;
            nonce = getNextNonce(nonce);
            genesisBlock = new BlockBuilder()
                    .from(genesisBlock)
                    .setNonce(nonce)
                    .build();
        }
        return genesisBlock;
    }

    public static Block mineNextBlock(Block lastBlock, String data) {
        String nextId = getNextBlockId(lastBlock.getId());
        String nonce = getNextNonce(null);
        Block nextBlock = new BlockBuilder()
                .setId(nextId)
                .setNonce(nonce)
                .setData(data)
                .setPreviousHash(lastBlock.getHash())
                .build();
        while (true) {
            if (isValid(nextBlock)) break;
            nonce = getNextNonce(nonce);
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
