package com.hawksjamesf.common.util;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.AnimRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

/**
 * Copyright ® $ 2017
 * All right reserved.
 *
 * @author: hawks.jamesf
 * @since: Nov/01/2018  Thu
 */
public class FragmentUtil {
    private static final int TYPE_ADD_FRAGMENT = 0X01;
    private static final int TYPE_SHOW_FRAGMENT = 0x01 << 1;
    private static final int TYPE_HIDE_FRAGMENT = 0x01 << 2;
    private static final int TYPE_SHOW_HIDE_FRAGMENT = 0x01 << 3;
    private static final int TYPE_REPLACE_FRAGMENT = 0X01 << 4;
    private static final int TYPE_REMOVE_FRAGMENT = 0x01 << 5;
    private static final int TYPE_REMOVE_TO_FREAGMENT = 0x01 << 6;

    private static final String ARGS_ID = "args_id";
    private static final String ARGS_IS_HIDE = "args_is_hide";
    private static final String ARGS_IS_ADD_STACK = "args_is_add_stack";

    //===============================================================================
    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @NonNull final int containerId) {
        add(fm, add, containerId, false, false);
    }

    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @NonNull final int containerId, @NonNull final boolean isHide) {
        add(fm, add, containerId, isHide, false);
    }

    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @NonNull final int containerId, @NonNull final boolean isHide,
                           @NonNull final boolean isAddStack) {
        putArgs(add, new Args(containerId, isHide, isAddStack));
        operateNoAnim(fm, TYPE_ADD_FRAGMENT, null, add);
    }

    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @AnimRes final int enterAnim, @AnimRes final int exitAnim) {
        add(fm, add, containerId, false, enterAnim, exitAnim, 0, 0);
    }

    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           //Fragment转场动画
                           @AnimRes final int enterAnim, @AnimRes final int exitAnim,
                           //Fragment从栈pop出去的动画
                           @AnimRes final int popEnterAnim, @AnimRes final int popExitAnim) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addAnim(ft, enterAnim, exitAnim, popEnterAnim, popExitAnim);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }


    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @IdRes final int containerId,
                           @NonNull final View... sharedElements) {
        add(fm, add, containerId, false, sharedElements);
    }

    public static void add(@NonNull final FragmentManager fm, @NonNull final Fragment add,
                           @IdRes final int containerId,
                           final boolean isAddStack,
                           @NonNull final View... sharedElements) {
        FragmentTransaction ft = fm.beginTransaction();
        putArgs(add, new Args(containerId, false, isAddStack));
        addSharedElements(ft, sharedElements);
        operate(TYPE_ADD_FRAGMENT, fm, ft, null, add);
    }

    //===============================================================================

    public static void show(@NonNull final Fragment show) {
        putArgs(show, false);
        operateNoAnim(show.getFragmentManager(), TYPE_SHOW_FRAGMENT, null, show);
    }

    public static void show(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment f :
                fragments) {
            putArgs(f, false);
        }
        operateNoAnim(fm, TYPE_SHOW_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));

    }

    //===============================================================================
    public static void hide(@NonNull final Fragment hide) {
        putArgs(hide, true);
        operateNoAnim(hide.getFragmentManager(), TYPE_HIDE_FRAGMENT, null, hide);
    }

    public static void hide(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        for (Fragment hide :
                fragments) {
            putArgs(hide, true);
        }
        operateNoAnim(fm, TYPE_HIDE_FRAGMENT, null, fragments.toArray(new Fragment[fragments.size()]));
    }
    //===============================================================================

    public static void replace(@NonNull final Fragment src,
                               @NonNull final Fragment dest) {
        replace(src, dest, false);
    }

    public static void replace(@NonNull final Fragment src,
                               @NonNull final Fragment dest,
                               final boolean isAddStack) {
        Args args = getArgs(src);
        replace(src.getFragmentManager(), dest, args.id, isAddStack);
    }

    public static void replace(@NonNull final FragmentManager fm,
                               @NonNull final Fragment fragment,
                               final int containerId,
                               @NonNull final boolean isAddStack) {

        FragmentTransaction ft = fm.beginTransaction();
        putArgs(fragment, new Args(containerId, false, isAddStack));
        operate(TYPE_REPLACE_FRAGMENT, fm, ft, null, fragment);

    }

    //===============================================================================


    public static void pop(@NonNull final FragmentManager fm) {
        pop(fm, true);
    }

    public static void pop(@NonNull final FragmentManager fm,
                           final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate();
        } else {
            fm.popBackStack();
        }
    }

    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive) {
        popTo(fm, popClz, isInclusive, true);
    }

    public static void popTo(@NonNull final FragmentManager fm,
                             final Class<? extends Fragment> popClz,
                             final boolean isInclusive,
                             final boolean isImmediate) {
        if (isImmediate) {
            fm.popBackStackImmediate(popClz.getName(), isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        } else {
            fm.popBackStack(popClz.getName(), isInclusive ? FragmentManager.POP_BACK_STACK_INCLUSIVE : 0);
        }

    }

    public static void popAll(@NonNull final FragmentManager fm) {
        popAll(fm, true);
    }

    public static void popAll(@NonNull final FragmentManager fm, final boolean isImmediate) {
        while (fm.getBackStackEntryCount() > 0) {

            if (isImmediate) {
                fm.popBackStackImmediate();
            } else {
                fm.popBackStack();
            }
        }
    }
    //===============================================================================

    private static Args getArgs(final Fragment fragment) {
        Bundle arguments = fragment.getArguments();
        return new Args(arguments.getInt(ARGS_ID, fragment.getId()),
                arguments.getBoolean(ARGS_IS_HIDE),
                arguments.getBoolean(ARGS_IS_ADD_STACK));
    }


    private static void addAnim(@NonNull FragmentTransaction ft, @AnimRes final int enterAnim, @AnimRes final int exitAnim,
                                @AnimRes final int popEnterAnim, @AnimRes final int popExitAnim) {
        ft.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
    }

    public static void addSharedElements(@NonNull FragmentTransaction ft, @NonNull View... sharedElements) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            for (View view : sharedElements) {
                ft.addSharedElement(view, view.getTransitionName());
            }
        }
    }

    private static void putArgs(final Fragment fragment, final boolean isHide) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putBoolean(ARGS_IS_HIDE, isHide);
    }

    private static void putArgs(final Fragment fragment, final Args args) {
        Bundle bundle = fragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            fragment.setArguments(bundle);
        }
        bundle.putInt(ARGS_ID, args.id);
        bundle.putBoolean(ARGS_IS_ADD_STACK, args.isAddStack);
        bundle.putBoolean(ARGS_IS_HIDE, args.isHide);
    }

    private static void operateNoAnim(@NonNull final FragmentManager fm,
                                      @NonNull final int type,
                                      final Fragment src,
                                      @NonNull final Fragment... dest) {
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        operate(type, fm, fragmentTransaction, src, dest);

    }

    private static void operate(
            @NonNull final int type,
            @NonNull final FragmentManager fm,
            @NonNull final FragmentTransaction ft,
            final Fragment src,
            @NonNull final Fragment... dest) {
        if (src != null && src.isRemoving()) {

            return;
        }
        String name;
        Bundle args;
        switch (type) {
            case TYPE_ADD_FRAGMENT: {
                for (Fragment f : dest) {
                    name = f.getClass().getName();
                    args = f.getArguments();
                    Fragment fragmentByTag = fm.findFragmentByTag(name);
                    if (fragmentByTag != null && fragmentByTag.isAdded()) {
                        ft.remove(fragmentByTag);
                    }
                    ft.add(args.getInt(ARGS_ID), f, name);
                    if (args.getBoolean(ARGS_IS_HIDE)) ft.hide(f);
                    if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                }
                break;

            }
            case TYPE_HIDE_FRAGMENT: {
                for (Fragment f :
                        dest) {
                    ft.hide(f);
                }
                break;

            }
            case TYPE_SHOW_FRAGMENT: {
                for (Fragment f : dest) {
                    ft.show(f);
                }
                break;

            }
            case TYPE_SHOW_HIDE_FRAGMENT: {
                ft.show(src);
                for (Fragment f : dest) {
                    ft.hide(f);
                }
                break;

            }
            case TYPE_REPLACE_FRAGMENT: {
                name = dest[0].getClass().getName();
                args = dest[0].getArguments();
                ft.replace(args.getInt(ARGS_ID), dest[0], name);
                if (args.getBoolean(ARGS_IS_ADD_STACK)) ft.addToBackStack(name);
                break;


            }
            case TYPE_REMOVE_FRAGMENT: {
                for (Fragment f :
                        dest) {
                    if (f != src) {
                        ft.remove(f);
                    }
                }
                break;
            }
            case TYPE_REMOVE_TO_FREAGMENT: {
                for (Fragment f : dest) {
                    if (f == dest[0]) {
                        ft.remove(f);
                        break;
                    }
                    ft.remove(f);

                }
                break;
            }
        }
        /*
            系统不允许Fragment在Activity#onSaveInstanceState做commit的操作，因为在onSaveInstanceState阶段会保存Fragment对象，而在onSaveInstanceState之后所发生的commit操作数据可能丢失。逻辑上是说不过去的。所以会throw异常。但是有时候Fragment页面并没有做多少的数据交互或者onSaveInstanceState保存Fragment对象不重要，所以正常道理也可以在onSaveInstanceState之后执行commit，故commitAllowingStateLoss
         */
        ft.commitAllowingStateLoss();
//        ft.commit()
    }

    public static Fragment getTopFragment(@NonNull final FragmentManager fm) {
        return getTopInStack(fm, false);
    }

    public static Fragment getTopFragmentInStack(@NonNull final FragmentManager fm) {
        return getTopInStack(fm, true);
    }

    private static Fragment getTopInStack(@NonNull final FragmentManager fm,
                                          final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i >= 0; --i) {
            Fragment f = fragments.get(i);
            if (f != null) {
                if (isInStack) {
                    if (f.getArguments().getBoolean(ARGS_IS_ADD_STACK)) return f;
                }
            } else {
                return f;
            }
        }
        return null;
    }


    private static Fragment getTopShowInStack(@NonNull final FragmentManager fm, final boolean isInStack) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i > 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null && fragment.isAdded() && fragment.isVisible() && fragment.getUserVisibleHint()) {
                if (isInStack) {
                    if (fragment.getArguments().getBoolean(ARGS_IS_ADD_STACK)) return fragment;
                } else {
                    return fragment;
                }
            }

        }
        return null;
    }

    public static List<Fragment> getFragments(@NonNull final FragmentManager fm) {
        List<Fragment> fragmentList = fm.getFragments();
        if (fragmentList == null || fragmentList.isEmpty()) return Collections.emptyList();
        return fragmentList;
    }

    public static List<Fragment> getFragmentsInStack(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        List<Fragment> result = new ArrayList<>();
        for (Fragment f :
                fragments) {
            if (f != null && f.getArguments().getBoolean(ARGS_IS_ADD_STACK)) result.add(f);
        }
        return result;
    }


    public static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm) {
        return getAllFragments(fm, new ArrayList<FragmentNode>());
    }

    private static List<FragmentNode> getAllFragments(@NonNull final FragmentManager fm,
                                                      final List<FragmentNode> result) {
        List<Fragment> fragments = getFragments(fm);
        for (int i = fragments.size() - 1; i > 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                result.add(new FragmentNode(
                        fragment,
                        getAllFragments(fragment.getChildFragmentManager(), new ArrayList<FragmentNode>())));
            }
        }

        return result;
    }

    public static boolean dispatchBackPress(@NonNull final Fragment fragment) {
        return fragment.isResumed()
                && fragment.isVisible()
                && fragment.getUserVisibleHint()
                && fragment instanceof OnBackClickListener
                && ((OnBackClickListener) fragment).onBackClick();
    }

    public static boolean dispatchBackPress(@NonNull final FragmentManager fm) {
        List<Fragment> fragments = getFragments(fm);
        if (fragments == null || fragments.isEmpty()) return false;
        for (int i = fragments.size() - 1; i > 0; --i) {
            Fragment fragment = fragments.get(i);
            if (fragment != null) {
                dispatchBackPress(fragment);
            }
        }
        return false;
    }

    public interface OnBackClickListener {
        boolean onBackClick();
    }

    private static class Args {
        int id;
        boolean isHide;
        boolean isAddStack;

        public Args(int id, boolean isHide, boolean isAddStack) {
            this.id = id;
            this.isHide = isHide;
            this.isAddStack = isAddStack;
        }
    }

    private static class FragmentNode {
        Fragment fragment;
        List<FragmentNode> next;

        public FragmentNode(Fragment fragment, List<FragmentNode> next) {
            this.fragment = fragment;
            this.next = next;
        }
    }
}
