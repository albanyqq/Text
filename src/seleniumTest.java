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
			// 设置起始网站
			webDriver.get("http://www.ele.me/shop/" +i + "/info");//定义顺序访问网站网址的公式
			try {
				String number = webDriver
						.findElement(
								By.xpath("//div[@class='shopguide-info-wrapper']/p[@class='shopguide-info-rate']/span[@class='ng-binding']"))
						.getText();
				String address = webDriver
						.findElement(
								By.xpath("//div[@class='shopinfo-content']/p[@ng-if='shop.address']/span[@class='ng-binding']"))
						.getText();
				// 爬取关键数据
				if (!number.equals("") && !number.equals("月售0单")) {

					Pattern pattern = Pattern.compile("[^0-9]");
					Matcher matcher = pattern.matcher(number);
					String overnumber = matcher.replaceAll("");
					// 提取月售单数中的数字

					sql.append("insert into eleme(number,address) values");
					sql.append("('");
					sql.append(overnumber + "', '");
					sql.append(address);
					sql.append("');");
					sql1 = sql.toString();// 创建插入语句
					db1 = new DBhelper(sql1);// 创建DBHelper对象
					sql.delete(0, sql.length());// 归零插入语句
					db1.pst.executeUpdate();// 执行插入语句
					db1.pst.close();// 关闭插入
					db1.close();// 关闭连接
				}else{
		
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("未查找到店铺数据路径下的数据");
			}
		}
		webDriver.close();
		System.out.println("爬虫结束");
	}
}
