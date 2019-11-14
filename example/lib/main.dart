import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:flutter_meiqia/flutter_meiqia.dart';

void main() => runApp(MyApp());

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String res = "未初始化";
  @override
  void initState() {
    super.initState();
    initMeiQia();
  }
  Future<void> initMeiQia() async {
    try {
      res = await FlutterMeiqia.initMeiQia("你的appKey");
    } on PlatformException {
      res = 'Failed to get init.';
    }
    if (!mounted) return;

    setState(() {
      res = res;
    });
  }


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('客服Example'),
        ),
        body: Column(
          children: <Widget>[
            Center(
              child: Text('Running on: $res\n'),
            ),
            FlatButton(
                onPressed: () async {
                   await FlutterMeiqia.openMeiQia();
                },
                child: Text("打开客服")
            )
          ],
        )
      ),
    );
  }
}
