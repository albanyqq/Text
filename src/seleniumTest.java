import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class seleniumTest {
	static StringBuffer sql = new StringBuffer();
	static String sql1 = null;
	static DBhelper db1 = null;
	String overnumber;
	
	
	public static void main(String args[]) throws SQLException {
		System.getProperties().setProperty("webdriver.chrome.driver",
				"C:\\driver\\chromedriver.exe");

		WebDriver webDriver = new ChromeDriver();
		for (int i = 170000; i < 200000; i++) {
			// ������ʼ��վ
			webDriver.get("http://www.ele.me/shop/" +i + "/info");//����˳�������վ��ַ�Ĺ�ʽ
			try {
				String number = webDriver
						.findElement(
								By.xpath("//div[@class='shopguide-info-wrapper']/p[@class='shopguide-info-rate']/span[@class='ng-binding']"))
						.getText();
				String address = webDriver
						.findElement(
								By.xpath("//div[@class='shopinfo-content']/p[@ng-if='shop.address']/span[@class='ng-binding']"))
						.getText();
				// ��ȡ�ؼ�����
				if (!number.equals("") && !number.equals("����0��")) {

					Pattern pattern = Pattern.compile("[^0-9]");
					Matcher matcher = pattern.matcher(number);
					String overnumber = matcher.replaceAll("");
					// ��ȡ���۵����е�����

					sql.append("insert into eleme(number,address) values");
					sql.append("('");
					sql.append(overnumber + "', '");
					sql.append(address);
					sql.append("');");
					sql1 = sql.toString();// �����������
					db1 = new DBhelper(sql1);// ����DBHelper����
					sql.delete(0, sql.length());// ����������
					db1.pst.executeUpdate();// ִ�в������
					db1.pst.close();// �رղ���
					db1.close();// �ر�����
				}else{
		
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("δ���ҵ���������·���µ�����");
			}
		}
		webDriver.close();
		System.out.println("�������");
	}
}
