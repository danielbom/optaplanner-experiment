package com.ipb;

import java.time.Duration;

import com.ipb.demo.DemoDataGenerator.DemoData;
import com.ipb.solver.TimeTableConstraintProvider.ConstraintsUsed;

public class Constants {
    public static final DemoData demoData = DemoData.SMALL;
    public static final Duration terminationSpentLimit = Duration.ofMillis(5000); // 5s;
    public static final ConstraintsUsed constraintsUsed = ConstraintsUsed.ALL;
}
