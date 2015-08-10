package com.wikia.webdriver.testcases.articlecrudtests;

import com.wikia.webdriver.common.core.ArticleContent;
import com.wikia.webdriver.common.core.TestContext;
import com.wikia.webdriver.common.core.annotations.Execute;
import com.wikia.webdriver.common.core.annotations.User;
import com.wikia.webdriver.common.driverprovider.UseUnstablePageLoadStrategy;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.DeletePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.RenamePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.article.ArticlePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialRestorePageObject;

import org.testng.annotations.Test;

/**
 * @author: Bogna 'bognix' Knychała
 * @ownership: Content X-Wing
 */
@Test(groups = {"ArticleActionsAdmin"})
public class ArticleActionsAdminTests extends NewTestTemplate {

  @Test(groups = {"ArticleActionsAdmin_001"})
  @UseUnstablePageLoadStrategy
  @Execute(asUser = User.STAFF)
  public void deleteUndeleteArticle() {
    ArticleContent.push("LOREM IPSUM");

    ArticlePageObject article = new ArticlePageObject(driver).open();
    DeletePageObject deletePage = article.deleteUsingDropdown();
    deletePage.submitDeletion();
    SpecialRestorePageObject restore = article.undeleteByFlashMessage();
    restore.verifyArticleName(TestContext.getCurrentMethodName());
    restore.giveReason(article.getTimeStamp());
    restore.restorePage();
    article.verifyNotificationMessage();
    article.verifyArticleTitle(TestContext.getCurrentMethodName());
  }

  @Test(groups = {"ArticleActionsAdmin_002"})
  @UseUnstablePageLoadStrategy
  @Execute(asUser = User.STAFF)
  public void moveArticle() {
    ArticleContent.push("LOREM IPSUM");

    ArticlePageObject article = new ArticlePageObject(driver).open();
    String articleNewName = TestContext.getCurrentMethodName() + article.getTimeStamp();
    RenamePageObject renamePage = article.renameUsingDropdown();
    renamePage.rename(articleNewName, false);
    article.verifyNotificationMessage();
    article.verifyArticleTitle(articleNewName);
  }
}
