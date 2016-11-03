package itx.examples.primenumbers;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PrimeNumbersLookup {
	
	private static final Logger logger = Logger.getLogger(PrimeNumbersLookup.class.getName());
	
	private List<Long> getPrimeFactors(long number) {
	    long n = number;
	    List<Long> factors = new ArrayList<Long>();
	    for (long i = 2; i <= n / i; i++) {
	      while (n % i == 0) {
	        factors.add(i);
	        n /= i;
	      }
	    }
	    if (n > 1) {
	      factors.add(n);
	    }
	    return factors;
	}
	
	private boolean isPrime(long number) {
		if (number > 1) {
			long n = number;
			for (int i = 2; i <= n / i; i++) {
				while (n % i == 0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void printFactors(long number) {
		long st = System.currentTimeMillis();
		List<Long> factors = getPrimeFactors(number);
		long fTime = System.currentTimeMillis() - st;
		String factorString = "";
		for (Long factor: factors) {
			factorString = factorString + "." + factor;
		}
		logger.info("factors of " + number + " are: " + factorString + ", took: " + fTime + "ms");
	}
	
	public void lookForPrimes(long startNumber) {
		long startTime = System.currentTimeMillis();
		long primeCounter = 0;
		logger.info("looking for primes in range of long ...");
		for (long counter=startNumber; counter <= Long.MAX_VALUE; counter ++) {
			if (isPrime(counter)) {
				logger.info("found prime [" + primeCounter + "]: " + counter);
				primeCounter++;
			}
		}
		logger.info("done in " + (System.currentTimeMillis() - startTime) + "ms, found " + primeCounter + " primes.");
	}

	public static void main(String[] args) {
		PrimeNumbersLookup pnl = new PrimeNumbersLookup();
		pnl.lookForPrimes(286260629);
		//pnl.printFactors();
		//pnl.printFactors(4062557);
		//pnl.printFactors(286260629);
		//pnl.printFactors(1234567890123456789L);
		//pnl.printFactors(2*286260629L);
		//pnl.printFactors(9223372036854775807L); //MAX Long
	}

}
