[![Build Status](https://travis-ci.org/damage-control/report.png?branch=master)](https://travis-ci.org/damage-control/report)

Damage Control Report
---------------------

Report generation tool for [Spock Framework](http://github.com/spockframework) based tests. It reads the test result files and produces a nice HTML report using the specification steps (given, when, then).

See some sample reports [here](https://github.com/damage-control/report/wiki/Sample-Reports).

And a sample Spock project using Maven [here](https://github.com/damage-control/report/tree/master/end-2-end-test/sample-spock-project).

Modules
-------

- html-generator
- plugins:maven
- end-2-end-test

Building
--------

Prerequisites:
- JDK 6 or higher
- [Maven](http://maven.apache.org)
- [Gradle](http://www.gradle.org)

To build the whole project and run all tests:

```
gradle
```

To build `html-generator` module only:

```
gradle :html-generator:install
```

Change Notes
------------

## 1.1.0

* new "skip" configuration property (Maven plugin)
* summary on index page
* navigation link on top of each page
* generation timestamps on each page
* ignoring missing sources so plain JUnit tests are displayed as well
* presenting spec error details when available
* handling special characters in feature names

