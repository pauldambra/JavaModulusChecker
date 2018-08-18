package com.github.pauldambra.moduluschecker

fun String.toNumberList() =
  this.toCharArray().map { it.toString() }.map { it.toInt() }
