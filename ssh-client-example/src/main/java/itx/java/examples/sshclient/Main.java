package itx.java.examples.sshclient;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.jcabi.ssh.SSHByPassword;
import com.jcabi.ssh.Shell;

public class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class.getName());
	
	public static void main(String[] args) {
		try {
			Shell shell = new SSHByPassword("localhost", 22, "userName", "password");
			String stdout = new Shell.Plain(shell).exec("hostname");
			logger.info("result: " + stdout);
		} catch (UnknownHostException e) {
			logger.log(Level.SEVERE, "Error: ", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "IO Error: ", e);
		}
	}

}
