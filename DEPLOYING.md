
# Sonatype Staging
 
 can be [viewed here](https://oss.sonatype.org/index.html#stagingRepositories)
 
# Sonatype Snapshots/Releases

 can be [viewed here](https://oss.sonatype.org/index.html#nexus-search;quick~com.github.pauldambra)
 
# Gradle docs
 
 can be [viewed here](http://central.sonatype.org/pages/gradle.html)
 
# Manual Promotion of releases
 
 is [described here](http://central.sonatype.org/pages/releasing-the-deployment.html)
 
# Publish command

`MAVEN_USER=ausername MAVEN_PASS=asupersecretpassword VERSION='0.0.0-SNAPSHOT' gradle uploadArchives`

NB gpg secrets/gradle config are on my MBP in ~/me/gradle.properties and ~/.gunpg/* that's not sustainable :( 