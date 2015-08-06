package com.wikia.webdriver.pageobjectsfactory.pageobject.mercury;

import com.wikia.webdriver.common.core.Assertion;
import com.wikia.webdriver.common.core.configuration.Configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


/**
 * @ownership Social
 */
public class SignupPageObject extends BasePageObject {

  public SignupPageObject(WebDriver driver) {
    super(driver);
  }

  @FindBy(css = "#signupEmail")
  private WebElement signupEmail;
  @FindBy(css = "#signupUsername")
  private WebElement signupUsername;
  @FindBy(css = "#signupPassword")
  private WebElement signupPassword;
  @FindBy(css = "#signupBirthDate")
  private WebElement signupBirthdate;
  @FindBy(css = ".birth-month")
  private WebElement signupBirthMonth;
  @FindBy(css = ".birth-day")
  private WebElement signupBirthDay;
  @FindBy(css = ".birth-year")
  private WebElement signupBirthYear;
  @FindBy(css = "#signupSubmit")
  private WebElement signupSubmitButton;
  @FindBy(css = ".avatar")
  private WebElement avatar;
  @FindBy(xpath = "//*[@id=\"signupForm\"]/div[1]/small")
  private WebElement emailError;
  @FindBy(xpath = "//*[@id=\"signupForm\"]/div[2]/small[2]")
  private WebElement usernameError;
  @FindBy(xpath = "//*[@id=\"signupForm\"]/div[3]/small")
  private WebElement passwordError;
  @FindBy(css = "#signupForm > small.error")
  private WebElement genericError;
  @FindBy(css = " header.auth-header")
  private WebElement registerHeader;
  @FindBy(css = ".login")
  private WebElement newloginButton;
  @FindBy(css = ".signup-provider-email")
  private WebElement signupButton;

  public SignupPageObject typeEmailAddress(String email) {
    wait.forElementVisible(signupEmail);
    signupEmail.sendKeys(email);
    return this;
  }

  public SignupPageObject typeUsername(String username) {
    wait.forElementVisible(signupUsername);
    signupUsername.sendKeys(username);
    return this;
  }

  public SignupPageObject typePassword(String password) {
    wait.forElementVisible(signupPassword);
    signupPassword.sendKeys(password);
    return this;
  }

  public SignupPageObject typeBirthdate(String month, String day, String year) {
    wait.forElementVisible(signupBirthdate);
    scrollAndClick((signupBirthdate));

    wait.forElementVisible(signupBirthMonth);
    scrollAndClick((signupBirthMonth));
    signupBirthMonth.sendKeys(month);

    wait.forElementVisible(signupBirthDay);
    signupBirthDay.click();
    signupBirthDay.sendKeys(day);

    wait.forElementVisible(signupBirthYear);
    signupBirthYear.click();
    signupBirthYear.sendKeys(year);

    return this;
  }

  public void register() {
    wait.forElementVisible(signupSubmitButton);
    scrollAndClick(signupSubmitButton);
  }

  public void verifyAvatarAfterSignup() {
    wait.forElementVisible(avatar);
    Assertion.assertTrue(avatar.isDisplayed());
  }

  public void verifyEmailInUseError() {
    wait.forElementVisible(emailError);
    Assertion.assertEquals(emailError.getText(), "Email is already registered on Wikia");
  }

  public void verifyUsernameTakenError() {
    wait.forElementVisible(usernameError);
    Assertion.assertEquals(usernameError.getText(), "Someone already has this username. Try a different one!");
  }

  public void verifyPasswordError() {
    wait.forElementVisible(passwordError);
    Assertion.assertEquals(passwordError.getText(), "Your password must be different from your username.");
  }

  public void verifyBirthdateError() {
    wait.forElementVisible(genericError);
    Assertion.assertEquals(genericError.getText(),
        "We can not complete your registration at this time.");
  }

  public String getRegisterHeaderText() {
    return registerHeader.getText();
  }
  public void openRegisterPage() {
    driver.get(urlBuilder.getUrlForWiki(Configuration.getWikiName()) + "register");
  }

  public SignupPageObject openMobileSignupPage(String wikiURL) {
    openHome(wikiURL);
    wait.forElementVisible(newloginButton);
    newloginButton.click();
    wait.forElementVisible(signupButton);
    signupButton.click();
    return new SignupPageObject(driver);
  }
}