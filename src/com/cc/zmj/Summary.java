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
	 * @param input �����ַ���
	 * @return ����ģ�麯����ʵ�����룬֧��������ļ���,���ͳ�������룬֧�������棬���ɻ�����Ϣ�ַ�����Ϊ���
	 * @throws Exception ����ʱ���
	 * @throws Exception ���������ַ���, �����linuxϵͳ�س����� \n�������windowsϵͳ�س�����\r\n,���������windowsϵͳ������\r\n
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
			/**pTime ���������ʱ��*/
			String pTime=inputs[0];
			String time=inputs[1];
			/**M �μ�����*/
			int M=Integer.valueOf(inputs[2]);
		  	String[] parseTime=time.split("~");
		  	/**first ���ʼʱ��*/
		  	String first=parseTime[0];
		  	/**second �����ʱ��*/
		  	String second=parseTime[1];
		  	getPlace(M);
		  	hourForDay(pTime, first, second);
		  	getMoneyForHour(first, second);
		  	if(getPlaceNum()==0){
		  		M=0;
		  	}
		  	/**income ÿ�λ������*/
		  	long income=30*M;
		  	/**payment ÿ�λ��֧��*/
		  	long payment=getPlaceNum()*getPrice();
		  	/**profit ÿ�λ������*/
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
	
	/**dayForWeek �ܼ�*/
	private int dayForWeek;
	/**place_num ������Ŀ */
	private int place_num;
	/**time_num �ʱ�� */
	private int time_num;
	/**price_num ÿ�����صĻ��� */
	private int price_num;
	
	/**
	 * ��ʼ������
	 */
	public Summary(){
		this.dayForWeek=0;
		this.place_num=0;
		this.time_num=0;
		this.price_num=0;
	}

	/**
	 * @param M �����
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
	 * @return �õ�������Ŀ
	 */
	public int getPlaceNum(){
		return place_num;
	}
	
	
	/**
	 * @param pTime ���������ʱ��
	 * @param first �����ʼʱ��
	 * @param second �����ֹʱ��
	 * @throws Exception ����ʱ���
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
	 * @return �õ���ĳ���ʱ��
	 */
	public int getHour(){
		return time_num;
	}
	/**
	 * @see Summary#hourForDay(String, String, String)
	 * @return	�õ����ÿ�ܼ�
	 */
	public int getDayForWeek(){
		return dayForWeek;
	}
	
	
	/**
	 * 
	 * @param first �����ʼʱ��
	 * @param second �����ֹʱ��
	 * @throws Exception ����ʱ��⺯��
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
	 * @return �õ�ÿ��������Ҫ�Ļ���
	 */
	public int getPrice(){
		return price_num;
	}
}
