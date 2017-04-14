
package com.wetrack.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by zhanghong on 16/1/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = { "classpath*:spring*/*.xml" }
)
public class ProviderServiceTest {

	@Test
	public void testProvider() {
		System.out.println("service start");
		synchronized (ProviderServiceTest.class) {
			while (true) {
				try {
					ProviderServiceTest.class.wait();
				} catch (Throwable e) {
				}
			}
		}
	}
}

