package com.wikia.webdriver.pageobjectsfactory.pageobject.special.block;

import com.wikia.webdriver.common.contentpatterns.URLsContent;
import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.logging.Log;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class SpecialBlockPage extends WikiBasePageObject {

  @FindBy(css = "input[name='wpTarget']")
  private WebElement userNameField;
  @FindBy(css = "select#mw-input-wpExpiry")
  private WebElement expiry;
  @FindBy(css = "#mw-input-wpExpiry-other")
  private WebElement expiryInput;
  @FindBy(css = "#mw-input-wpReason-other")
  private WebElement reasonInput;
  @FindBy(css = ".mw-htmlform-submit")
  private WebElement blockButton;
  @FindBy(css = ".mw-input [type='checkbox']")
  private List<WebElement> checkBoxes;

  public SpecialBlockPage(WebDriver driver) {
    super();
    PageFactory.initElements(driver, this);
  }

  public SpecialBlockPage open() {
    return open(urlBuilder.getUrl());
  }

  public SpecialBlockPage open(String wikiUrl) {
    getUrl(wikiUrl + URLsContent.WIKI_DIR + URLsContent.SPECIAL_BLOCK);
    Log.log("openSpecialBlockPage", "history page opened", true);
    wait.forElementVisible(blockButton);

    return this;
  }

  public void typeInUserName(String userName) {
    wait.forElementVisible(userNameField);
    userNameField.sendKeys(userName);
  }

  public void selectExpiration(String period) {
    wait.forElementVisible(expiry);
    Select exp = new Select(expiry);
    exp.selectByValue(period);
  }

  /**
   * @param period you can type here '5 min', '10 year', ...
   */
  public void typeExpiration(String period) {
    wait.forElementVisible(expiryInput);
    expiryInput.sendKeys(period);
  }

  public void typeReason(String reason) {
    wait.forElementVisible(reasonInput);
    reasonInput.sendKeys(reason);
  }

  public void clickBlockButton() {
    wait.forElementVisible(blockButton);
    scrollAndClick(blockButton);
  }

  public void deselectAllSelections() {
    checkBoxes.stream().filter(WebElement::isSelected).forEach(WebElement::click);

    checkBoxes.stream().map(WebElement::isSelected).forEach(Assertion::assertFalse);

    Log.log("deselectAllSelections", "all selections deselected", true);
  }
}
