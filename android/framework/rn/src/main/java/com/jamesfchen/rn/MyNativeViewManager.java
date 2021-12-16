/*
 * Copyright (c) Facebook, Inc. and its affiliates.
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.jamesfchen.rn;

import androidx.annotation.NonNull;

import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewProps;
import com.facebook.react.uimanager.annotations.ReactProp;

/** View manager for {@link MyNativeView} components. */
@ReactModule(name = MyNativeViewManager.REACT_CLASS)
public class MyNativeViewManager extends SimpleViewManager<MyNativeView> {

  public static final String REACT_CLASS = "RNTMyNativeView";
  public MyNativeViewManager() {
  }
  @NonNull
  @Override
  public String getName() {
    return REACT_CLASS;
  }

  @NonNull
  @Override
  protected MyNativeView createViewInstance(@NonNull ThemedReactContext reactContext) {
    return new MyNativeView(reactContext);
  }

  @Override
  @ReactProp(name = ViewProps.OPACITY, defaultFloat = 1.f)
  public void setOpacity(@NonNull MyNativeView view, float opacity) {
    super.setOpacity(view, opacity);
  }
}
