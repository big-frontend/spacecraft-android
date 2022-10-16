/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package retrofit2;


import org.jetbrains.annotations.NotNull;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

import javax.annotation.Nullable;

public final class OkHttpCallFactory implements Call.Factory {
  private final okhttp3.Call.Factory callFactory;

  private OkHttpCallFactory(okhttp3.Call.Factory callFactory) {
    this.callFactory = callFactory;
  }

  public static OkHttpCallFactory create() {
    return new OkHttpCallFactory(new OkHttpClient());
  }

  public static OkHttpCallFactory create(okhttp3.Call.Factory callFactory) {
    return new OkHttpCallFactory(callFactory);
  }

  @Override
  public <T> Call<T> newCall(@NotNull RequestFactory requestFactory, @Nullable Object[] args, @NotNull Converter<ResponseBody, T> responseConverter) {
    return new OkHttpCall<>(requestFactory, args, callFactory, responseConverter);
  }

}
