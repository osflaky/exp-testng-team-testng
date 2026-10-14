package org.testng.reporters.snapshot;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * A configuration method that is handed the values its test method will run with, mutates them, and
 * then fails -- so a reporter has to have kept what it was announced with to report it.
 *
 * <p>The value is made per run rather than held by the class: mutating it is the point, and a
 * sample every run leaves spent is one only the first reporter to use it can assert on.
 */
public class ConfigurationParameterSample {

  @DataProvider(name = "shared")
  public static Object[][] shared() {
    return new Object[][] {{new MutableParameter("before-configuration")}};
  }

  @BeforeMethod
  public void prepare(Object[] parameters) {
    ((MutableParameter) parameters[0]).set("mutated");
    throw new IllegalStateException("this configuration method fails on purpose");
  }

  @Test(dataProvider = "shared")
  public void report(MutableParameter parameter) {}
}
