package org.ngbot.stack.udp;

import java.net.UnknownHostException;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

@Slf4j
class UDPListenerTest {

	@Test
	void testBind() {
		try {
			UDPStackConfiguration config = new UDPStackConfiguration("UDPConfig", "127.0.0.1", 9000 , null);
			UDPListener listener = new UDPListener(config);
			listener.bind();
			Thread.sleep(60000);
		} catch (UnknownHostException e) {
			logger.error("Error in UDP stack configuration", e);
		} catch (Exception e) {
			logger.error("Error in UDP stack bind", e);
		}
	}

}
