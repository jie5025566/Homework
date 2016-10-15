package com.cc.zmj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author zhaomengjie
 * @version 2016-10-15 11:54:32
 */
public class Summary {
	
	/**
	 * 
	 * @param input 输入字符串
	 * @return 调用模块函数，实现收入，支出和收益的计算,最后统计总收入，支出和收益，生成汇总信息字符串作为输出
	 * @throws Exception 调用时间库
	 * @throws Exception 解析输入字符串, 如果是linux系统回车符是 \n，如果是windows系统回车符是\r\n,这里假设在windows系统下利用\r\n
	 */
	
	public String generateSummary(String input) throws Exception{

	  	StringBuilder sbBuilder=new StringBuilder();
	  	sbBuilder.append("[summary]\r\n\r\n");
	  	long totalIncome=0;
	  	long totalPayment=0;
	  	long Profit=0;
		String[] items=input.split("\r\n");
		for(String item:items){
			String[] inputs=item.split(" ");
			/**pTime 活动的年月日时间*/
			String pTime=inputs[0];
			String time=inputs[1];
			/**M 参加人数*/
			int M=Integer.valueOf(inputs[2]);
		  	String[] parseTime=time.split("~");
		  	/**first 活动开始时刻*/
		  	String first=parseTime[0];
		  	/**second 活动结束时刻*/
		  	String second=parseTime[1];
		  	getPlace(M);
		  	hourForDay(pTime, first, second);
		  	getMoneyForHour(first, second);
		  	if(getPlaceNum()==0){
		  		M=0;
		  	}
		  	/**income 每次活动的收入*/
		  	long income=30*M;
		  	/**payment 每次活动的支出*/
		  	long payment=getPlaceNum()*getPrice();
		  	/**profit 每次活动的收益*/
		  	long profit=income-payment;
		  	totalIncome+=income;
		  	totalPayment+=payment;
		  	String s=" ";
		  	if(profit>0){
		  		s=" +";
		  	}
		  	sbBuilder.append(pTime+" "+time+" +"+income+" -"+payment+s+profit+"\r\n");
		}
		Profit=totalIncome-totalPayment;
		sbBuilder.append("\r\nTotal Income:"+totalIncome+"\r\n"+"Total Payment:"+totalPayment+"\r\n"+"Profit:"+Profit);
	  	return sbBuilder.toString();

	}
	
	/**dayForWeek 周几*/
	private int dayForWeek;
	/**place_num 场地数目 */
	private int place_num;
	/**time_num 活动时间 */
	private int time_num;
	/**price_num 每个场地的花费 */
	private int price_num;
	
	/**
	 * 初始化参数
	 */
	public Summary(){
		this.dayForWeek=0;
		this.place_num=0;
		this.time_num=0;
		this.price_num=0;
	}

	/**
	 * @param M 活动人数
	 * 
	 */
	public void getPlace(int M){
		int T=M/6;
		int X=M%6;
		switch (T) {
		case 0:
			if(X<4){
				place_num=0;
			}
			else{
				place_num=1;
			}
			break;
		case 1:
			place_num=2;
			break;
		case 2:
			if(X<4){
				place_num=2;
			}
			else{
				place_num=3;
			}
			break;
		case 3:
			if(X<4){
				place_num=3;
			}
			else{
				place_num=4;
			}
			break;
		default:
			place_num=T;
			break;
		}	
	}
	/**
	 * @see Summary#getPlace(int)
	 * @return 得到场地数目
	 */
	public int getPlaceNum(){
		return place_num;
	}
	
	
	/**
	 * @param pTime 活动的年月日时间
	 * @param first 活动的起始时刻
	 * @param second 活动的终止时刻
	 * @throws Exception 调用时间库
	 */
	public void hourForDay(String pTime,String first,String second) throws Exception {
		  SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		  Calendar c1 = Calendar.getInstance();
		  Calendar c2 = Calendar.getInstance();
		  c1.setTime(format.parse(pTime+" "+first));
		  c2.setTime(format.parse(pTime+" "+second));
		  time_num=c2.get(Calendar.HOUR_OF_DAY)-c1.get(Calendar.HOUR_OF_DAY);
		  if(c1.get(Calendar.DAY_OF_WEEK) == 1){
			   dayForWeek = 7;
		  }else{
			   dayForWeek = c1.get(Calendar.DAY_OF_WEEK) - 1;
		  }
	 }
	/**
	 * @see Summary#hourForDay(String, String, String)
	 * @return 得到活动的持续时间
	 */
	public int getHour(){
		return time_num;
	}
	/**
	 * @see Summary#hourForDay(String, String, String)
	 * @return	得到活动在每周几
	 */
	public int getDayForWeek(){
		return dayForWeek;
	}
	
	
	/**
	 * 
	 * @param first 活动的起始时刻
	 * @param second 活动的终止时刻
	 * @throws Exception 调用时间库函数
	 */
	public void getMoneyForHour(String first,String second) throws ParseException{
		int num=getHour();
		long[] standTime={9,12,18,20,22};
		int[] price={30,40,50,60,80};
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Calendar c1= Calendar.getInstance();
		Calendar c2= Calendar.getInstance();
		c1.setTime(df.parse(first));
		c2.setTime(df.parse(second));
		int start= c1.get(Calendar.HOUR_OF_DAY);
		int end= c2.get(Calendar.HOUR_OF_DAY);
		switch (dayForWeek) {
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:		
			if(start>=standTime[0] && start<standTime[1]){
				
				if(end<=standTime[1]){
					price_num=price[0]*num;
				}
				else{
					int count=(int)(standTime[1]-start);
					price_num=price[0]*count+price[2]*(num-count);
				}
			}
			else if(start>=standTime[1] && start<standTime[2]){
				if(end<=standTime[2]){
					price_num=price[2]*num;
				}
				else{
					int count=(int)(standTime[2]-start);
					price_num=price[2]*count+price[4]*(num-count);
				}
			}
			else if(start>=standTime[2] && start<standTime[3]){
				if(end<=standTime[3]){
					price_num=price[4]*num;
				}
				else{
					int count=(int)(standTime[3]-start);
					price_num=price[4]*count+price[3]*(num-count);
				}
			}
			else{
				price_num=price[3]*num;
			}
			break;
		case 6:
		case 7:
			if(start>=standTime[0] && start<standTime[1]){
				if(end<=standTime[1]){
					price_num=price[1]*num;
				}
				else{
					int count=(int)(standTime[1]-start);
					price_num=price[1]*count+price[2]*(num-count);
				}
			}
			else if(start>=standTime[1] && start<standTime[2]){
				if(end<=standTime[2]){
					price_num=price[2]*num;
				}
				else{
					int count=(int)(standTime[2]-start);
					price_num=price[2]*count+price[3]*(num-count);
				}
			}	
			else{
				price_num=price[3]*num;
			}
			break;
		default:
			break;
		}
	}
	/**
	 * @see Summary#getMoneyForHour(String, String)
	 * @return 得到每个场地需要的花费
	 */
	public int getPrice(){
		return price_num;
	}
}
