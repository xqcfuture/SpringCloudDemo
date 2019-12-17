/**
 * projectName: gs-service-registration-and-discovery
 * fileName: Test.java
 * packageName: hello
 * date: 2019-12-16 14:00
 * copyright(c) 2017-2020 锐捷网络股份有限公司
 */
package hello;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.w3c.dom.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

/**
 * @version: V1.0
 * @author: xieqingcheng
 * @className: Test
 * @packageName: hello
 * @description:
 * @data: 2019-12-16 14:00
 **/
public class Test {

    public static void main(String[] args) {
        long t1 = System.currentTimeMillis();
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chromedriver.exe");
        WebDriver driver;driver=new ChromeDriver();
        driver.get("https://www.ncbi.nlm.nih.gov/geoprofiles/?term=lung%20cancer");

        List<String> gbs = new ArrayList<String>();
        String title = driver.getTitle();
        WebElement we1 = ((ChromeDriver) driver).findElementByLinkText("Next >");
        String page = we1.getAttribute("page");
        Integer pageNum = Integer.parseInt(page);

        try {
            //等待页面加载
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        try {
            while(pageNum < 52507){
                List<WebElement> gbsList = ((ChromeDriver) driver).findElementsByXPath("//*[@id=\"maincontent\"]/div/div[5]/div[@class='rprt']/div[2]/div/div[1]/dl[3]/dd/a[2]");

                for (WebElement we:gbsList
                        ) {
                    if (!gbs.contains(we.getText())) {
                        gbs.add(we.getText());
                    }
                }

                WebElement pageLink = ((ChromeDriver) driver).findElementByLinkText("Next >");
                //点击下一页
                pageLink.click();

                //等待页面加载
                Thread.sleep(50000);

                pageNum ++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        String join = String.join("\n", gbs);

        try {
            FileWriter fileWriter = new FileWriter("C:\\Users\\xqcfuture\\Desktop\\gds.txt");
            fileWriter.write(join);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.printf("遍历页面：" + pageNum +  ",花费时间：" + (System.currentTimeMillis()-t1)/60*1000 + "分钟") ;

        /*driver.close();*/
    }
}
