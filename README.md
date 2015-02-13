Maximum productivity with minimum code via declarative UIs for Java.
====================================================================

Quote:

    Just started on using the Swing JavaBuilder and I must say I like it.
    Just replaced 170 rules of Java code with only 13 lines YAML

*Comment posted the JavaBuilders forum*

Forums
------

Join our user discussion forum at [Google Groups](https://groups.google.com/forum/#!forum/javabuilders)

FX JavaBuilder
==============

Currently in progress, working on creating a JavaFX JavaBuilder.

*Requires the new beta JDK 8u40 since it relies on the new Dialog API.*

Gradle
------

    repositories {
        jcenter()
    }

    compile "javabuilders:javafx-core:2.0.0-SNAPSHOT"


Maven
-----

    <repositories>
            <repository>
                    <id>jcenter</id>
                    <url>http://jcenter.bintray.com</url>
            </repository>
    </repositories>

    <dependencies>
            <dependency>
                    <groupId>javabuilders</groupId>
                    <artifactId>javafx-core</artifactId>
                    <version>2.0.0-SNAPSHOT</version>
            </dependency>
    </dependencies>



Swing JavaBuilder
=================

Currently in maintenance mode. No new development is planned.

Documentation
-------------

Please refer to our [PDF Book](https://github.com/jacek99/javabuilders/raw/master/Swing%20JavaBuilder.pdf)

Articles
--------

Refers to older version, but has all the major concepts:

[JavaLobby - Making GUI Builders obsolete with Swing JavaBuilder](http://java.dzone.com/articles/making-gui-builders-obsolete)



Gradle
------

    repositories {
        jcenter()
    }


    compile "javabuilders:swing-core:1.3.0"
    compile "javabuilders:swing-glazedlists:1.3.0"


Maven
-----

    <repositories>
            <repository>
                    <id>jcenter</id>
                    <url>http://jcenter.bintray.com</url>
            </repository>
    </repositories>

    <dependencies>
            <dependency>
                    <groupId>javabuilders</groupId>
                    <artifactId>swing-core</artifactId>
                    <version>1.3.0</version>
            </dependency>
            <dependency>
                    <groupId>javabuilders</groupId>
                    <artifactId>swing-glazedlists</artifactId>
                    <version>1.3.0</version>
            </dependency>
    </dependencies>

