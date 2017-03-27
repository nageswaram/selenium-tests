package com.wikia.webdriver.pageobjectsfactory.pageobject.auth;

public class DetachedForgotPasswordPage implements ForgotPasswordPage {

  AttachedForgotPasswordPage forgotPasswordPage;

  public DetachedForgotPasswordPage(AttachedForgotPasswordPage forgotPasswordPage) {
    this.forgotPasswordPage = forgotPasswordPage;
  }

  public DetachedForgotPasswordPage() {
    this.forgotPasswordPage = new AttachedForgotPasswordPage();
  }

  @Override public void submit() {
    this.forgotPasswordPage.submit();
  }

  @Override public void requestLinkForUsername(String username) {
    this.forgotPasswordPage.requestLinkForUsername(username);
  }
}
