package com.hawksjamesf.processor;


import com.google.testing.compile.Compilation;
import com.google.testing.compile.JavaFileObjects;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.testing.compile.CompilationSubject.assertThat;
import static com.google.testing.compile.Compiler.javac;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        Compilation compilation =
                javac()
                        .withProcessors(new ControlProcessor())
                        .compile(
                                JavaFileObjects.forSourceLines(
                                        "Extension",
                                        "package com.bumptech.glide.test;",
                                        "import com.bumptech.glide.annotation.GlideExtension;",
                                        "import com.bumptech.glide.annotation.GlideOption;",
                                        "import com.bumptech.glide.request.BaseRequestOptions;",
                                        "@GlideExtension",
                                        "public class Extension {",
                                        "  private Extension() {}",
                                        "  @GlideOption",
                                        "  public static BaseRequestOptions<?> doSomething(",
                                        "      BaseRequestOptions<?> options) {",
                                        "    return options;",
                                        "  }",
                                        "}"));
        assertThat(compilation).succeeded();
    }
}