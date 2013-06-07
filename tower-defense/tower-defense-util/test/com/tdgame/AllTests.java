package com.tdgame;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TowerActTests.class, TowerFindTargetTests.class,
		TowerFireTests.class })
public class AllTests {

}
