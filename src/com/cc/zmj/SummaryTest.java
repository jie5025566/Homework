package com.cc.zmj;

import static org.junit.Assert.*;

import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 * @author zhaomengjie
 * @time 2016-10-15 11:54:32
 */
public class SummaryTest {
	/**
	 * 生成Summary类实例，并初始化参数
	 */
	private static Summary summary=new Summary();
	
	@Before
	public void setUp() throws Exception {

	}

	@Test
	/**
	 * 对外接口函数，生成汇总信息输出的模块单元测试
	 * @throws Exception
	 */
	public void testGenerateSummary() throws Exception {
		String res=summary.generateSummary("2016-06-02 20:00~22:00 7\r\n2016-06-03 09:00~12:00 14\r\n2016-06-04 14:00~17:00 22\r\n2016-06-05 19:00~22:00 3\r\n2016-06-06 12:00~15:00 15\r\n2016-06-07 15:00~17:00 12\r\n2016-06-08 10:00~13:00 19\r\n2016-06-09 16:00~18:00 16\r\n2016-06-10 20:00~22:00 5\r\n2016-06-11 13:00~15:00 11");
		assertEquals("[summary]\r\n\r\n2016-06-02 20:00~22:00 +210 -240 -30\r\n2016-06-03 09:00~12:00 +420 -180 +240\r\n2016-06-04 14:00~17:00 +660 -600 +60\r\n2016-06-05 19:00~22:00 +0 -0 0\r\n2016-06-06 12:00~15:00 +450 -300 +150\r\n2016-06-07 15:00~17:00 +360 -200 +160\r\n2016-06-08 10:00~13:00 +570 -330 +240\r\n2016-06-09 16:00~18:00 +480 -300 +180\r\n2016-06-10 20:00~22:00 +150 -120 +30\r\n2016-06-11 13:00~15:00 +330 -200 +130\r\n\r\nTotal Income:3630\r\nTotal Payment:2470\r\nProfit:1160",res);
	}

	@Test
	/**
	 * 根据策略计算场地数目的模块单元测试
	 * @throws Exception
	 */
	public void testGetPlace() {
		summary.getPlace(7);
		assertEquals(2,summary.getPlaceNum());
	}

	@Test
	/**
	 * 计算周几和活动持续时间的模块单元测试
	 * @throws Exception
	 */
	public void testHourForDay() throws Exception{
		summary.hourForDay("2016-06-02","20:00","22:00");
		assertEquals(4,summary.getDayForWeek());
		assertEquals(2,summary.getHour());
	}

	@Test
	/**
	 * 计算每个场地花费的模块单元测试
	 * @throws Exception
	 */
	public void testGetMoneyForHour() throws ParseException {
		summary.getMoneyForHour("20:00","22:00");
		assertEquals(120,summary.getPrice());
	}

}
