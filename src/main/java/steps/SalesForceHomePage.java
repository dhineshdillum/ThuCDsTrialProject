package steps;

import net.thucydides.core.pages.PageObject;
import net.thucydides.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.Select;

/**
 * Created by dhinesh.dillum on 03/02/17.
 */
public class SalesForceHomePage extends PageObject {

    //private WebElementFacade dropdownList() 	 { return element(By.xpath("//*[@class='x-btn-small x-btn-icon-small-left']"));		}
    private WebElementFacade dropdownList() 	 { return element(By.xpath("//[contains(text(),'::after')]"));		}
    private WebElementFacade knowledge1() 	 { return element(By.xpath("//*[@id='navigator-sbmenu']//span[contains(text(),'Knowledge')]"));		}
    private WebElementFacade knowledge() 	 { return element(By.xpath("//*[@id='nav-tab-2']"));		}

    public void selectOptionInDropDownList() {
        waitFor(3).seconds();

        WebElementFacade know1 = element((knowledge()));
        System.out.println("know1 = " + know1);

                
        JavascriptExecutor js1 =(JavascriptExecutor)getDriver();
        js1.executeScript("arguments[0].click();",know1);

//        dropdownList().click();
//        waitFor(3).seconds();
//
//        dropdownList().selectByValue("Knowledge");

//        Select select = new Select(getDriver().findElement(By.xpath("//*[@class='x-menu-item knowledgeMru standardObject sd-nav-menu-item']")));
//        select.deselectAll();
//        select.selectByVisibleText("Knowledge");
        waitFor(3).seconds();
    }
}
