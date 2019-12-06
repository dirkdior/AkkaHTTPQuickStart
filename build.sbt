name := "ScalaAkkaHttp"

version := "0.1"

scalaVersion := "2.13.1"

lazy val akkaHttpVersion = "10.1.10"
lazy val akkaVersion = "2.6.0"
lazy val scalaTestVersion = "3.0.8"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "org.scalatest"     %% "scalatest"            % scalaTestVersion  % Test,
  "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion   % Test,
  "com.typesafe.akka" %% "akka-testkit"         % akkaVersion       % Test
  //"com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion       % Test
)
