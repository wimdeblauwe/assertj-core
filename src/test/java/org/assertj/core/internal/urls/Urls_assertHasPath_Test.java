/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.internal.urls;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.error.uri.ShouldHavePath.shouldHavePath;
import static org.assertj.core.test.TestData.someInfo;
import static org.assertj.core.util.FailureMessages.actualIsNull;
import static org.mockito.Mockito.verify;

import java.net.MalformedURLException;
import java.net.URL;

import org.assertj.core.api.AssertionInfo;
import org.assertj.core.internal.UrlsBaseTest;
import org.junit.jupiter.api.Test;

public class Urls_assertHasPath_Test extends UrlsBaseTest {

  @Test
  public void should_pass_if_actual_url_has_the_given_path() throws MalformedURLException {
    urls.assertHasPath(info, new URL("http://example.com/pages/"), "/pages/");
    urls.assertHasPath(info, new URL("http://example.com"), "");
  }

  @Test
  public void should_fail_if_actual_is_null() {
    assertThatExceptionOfType(AssertionError.class).isThrownBy(() -> urls.assertHasPath(info, null, "path"))
                                                   .withMessage(actualIsNull());
  }

  @Test
  public void should_throw_an_exception_fail_if_given_path_is_null() {
    assertThatIllegalArgumentException().isThrownBy(() -> urls.assertHasPath(info, new URL("http://example.com"), null))
                                        .withMessage("Expecting given path not to be null");
  }

  @Test
  public void should_fail_if_actual_URL_path_is_not_the_given_path() throws MalformedURLException {
    AssertionInfo info = someInfo();
    URL url = new URL("http://example.com/pages/");
    String expectedPath = "/news/";

    Throwable error = catchThrowable(() -> urls.assertHasPath(info, url, expectedPath));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHavePath(url, expectedPath));
  }

  @Test
  public void should_fail_if_actual_URL_has_no_path_and_the_given_path_is_not_null() throws MalformedURLException {
    AssertionInfo info = someInfo();
    URL url = new URL("http://example.com");
    String expectedPath = "/news";

    Throwable error = catchThrowable(() -> urls.assertHasPath(info, url, expectedPath));

    assertThat(error).isInstanceOf(AssertionError.class);
    verify(failures).failure(info, shouldHavePath(url, expectedPath));
  }
}
