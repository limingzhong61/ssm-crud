package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class JDBCTest {
	@Autowired
	ComboPooledDataSource pool;
	@Test
	public void test() {
		System.out.println(pool);
	}
}
