# logback-logpush-appender

[![Build Status](https://travis-ci.org/logpush/logback-logpush-appender.svg?branch=master)](https://travis-ci.org/logpush/logback-logpush-appender) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.logpush/logback-logpush-appender/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.logpush/logback-logpush-appender)

## Dependency

### Maven

```xml
<dependency>
  <groupId>io.logpush</groupId>
  <artifactId>logback-logpush-appender</artifactId>
  <version>0.1.0</version>
</dependency>
```

### Gradle

```groovy
compile 'io.logpush:logback-logpush-appender:0.1.0'
```

## How to use

```xml
<appender name="LOGPUSH" class="io.logpush.logback.appender.LogpushAppender">
  <token>THIS_IS_LOGPUSH_TOKEN</token> <!-- Your Logpush Token -->
  <onlyError>true</onlyError> <!-- Optional, Only show ERROR level log, default value is true -->
</appender>
```
