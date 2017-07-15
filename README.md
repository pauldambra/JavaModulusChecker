# Modulus Checker [![Build Status](https://travis-ci.org/pauldambra/JavaModulusChecker.svg?branch=master)](https://travis-ci.org/pauldambra/JavaModulusChecker) [![Maven Central](https://img.shields.io/maven-central/v/com.github.pauldambra/moduluschecker.svg)](https://search.maven.org/#search%7Cga%7C1%7Ca%3A%22moduluschecker%22) [![codecov](https://codecov.io/gh/pauldambra/JavaModulusChecker/branch/master/graph/badge.svg)](https://codecov.io/gh/pauldambra/JavaModulusChecker)
This is a Java implementation of UK Bank Account Modulus Checking. Modulus Checking is a process used to determine if a given account number could be valid for a given sort code.
***
Receiving a valid modulus check result only means that the Sort Code and Account Number pair **could** exist not that they do!
***
The algorithms, test cases and reference data can be found  [on the vocalink website](http://www.vocalink.com/products/payments/customer-support-services/modulus-checking.aspx "The Vocalink Modulus Checker Website"). That site should be considered the authoritative source for the modulus checking algorithm and weighting data.

#### CodeScene

[![](https://codescene.io/projects/1472/status.svg) Get more details at **codescene.io**.](https://codescene.io/projects/1472/jobs/latest-successful/results)

#### Usage
```
String sortCode = "012345";
String accountNumber = "12345678";
ModulusChecker modulusChecker = new ModulusChecker();
var result =  modulusChecker
			.checkBankAccount(sortCode,accountNumber);
```

#### License
This software is released under the MIT license. 

NB the resource text files valacdos.txt and scsubtab.txt are produced and released by Vocalink not me

#### Vocalink Version

Currently uses v4.0 of Vocalink Modulus Checking copied from their site on 2017-01-04