package com.sromku.sample.runtests;

import android.os.Bundle;
import android.support.test.internal.runner.listener.InstrumentationResultPrinter;
import android.support.test.internal.runner.listener.InstrumentationRunListener;

import org.junit.runner.Description;

import java.lang.annotation.Annotation;
import java.util.Collection;

/**
 * Created by sromku.
 */
public class AnnotationsTestPrinter extends InstrumentationRunListener {

    @Override
    public void testStarted(Description description) throws Exception {
        super.testStarted(description);

        Collection<Annotation> annotations = description.getAnnotations();
        if (annotations == null) {
            return;
        }

        Bundle bundle = new Bundle();
        StringBuilder stringBuilder = new StringBuilder();
        boolean comm = false;
        for (Annotation annotation : annotations) {
            if (comm) stringBuilder.append(",");
            stringBuilder.append(annotation.annotationType().getSimpleName());
            comm = true;
        }

        bundle.putString("annotations", stringBuilder.toString());
        getInstrumentation().sendStatus(InstrumentationResultPrinter.REPORT_VALUE_RESULT_START, bundle);

    }
}
