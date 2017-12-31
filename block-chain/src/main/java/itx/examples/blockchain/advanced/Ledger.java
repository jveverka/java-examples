package itx.examples.blockchain.advanced;

import java.util.List;

public class Ledger {

    private String id;
    private List<Block> blocks;

    public Ledger(String id, List<Block> blocks) {
        this.id = id;
        this.blocks = blocks;
    }

    public String getId() {
        return id;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public int size() {
        return blocks.size();
    }

    public Block getBlockAt(int index) {
        return blocks.get(index);
    }

}
