/**
 *
 */
package com.wikia.webdriver.testcases.imageservingtests;

import com.wikia.webdriver.common.contentpatterns.PageContent;
import com.wikia.webdriver.common.driverprovider.UseUnstablePageLoadStrategy;
import com.wikia.webdriver.common.properties.Credentials;
import com.wikia.webdriver.common.templates.NewTestTemplate;
import com.wikia.webdriver.pageobjectsfactory.pageobject.WikiBasePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.DeletePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.actions.RenamePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialNewFilesPageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.SpecialRestorePageObject;
import com.wikia.webdriver.pageobjectsfactory.pageobject.special.filepage.FilePagePageObject;

import org.testng.annotations.Test;

/**
 * @author Karol 'kkarolk' Kujawiak <p/> 1. Delete image, verify 404 status, restore image, verify
 *         200 status 2. Move image, verify status
 */
public class ImageStorageTests extends NewTestTemplate {

  Credentials credentials = config.getCredentials();

  String imageURL;
  String imageThumbnailURL;

  @Test(groups = {"ImageStorageTests", "ImageStorage_001"})
  @UseUnstablePageLoadStrategy
  public void ImageStorage_001_deleteImage_QAART_521() {
    WikiBasePageObject base = new WikiBasePageObject(driver);
    base.logInCookie(credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    FilePagePageObject file = new  SpecialNewFilesPageObject(driver).openFilePage(wikiURL, PageContent.FILE_DELETE_AND_RESTORE, true);
    imageURL = file.getImageUrl();
    imageThumbnailURL = file.getImageThumbnailUrl();
    file.verifyURLStatus(200, imageURL);
    file.verifyURLStatus(200, imageThumbnailURL);

    DeletePageObject delete = file.deletePage();
    base = delete.submitDeletion();
    base.verifyNotificationMessage();

    base.verifyURLStatus(404, imageURL);
    base.verifyURLStatus(404, imageThumbnailURL);

    SpecialRestorePageObject restore = delete.undeleteByFlashMessage();
    restore.giveReason(PageContent.CAPTION);
    restore.restorePage();
    restore.verifyNotificationMessage();

    file.verifyURLStatus(200, imageURL);
    file.verifyURLStatus(200, imageThumbnailURL);
  }

  @Test(groups = {"ImageStorageTests", "ImageStorage_002"})
  @UseUnstablePageLoadStrategy
  public void ImageStorage_002_moveImage() {
    WikiBasePageObject base = new WikiBasePageObject(driver);
    base.logInCookie(credentials.userNameStaff, credentials.passwordStaff, wikiURL);
    FilePagePageObject file = base.openFilePage(wikiURL, PageContent.FILERENAME, true);
    RenamePageObject renamePage = file.renameUsingDropdown();
    String imageNewName = renamePage.getTimeStamp() + PageContent.FILERENAME;
    renamePage.rename(imageNewName, true);
    file.verifyNotificationMessage();
    file.verifyHeader(imageNewName);
    file = base.openFilePage(wikiURL, imageNewName, true);
    renamePage = file.renameUsingDropdown();
    renamePage.rename(PageContent.FILERENAME, true);
    file.verifyNotificationMessage();
    file.verifyHeader(PageContent.FILERENAME);
  }
}
