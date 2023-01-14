// import 'package:synchronized/synchronized.dart';
import 'dart:io';
import 'dart:convert';

class Singleton {
  factory Singleton() {
    return _singleton;
  }
  Singleton._internal();
  static final Singleton _singleton = Singleton._internal();
}

main(List<String> args) {
  var s1 = Singleton();
  var s2 = Singleton();
  print(identical(s1, s2)); // true
  print(s1 == s2); // true
}
