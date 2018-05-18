/*
 *   The MIT License (MIT)
 *
 *   Copyright (c) 2016 Cleveroad
 *
 *   Permission is hereby granted, free of charge, to any person obtaining a copy
 *   of this software and associated documentation files (the "Software"), to deal
 *   in the Software without restriction, including without limitation the rights
 *   to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *   copies of the Software, and to permit persons to whom the Software is
 *   furnished to do so, subject to the following conditions:
 *
 *   The above copyright notice and this permission notice shall be included in all
 *   copies or substantial portions of the Software.
 *
 *   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *   IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *   FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *   AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *   LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *   SOFTWARE.
 */
package com.cvsong.study.library.wiget.slidingtutorial;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Basic implementation of {@link PageSupportFragment}.
 */
public class SimplePageSupportFragment extends PageSupportFragment {

    private SimplePageImpl mSimplePage;
    private PageImpl.InternalFragment mInternalFragment = new PageImpl.InternalFragment() {
        @Override
        public int getLayoutResId() {
            return SimplePageSupportFragment.this.getLayoutResId();
        }

        @Override
        public TransformItem[] getTransformItems() {
            return SimplePageSupportFragment.this.getTransformItems();
        }

        @Override
        public Bundle getArguments() {
            return SimplePageSupportFragment.this.getArguments();
        }
    };

    public static PageSupportFragment newInstance(@NonNull PageOptions pageOptions) {
        return newInstance(pageOptions.getPageLayoutResId(), ValidationUtil.checkNotNull(pageOptions.getTransformItems()));
    }

    public static PageSupportFragment newInstance(@LayoutRes int pageLayoutRes, @NonNull TransformItem[] transformItems) {
        PageSupportFragment fragment = new SimplePageSupportFragment();
        fragment.setArguments(SimplePageImpl.getArguments(pageLayoutRes, transformItems));
        return fragment;
    }

    public SimplePageSupportFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSimplePage = new SimplePageImpl(mInternalFragment);
    }

    @Override
    public int getLayoutResId() {
        return mSimplePage.getLayoutResId();
    }

    @NonNull
    @Override
    public TransformItem[] getTransformItems() {
        return mSimplePage.getTransformItems();
    }
}
