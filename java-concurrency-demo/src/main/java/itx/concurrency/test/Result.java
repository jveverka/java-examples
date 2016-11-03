package itx.concurrency.test;

public class Result {
	
	private int masterIterations;
	private int slaveIterations;
	private int masterCounter;
	private int slaveCounter;
	private boolean result;
	
	public Result(int masterIterations, int masterCounter, int slaveIterations, int slaveCounter) {
		this.masterIterations = masterIterations;
		this.slaveIterations = slaveIterations;
		this.masterCounter = masterCounter;
		this.slaveCounter = slaveCounter;
		this.result = (masterIterations == masterCounter) && (slaveIterations == slaveCounter);
	}
	
	public boolean isCorrect() {
		return result;
	}
	
	@Override
	public String toString() {
		return result + ":" + masterIterations + "/" + masterCounter + ":" + slaveIterations + "/" + slaveCounter;
	}
	
}
